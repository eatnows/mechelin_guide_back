package com.ninety_three.mechelin;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

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
		String id = "";
		String email = "";
		String password = udto.getPassword();
		String dbpass = "";

		boolean isValidPassword = false;
		int userchk = 0;

		// 유저 있는지 확인
		if (udto.getEmail() != null) {
			// 이메일이 왔을 경우
			email = udto.getEmail();
			// 이메일 있는지 확인
			userchk = udao.mailCheck(email);
		} else {
			// 아이디가 왔을 경우
			id = udto.getId();
			udto.setId(id);

			userchk = 1;
		}

		// 유저가 있으면
		if(userchk == 1) {
			// 비밀번호 입력값/DB 일치 확인
			dbpass = udao.getpwd(udto);
			isValidPassword = BCrypt.checkpw(password, dbpass);

			// 비밀번호 일치 확인
			if(isValidPassword) {
				// 비번 일치
				udto.setCheck_item("valid");
			} else {
				// 불일치
				udto.setCheck_item("pwfalse");
			}
		} else {
			// 유저부터가 없음
			udto.setCheck_item("mailfalse");
		}

		return udto;
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

		udto.setId(udao.selectIdUser(email).getId());
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
	 * 이메일로 id, authority 반환하는 메소드
	 */
	@GetMapping("/select/id")
	public UserDto selectIdUser(@RequestParam String email) {
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
		if (dto.getEmail() != null) {
			map.put("email", dto.getEmail());
		} else {
			map.put("id", dto.getId());
		}

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
	@PostMapping("/dropout")
	public UserDto dropUser(@RequestBody UserDto dto) {
		// ID/PW 일치 검증
		dto = loginResult(dto);

		// 일치하면 탈퇴 실행
		if (dto.getCheck_item() == "valid") {
			udao.dropUser(dto.getId());
		}

		return dto;
	}


	/*
	 * 네이버 로그인
	 */
	@PostMapping("/naverlogin")
	public int naverLogin(@RequestBody String token) throws ParseException {
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		String naverToken = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(token);
			naverToken = (String) jsonObj.get("token");
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String header = "Bearer " + naverToken;
		String apiURL = "https://openapi.naver.com/v1/nid/me";
		Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);
		JSONObject response = null;
		JSONObject naverUserInfo = null;
		response = (JSONObject) jsonParse.parse(responseBody);
		naverUserInfo = (JSONObject) jsonParse.parse(response.get("response").toString());
		String id = (String) naverUserInfo.get("id");
		String nickname = (String) naverUserInfo.get("nickname");
		String profile_url = (String) naverUserInfo.get("profile_image");
		String email = (String) naverUserInfo.get("email");

		int user_id;
		// naver login 테이블에 있는지 확인
		if(udao.selectExistNaverUser(Integer.parseInt(id)) == 0) {
			// 없으면 우리 db에 등록된 아이디 인지 확인
			if(udao.selectCountEmailUser(email) == 0) {
				UserDto newdto = new UserDto();
				newdto.setEmail(email);
				newdto.setPassword("");
				newdto.setNickname(nickname);
				newdto.setProfile_url(profile_url);
				// 우리 db에 insert
				udao.insertUser(newdto);
			}
			// email에 대한 id번호를 얻기
			user_id = Integer.parseInt((udao.selectIdUser(email).getId()));
			// naver login 테이블에 insert
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("email", email);
			map.put("naverid", id);
			map.put("nickname", nickname);
			map.put("user_id", user_id);
			udao.insertNaverUser(map);
		}
		return udao.selectGetUserIdNaver(email);
	}

	private String get(String apiURL, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiURL);
		try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
	}

	private String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
	}

	private HttpURLConnection connect(String apiURL) {
		try {
            URL url = new URL(apiURL);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
	}

	@PostMapping("/tokenlogin")
	public int googleLogin(@RequestBody String idtoken) throws IOException {
		// RestTemplate 에 MessageConverter 세팅
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new FormHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(converters);

		// parameter 세팅
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("str", "thisistest");

		// REST API 호출
		String result = restTemplate.postForObject("https://oauth2.googleapis.com/tokeninfo?id_token="+idtoken, param, String.class);
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = null;
		int answer=0;
		String email = null;
		try {
			jsonObj = (JSONObject) jsonParse.parse(result);
			//값을 가져왔을 때 이메일이 유효한 경우에만 함수 실행
			if(jsonObj.get("email_verified").equals("true")) {
				BigInteger googleId = new BigInteger(jsonObj.get("sub").toString());
				email = jsonObj.get("email").toString();
				//구글 테이블에 값이 있는지 확인
				if(udao.existGoogleUser(googleId)==0) {
					// 없으면 우리 db에 등록된 아이디 인지 확인
					String name = jsonObj.get("name").toString();
					String pictureUrl = jsonObj.get("picture").toString();
					if(udao.selectCountEmailUser(email) == 0) {
						// 중복된 이름이 있을 수 있기에 이메일 앞 아이디를 닉네임으로 함
						String[] nickname = email.split("@");
						UserDto newdto = new UserDto();
						newdto.setEmail(email);
						newdto.setPassword("");
						newdto.setNickname(nickname[0]);
						newdto.setProfile_url(pictureUrl);
						// 우리 db에 insert
						udao.insertUser(newdto);
					}
					// email에 대한 id번호를 얻기
					int user_id =Integer.parseInt((udao.selectIdUser(email).getId()));
					// google login 테이블에 insert
					HashMap<String, Object> map = new HashMap<String, Object>();
					// 중복된 이름이 있을 수 있기에 이메일 앞 아이디를 닉네임으로 함
					String[] nickname = email.split("@");
					System.out.println(nickname[0]);
					map.put("email", email);
					map.put("googleId", googleId);
					map.put("nickname", nickname[0]);
					map.put("user_id", user_id);
					udao.insertOfGoogleUser(map);
					System.out.println(map);

				}
				//구글 테이블에 값이 있는 경우 실행
				answer = udao.selectUserIdOfGoogle(email);
				
			}else{
				System.out.println("invalid token id.");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(answer==0) {
			return 0;
			
		}else {
			return answer;
		}
	}
}
