package data.dao;

import java.util.HashMap;

import data.dto.UserPlaceDto;

public interface UserPlaceDaoInter {
	public void insertUserPlace(UserPlaceDto dto);
	public Integer selectCheckUserPlace(HashMap<String, Integer> map);
	public UserPlaceDto selectLatelyUserPlace(int user_id);
	public void updatePlusUserPlace(int id);
	public void updateMinusUserPlace(int id);
	public void updateIsShowUserPlace(HashMap<String, Boolean> map);
}
