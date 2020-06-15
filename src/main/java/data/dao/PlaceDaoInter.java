package data.dao;

import java.util.HashMap;

import data.dto.PlaceDto;

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
}
