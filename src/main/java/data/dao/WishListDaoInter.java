package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.PostDto;

public interface WishListDaoInter {
	public void insertWishList(HashMap<String, Integer> map);
	public int selectIsExistWishList(HashMap<String, Integer> map);
	public void deleteReviewWishList(HashMap<String, Integer> map);
	public void deleteClickWishList(int id);
	public List<PostDto> selectDataWishList(HashMap<String, Integer> map);
	public int selectCountWishList(int user_id);
	public int selectExistWishList(HashMap<String, Integer> map);
}
