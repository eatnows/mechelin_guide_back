package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.PlaceDto;

@Repository
public class PlaceDao extends SqlSessionDaoSupport implements PlaceDaoInter {

	@Override
	public void insertPlace(PlaceDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfPlace", dto);
	}

	/*
	 * post_count 칼럼 1증가 시키는 메소드
	 */
	@Override
	public void updatePostCount(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePostCountOfPlace", id);
	}
	
	/*
	 * post_count 칼럼의 값을 조회하는 메소드
	 */
	@Override
	public int selectPostCount(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectPostCountOfPlace", id);
	}
	/*
	 * wish_count 칼럼의 값을 1증가 시키는 메소드
	 */
	@Override
	public void updateWishCount(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateWishCountOfPlace", id);
	}
	/*
	 * wish_count 칼럼의 값을 조회 메소드
	 */
	@Override
	public int selectWishCount(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("place.selectWishCountOfPlace", id);
	}

	@Override
	public int selectLatelyId() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectLatelyOfPlace");
	}
	/*
	 * post_count 칼럼의 값을 1감소 시키는 메소드
	 */
	@Override
	public void updatePostMinus(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePostMinusOfPlace", id);
	}
	/*
	 * wish_count 칼럼의 값을 1감소 시키는 메소드
	 */
	@Override
	public void updateWishMinus(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateWishMinusOfPlace", id);
	}
	/*
	 * 관리자가 맛집을 수정
	 */
	@Override
	public void updatePlace(PlaceDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateOfPlace", dto);
	}
	
	

}
