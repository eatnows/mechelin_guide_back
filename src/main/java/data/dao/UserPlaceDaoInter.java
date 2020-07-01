package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.PostDto;
import data.dto.UserPlaceDto;

public interface UserPlaceDaoInter {
	public void insertUserPlace(UserPlaceDto dto);
	public Integer selectCheckUserPlace(HashMap<String, Integer> map);
	public UserPlaceDto selectLatelyUserPlace(int user_id);
	public void updatePlusUserPlace(int id);
	public void updateMinusUserPlace(int id);
	public void updateIsShowUserPlace(HashMap<String, Object> map);
	public int selectPostIdUserPlace(int id);
	public int selectCountMyPlace(int user_id);
	public List<PostDto> selectMyPlace(HashMap<String, Integer> map);
}
