package data.dao;

import java.util.HashMap;

public interface WishListDaoInter {
	public void insertWishList(HashMap<String, Integer> map);
	public int selectIsExistWishList(HashMap<String, Integer> map);
	public void deleteReviewWishList(HashMap<String, Integer> map);
	public void deleteClickWishList(int id);
}
