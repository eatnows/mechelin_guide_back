package data.dao;

import data.dto.UserDto;

public interface UserDaoInter {
	public void insertUser(UserDto dto);
	public int mailCheck(String email);
	public int nickCheck(String nickname);
	public String getpwd(String email);
}
