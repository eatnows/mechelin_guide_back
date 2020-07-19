package data.dao;

import java.util.HashMap;

import data.dto.UserDto;

public interface UserDaoInter {
	public void insertUser(UserDto dto);
	public int mailCheck(String email);
	public int nickCheck(String nickname);
	
	public void insertValid(String email);
	public void updateValid(String email);
	
	public int hasInfo(String email);
//	public String whenAdded(String email);
	public void gainValid(String email);
	public boolean isGranted(String email);
	public void deleteValid(String email);
	
	public String getpwd(UserDto dto);
	public void changePwd(HashMap<String, String> map);
	
	public int apiUserCheck(String email);
	public void insertApiUser(UserDto dto);
	public void updateApiUser(UserDto dto);
	public void deleteApiUser(String email);
	
	public int selectIdUser(String email);
	public UserDto getUserProfile(String id);
	public void updateProfileImageUser(HashMap<String, Object> map);
	public void updateMarkerImageUser(HashMap<String, Object> map);
	
	public void changeIntro(UserDto dto);
	public void changeNick(UserDto dto);
	public void dropUser(String id);
	
	public void insertNaverUser(HashMap<String, Object> map);
	public int selectExistNaverUser(int naverid);
	public int selectGetUserIdNaver(String email);
	public int selectCountEmailUser(String email);
}
