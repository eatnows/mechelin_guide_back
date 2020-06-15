package com.ninety_three.mechelin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@Controller
public class LoginController {
	
	@Autowired
	private UserDaoInter udao;
	@Autowired
	private SetKakaoApi kakao;
	
	@GetMapping("/testlogin")
	public String loginPage() {
		return "usertest";
	}
	
	/*
		메일/비밀번호 검사
		: 메일O 비번O "valid", 메일O 비번X "pwfalse", 메일X "mailfalse"
	*/
	@PostMapping("/login")		// 임시매핑
	public String loginResult(@RequestParam String email, @RequestParam String password) {
		int mailchk = udao.mailCheck(email);
		String dbpass = udao.getpwd(email);
		boolean isValidPassword = BCrypt.checkpw(password, dbpass);
		
		if (mailchk==1 && isValidPassword) {
			return "valid";
		} else if (mailchk==1) {
			return "pwfalse";
		} else {
			return "mailfalse";
		}
	}
	/*
		메일 중복검사
		: 사용가능 "usethis", 중복 "usenot"
	*/
	@GetMapping("/signupcheck/email")
	public String mailCheck(@RequestParam String email) {
		String result = "";
		if (udao.mailCheck(email) == 0) {
			result = "usethis";
		} else {
			result = "usenot";
		}
		return result;
	}
	/*
		닉네임 중복검사
		: 사용가능 "usethis", 중복 "usenot"
	*/
	@GetMapping("/signupcheck/nick")
	public String nickCheck(@RequestParam String nickname) {
		String result = "";
		if (udao.nickCheck(nickname) == 0) {
			result = "usethis";
		} else {
			result = "usenot";
		}
		return result;
	}
	/*
		회원가입
		- 비밀번호 암호화
		- 실행 시 "success" 리턴
	*/	
	@PostMapping("/signup")		// 임시매핑
	public String signUp(@ModelAttribute UserDto dto) {
		String password = dto.getPassword();
		String pwdhash = BCrypt.hashpw(password, BCrypt.gensalt());
		
		dto.setPassword(pwdhash);
		udao.insertUser(dto);
		return "success";
	}
	
	/*
		카카오 로그인 요청
	*/
	@GetMapping("/klogin")
	public String klogin(
			HttpSession session,
			@RequestParam String code
	) {
		String access_Token = kakao.getAccessToken(code);
		System.out.println("code: " + code);
		System.out.println("accessToken: " + access_Token);
		
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
		String kakaoId = userInfo.get("kakaoId").toString();
		String email = userInfo.get("").toString();
		// session 말고 어느 값으로 줄지 프론트와 상의 필요
		session.setAttribute("kakao_id", kakaoId);
		session.setAttribute("access_token", access_Token);
		
		/*
			같은 메일주소가 우리 user TB에 없고,
			같은 카카오ID가 kakao_user TB에 없으면 insert.
			
			있으면 프론트로 경고 돌려줘야 함.
		*/
		int mailmatch = udao.mailCheck(email);
		int dbmatch = udao.apiUserCheck(kakaoId);
		if (mailmatch != 1 && dbmatch != 1) {
			UserDto dto = new UserDto();
			dto.setId(kakaoId);
			dto.setEmail(email);
			udao.insertApiUser(dto);
		}
		
		// return 시 어느 값을 얹어서 줄지 프론트와 상의
		// 일단 token은 돌려줘야 한다
		return "redirect:testlogin";
	}
	
	/*
	@PostMapping(path = "/klogin",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> handle(
		@RequestParam String code
	) {
		ResponseEntity<String> tokenreq = new ResponseEntity<String>("", HttpStatus.OK);
		
		return tokenreq;
	}
	*/
	
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
