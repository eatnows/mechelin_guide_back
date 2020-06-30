package kakao.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import data.dto.UserDto;

@Service
public class SetKakaoApi {

	public String getAccessToken(String authorize_code) {
		String access_token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=71100263fd4bab7558fb465089e72859");
			sb.append("&redirect_uri=http://localhost:9000/mechelin/klogin");
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
			
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("kakao get tokens method error: " + e.getMessage());
		}

		return access_token;
	}

	public HashMap<String, Object> getUserInfo(String access_token) {

		// map 사용: 카카오에도 없는 정보가 있을 수 있음
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			
			// 연결 성공 시 200 (콘솔 확인용)
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// Json 형태의 데이터 String 으로 받기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// String result 를 객체화
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			
			String kakaoId = element.getAsJsonObject().get("id").getAsString();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").toString();
			email = email.replace("\"", "");
			System.out.println("email 따옴표 제거 : " + email);
			
			userInfo.put("kakaoId", kakaoId);
			userInfo.put("nickname", nickname);
			userInfo.put("profile_url", profile_image);
			userInfo.put("email", email);
			
			// map에 element 객체를 통으로 넣으면 받을 때 어떻게 받는지 알아내야함...
			// rfc 3339 parsing 아직 해결 못 함
			userInfo.put("user", element);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("get userinfo of kakao error: " + e.getMessage());
		}

		return userInfo;
	}
	
	
	public void kakaoLogout(String access_token) {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String result = "";
	        String line = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println(result);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        System.out.println("kakao logout method error: " + e.getMessage());
	    }
	}
	
	
	public void deleteUser(String access_token) {
		String reqURL = "https://kapi.kakao.com/v1/user/unlink";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        System.out.println("kakao delete method error: " + e.getMessage());
	    }
	}

}

/*
	(REST API 구현 시, 컨트롤러 부분)
	
	@GetMapping("/klogin")
	public UserDto klogin(
			@RequestParam String code
	) {
		System.out.println("authorize_code: " + code);	// 확인
		
		UserDto udto = new UserDto();
		String kakaoId = "";
		String email = "";
		String password = "";
		String nickname = "";
		String profile_url = "";
		
		// 인증코드를 카카오로 보내서 액세스 토큰 받아오기
		String access_token = kakao.getAccessToken(code);
		System.out.println("accessToken: " + access_token);		// 확인
		
		// 액세스 토큰을카카오로  보내서 유저 기본정보 받아오기
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_token);
		kakaoId = userInfo.get("kakaoId").toString();
		email = userInfo.get("email").toString();
		nickname = userInfo.get("nickname").toString();
		profile_url = userInfo.get("profile_url").toString();
		
		// 카카오 TB에 있는지 확인
		int kakaomatch = udao.apiUserCheck(kakaoId);
		if (kakaomatch != 0) {
			// 카카오 TB에 있으면 프로필사진 update
			UserDto dto = new UserDto();
			dto.setEmail(email);
			dto.setProfile_url(profile_url);
			udao.updateApiUser(dto);
		} else {
			// user TB에 있는지 확인
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
				password = buff.toString();
				System.out.println(password);		// 확인
				
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
				// kakao TB insert & user TB update
				UserDto dto = new UserDto();
				dto.setId(kakaoId);
				dto.setEmail(email);
				dto.setProfile_url(profile_url);
				
				udao.insertApiUser(dto);
				udao.updateApiUser(dto);
			}
		}
		
		udto.setEmail(email);
		udto.setAccess_token(access_token);
		
		return udto;
	}
*/