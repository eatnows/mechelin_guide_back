package com.ninety_three.mechelin;


import java.util.HashMap;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.UserDaoInter;
import data.dto.UserDto;
import kakao.login.KakaoApiReact;
import kakao.login.SetKakaoApi;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	private UserDaoInter udao;
	@Autowired
	private SetKakaoApi kakao;
	@Autowired
	private KakaoApiReact react;
	
	@Autowired
	private JavaMailSender sender;
	
	// 난수발생용 코드
	private char[] randomChar = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
            'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
            '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
	};
	
	
	@GetMapping("/testlogin")
	public String loginPage() {
		return "usertest";
	}
	
	/*
		메일/비밀번호 검사
	*/
	@PostMapping("/login")
	public UserDto loginResult(@RequestBody UserDto udto) {
		UserDto dto = new UserDto();
		String email = udto.getEmail();
		String password = udto.getPassword();
		int mailchk = udao.mailCheck(email);
		String dbpass = udao.getpwd(email);
		boolean isValidPassword = BCrypt.checkpw(password, dbpass);
		System.out.println("email: " + email + ", password: " + password + ", dbpass: " + dbpass);
		System.out.println("mailchk: " + mailchk + ", isValidPassword" + isValidPassword);
		
		if (mailchk==1 && isValidPassword) {
			// 메일과 비밀번호 모두 일치
			dto.setEmail(email);
			dto.setCheck_item("valid");;
		} else if (mailchk==1) {
			// 메일 일치 / 비밀번호 불일치
			dto.setCheck_item("pwfalse");
		} else {
			// 메일부터 틀렸음
			dto.setCheck_item("mailfalse");
		}
		return dto;
	}
	/*
		메일 중복검사
		: 카카오 kuser TB, user TB 체크
	*/
	@GetMapping("/signupcheck/email")
	public UserDto mailCheck(@RequestParam String email) {
		UserDto dto = new UserDto();
		String res = "";
		int isKakao = udao.apiUserCheck(email); 
		int isuser = udao.mailCheck(email);
		
		if (isKakao == 0 && isuser == 0) {
			// 메일 사용가능
			res = "usethis";
		} else if (isKakao == 0 && isuser != 0) {
			// 메일 중복
			res = "usenot";
		} else if (isKakao != 0) {
			// 카카오로 로그인하세요
			res = "kakaouser";
		}
		
		dto.setCheck_item(res);
		return dto;
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
		카카오 로그인
	*/
	@PostMapping("/kakaologin")
	public UserDto kakaoLogin(@RequestBody String kakao) {
		HashMap<String, Object> userInfo = react.getUserInfo(kakao);
		
		UserDto udto = new UserDto();
		String kakaoId = userInfo.get("kakaoId").toString();
		String email = userInfo.get("email").toString();
		String nickname = userInfo.get("nickname").toString();
		String profile_url = userInfo.get("profile_url").toString();
		String access_token = userInfo.get("access_token").toString();
		
		// 카카오 TB에 있는지 확인
		int kakaomatch = udao.apiUserCheck(kakaoId);
		if (kakaomatch != 0) {
			// 카카오 TB에 있으면, user TB 의 프로필사진 update
			UserDto dto = new UserDto();
			dto.setEmail(email);
			dto.setProfile_url(profile_url);
			udao.updateApiUser(dto);
		} else {
			// 카카오 TB에 없으면, user TB에 있는지 확인
			int mailmatch = udao.mailCheck(email);
			if (mailmatch == 0) {
				// user TB에도 없으면
				// kakao & user TB insert
				
				// password 난수발생코드
				Random ran = new Random(System.currentTimeMillis());
				int charcnt = randomChar.length;
				StringBuffer buff = new StringBuffer();
				
				for (int i=0; i<8; i++) {
					buff.append(randomChar[ran.nextInt(charcnt)]);
				}
				String password = buff.toString();
				
				// set
				UserDto dto = new UserDto();
				dto.setId(kakaoId);
				dto.setEmail(email);
				dto.setPassword(password);
				dto.setNickname(nickname);
				dto.setProfile_url(profile_url);
				
				udao.insertApiUser(dto);
				udao.insertUser(dto);
			} else {
				// 이미 가입한 유저면
				// kakao TB insert & user TB 프로필사진 update
				UserDto dto = new UserDto();
				dto.setEmail(email);
				dto.setProfile_url(profile_url);
				
				udao.updateApiUser(dto);
			}
		}
		
		udto.setId(Integer.toString(udao.selectIdUser(email)));
		udto.setEmail(email);
		udto.setAccess_token(access_token);
		
		return udto;
	}
	/*
		카카오 로그아웃
	*/
	@GetMapping("/klogout")
	public String klogout(@RequestParam String access_token) {
		System.out.println("access_token: " + access_token);
		
		return "kuser logout";
	}
	
	/*
		카카오 연결 끊기(탈퇴)
	*/
	@GetMapping("/kdelete")
	public String kdelete(@RequestBody UserDto dto) {
		String access_token = dto.getAccess_token();
		String email = dto.getEmail();
		System.out.println("access_token: " + access_token);	// 확인
		
		kakao.deleteUser(access_token);
		udao.deleteApiUser(email);
		
		return "kuser deleted";
	}
	
	
	/*
		비밀번호 변경 인증코드 메일 발송
	*/
	@GetMapping("/login/changepwd")
	public String changepwd(@RequestParam String email) {
		// password 난수발생코드
		Random ran = new Random(System.currentTimeMillis());
		int charcnt = randomChar.length;
		StringBuffer buff = new StringBuffer();
		
		for (int i=0; i<8; i++) {
			buff.append(randomChar[ran.nextInt(charcnt)]);
		}
		String rancode = buff.toString();
		
		String subject = "MEchelin 가이드 비밀번호 변경 인증메일입니다";
		String content = "<h3>인증코드는 <b>" + rancode +"</b> 입니다.</h3><br>"
				+ "<h5>이 코드는 5분간 유효합니다</h5>";
		MimeMessage message = sender.createMimeMessage();
		
		try {
			message.setSubject(subject);
			message.setText(content, "UTF-8", "html");
			message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			
			sender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("mail sending error: " + e.getMessage());
		}
		
		return rancode;
	}
	
	/*
	 * 이메일로 id 반환하는 메소드
	 */
	@GetMapping("/select/id")
	public int selectIdUser(@RequestParam String email) {
		return udao.selectIdUser(email);
	}
	
	
	/*
		비밀번호 변경
	*/
	@PostMapping("/changepwd/reset")
	public void changePW(
		@RequestBody UserDto dto
	) {
		String pwdhash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", dto.getEmail());
		map.put("password", pwdhash);
		
		udao.changePwd(map);
	}
	
	/*
		소개글 변경
	*/
	@PostMapping("/changeintro")
	public void changeIntro(
		@RequestBody UserDto dto
	) {
		udao.changeIntro(dto);
	}
	/*
		닉네임 변경
	*/
	@PostMapping("/changenick")
	public void changeNick(
		@RequestBody UserDto dto
	) {
		udao.changeNick(dto);
	}
	
	/*
		회원 탈퇴
	*/
	@GetMapping("/dropout")
	public void dropUser(@RequestParam String id) {
		udao.dropUser(id);
	}
	
}
