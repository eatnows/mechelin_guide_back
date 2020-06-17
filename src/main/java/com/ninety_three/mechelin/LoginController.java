package com.ninety_three.mechelin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;

import data.dao.UserDaoInter;
import data.dto.UserDto;
import kakao.login.SetKakaoApi;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	private UserDaoInter udao;
	@Autowired
	private SetKakaoApi kakao;
	
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("/testlogin")
	public String loginPage() {
		return "usertest";
	}
	
	/*
		메일/비밀번호 검사
	*/
	@PostMapping("/login")
	public String loginResult(@RequestParam String email, @RequestParam String password) {
		int mailchk = udao.mailCheck(email);
		String dbpass = udao.getpwd(email);
		boolean isValidPassword = BCrypt.checkpw(password, dbpass);
		
		if (mailchk==1 && isValidPassword) {
			// 메일과 비밀번호 모두 일치
			return "valid";
		} else if (mailchk==1) {
			// 메일 일치 / 비밀번호 불일치
			return "pwfalse";
		} else {
			// 메일부터 틀렸음
			return "mailfalse";
		}
	}
	/*
		메일 중복검사
	*/
	@GetMapping("/signupcheck/email")
	public String mailCheck(@RequestParam String email) {
		String result = "";
		if (udao.mailCheck(email) == 0) {
			// 메일 사용가능
			result = "usethis";
		} else {
			// 메일 중복
			result = "usenot";
		}
		return result;
	}
	/*
		닉네임 중복검사
	*/
	@GetMapping("/signupcheck/nick")
	public String nickCheck(@RequestParam String nickname) {
		String result = "";
		if (udao.nickCheck(nickname) == 0) {
			// 닉네임 사용가능
			result = "usethis";
		} else {
			// 닉네임 중복
			result = "usenot";
		}
		return result;
	}
	/*
		이메일 인증 발송
	*/
	@GetMapping("/validsend")
	public String validation(@RequestParam String email) {
		int cnt = udao.hasInfo(email);
		// 인증대기 없으면 insert, 있으면 update
		if(cnt == 0) {
			// mailvalid TB 에 새로 insert
			udao.insertValid(email);
		} else {
			// mailvalid TB 의 시간 update
			udao.updateValid(email);
		}
		
		String subject = "MEchelin 가이드 인증메일입니다";
		// 인증 클릭하면 /validok 매핑으로 오게 설정
		String clickurl = "http://localhost:9000/mechelin/validok?email=" + email;
		String content = "<a href='" + clickurl + "'>인증하려면 클릭하세요</a>";
		MimeMessage message = sender.createMimeMessage();
		
		try {
			message.setSubject(subject);
			message.setText(content, "UTF-8", "html");
			message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			
			sender.send(message);
			return "mail sended";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("mail sending error: " + e.getMessage());
			return "mailsend fail";
		}
		
	}
	/*
		사용자가 이메일 인증 완료
	*/
	@GetMapping("/validok")
	public void validok(@RequestParam String email) {
		// mailvalid TB 의 인증여부 bool 수정
		udao.gainValid(email);
	}
	
	/*
		회원가입 insert
	*/	
	@PostMapping("/signup")		// 임시매핑
	public String signUp(@RequestBody UserDto dto) {
		// 이메일 인증완료 여부 확인
		if (udao.isGranted(dto.getEmail())) {
			// 비밀번호 암호화
			String password = dto.getPassword();
			String pwdhash = BCrypt.hashpw(password, BCrypt.gensalt());
			dto.setPassword(pwdhash);
			
			// user TB 에 insert
			udao.insertUser(dto);
			
			// mailvalid TB 에서 삭제
			udao.deleteValid(dto.getEmail());
			
			return "success";
		} else {
			// 인증 전이면 프론트에 알림창 지시
			return "signup fail";
		}
	}
	
	/*
		유저가 카카오 로그인 요청 클릭
		: 카카오로 로그인하면 카카오 인증서버가 우리 서비스에 유저 인증코드 돌려줌
	*/
	@GetMapping("/klogin")
	public String klogin(
			HttpSession session,
			@RequestParam String code
	) {
		// 인증코드를 보내서 액세스 토큰 받아오기
		String access_Token = kakao.getAccessToken(code);
		System.out.println("code: " + code);
		System.out.println("accessToken: " + access_Token);
		
		// 액세스 토큰을 보내서 유저 기본정보 받아오기
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
		String kakaoId = userInfo.get("kakaoId").toString();
		String email = userInfo.get("").toString();
		// session 말고 어느 값으로 줄지 프론트와 상의 필요
		session.setAttribute("kakao_id", kakaoId);
		session.setAttribute("access_token", access_Token);
		
		// 같은 메일주소가 우리 user TB에 없고,
		// & 같은 카카오ID가 kakao_user TB에 없으면 -> insert
		int mailmatch = udao.mailCheck(email);
		int dbmatch = udao.apiUserCheck(kakaoId);
		if (mailmatch != 1 && dbmatch != 1) {
			UserDto dto = new UserDto();
			dto.setId(kakaoId);
			dto.setEmail(email);
			udao.insertApiUser(dto);
		}
		
		// return 시 어느 값을 얹어서 줄지 프론트와 상의
		// 일단 token은 넣어서 돌려줘야 한다
		return "redirect:testlogin";
	}
	
	/*
		카카오 로그아웃
		: session 말고 다른 방법으로 받을지 상의 필요
	*/
	@GetMapping("/klogout")
	public String klogout(HttpSession session) {
		session.removeAttribute("access_Token");
		session.removeAttribute("kakao_id");
		return "";
	}
}
