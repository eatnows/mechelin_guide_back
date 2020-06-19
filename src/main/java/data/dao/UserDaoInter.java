package data.dao;

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
	
	public String getpwd(String email);
	
	public int apiUserCheck(String email);
	public void insertApiUser(UserDto dto);
	public void updateApiUser(UserDto dto);
	public void deleteApiUser(String email);
}
