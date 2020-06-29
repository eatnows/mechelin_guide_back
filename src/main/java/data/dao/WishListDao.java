package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao extends SqlSessionDaoSupport implements WishListDaoInter {
	/*
	 * 인설트 문
	 */
	@Override
	public void insertWishList(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfWishList", map);
	}

	@Override
	public int selectIsExistWishList(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIsExistOfWishList", map);
	}
	/*
	 * 해당 맛집에 대해 리뷰글을 썼을때 위시리스트에서 삭제
	 */
	@Override
	public void deleteReviewWishList(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteOfWishList", map);
	}

	@Override
	public void deleteClickWishList(int id) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteClickOfWishList", id);
	}

}
