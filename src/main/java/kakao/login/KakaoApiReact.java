package kakao.login;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoApiReact {
	public HashMap<String, Object> getUserInfo(String kakao) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(kakao);
		
		// 토큰 값 읽기
		JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
		String access_token = response.get("access_token").getAsString();
		String refresh_token = response.get("refresh_token").getAsString();
		// 반환할 토큰
		userInfo.put("access_token", access_token);
		userInfo.put("refresh_token", refresh_token);
		
		// 유저 정보 얻기
		JsonObject profile = element.getAsJsonObject().get("profile").getAsJsonObject();
		String kakaoId = profile.get("id").getAsString();
		String nickname = profile.getAsJsonObject("properties").get("nickname").getAsString();
		String profile_image = profile.getAsJsonObject("properties").get("profile_image").getAsString();
		String email = profile.getAsJsonObject("kakao_account").get("email").getAsString();
		// 반환할 정보
		userInfo.put("kakaoId", kakaoId);
		userInfo.put("nickname", nickname);
		userInfo.put("profile_url", profile_image);
		userInfo.put("email", email);
		
		return userInfo;
	}
	
	public void parseTest(LinkedHashMap<String, LinkedHashMap> kakao) {
		System.out.println(kakao.getClass());
		System.out.println(kakao.get("response"));
		System.out.println(kakao.get("response").getClass());
		LinkedHashMap<String, Object> response = kakao.get("response");
		System.out.println(response);
		//JsonObject response = new JsonObject(kakao);
		//String res = mapper.writeValueAsString(kakao.get("response"));
	}
}
