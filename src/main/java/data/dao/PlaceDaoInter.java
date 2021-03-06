package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.PlaceDto;
import data.dto.PostDto;

public interface PlaceDaoInter {
	public void insertPlace(PlaceDto dto);
	public void updatePostCount(int id);
	public void updatePostMinus(int id);
	public int selectPostCount(int id);
	public void updateWishCount(int id);
	public void updateWishMinus(int id);
	public int selectWishCount(int id);
	public int selectLatelyId();
	public void updatePlace(PlaceDto dto);
	public List<PlaceDto> selectAroundPlace(HashMap<String, Double> map);
	public void deletePlace(int id);
	public Integer selectCheckPlace(HashMap<String, Double> map);
	public List<PostDto> selectMyPlace(int user_id);
	public List<PostDto> selectFriendsPlace(int user_id);
	public List<PostDto> selectALLPlace(int user_id);
}
