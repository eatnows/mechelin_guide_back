package data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.PlaceDto;
import data.dto.PostDto;

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
	/*
	 * 내가 찍은 좌표 근처에 place 테이블에 등록된 맛집 목록 출력
	 */
	@Override
	public List<PlaceDto> selectAroundPlace(HashMap<String, Double> map) {
		// TODO Auto-generated method stub
		//List<PlaceDto> list 
		return getSqlSession().selectList("selectAroundOfPlace", map);
	}
	/*
	 * 맛집 삭제
	 */
	public void deletePlace(int id) {
		getSqlSession().delete("deleteOfPlace", id);
	}
	/*
	 * 좌표에 해당하는 값이 DB에 있는지 확인하는 메소드
	 */
	@Override
	public Integer selectCheckPlace(HashMap<String, Double> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCheckOfPlace", map);
	}
	/*
	 * 나의 맛집을 조회하는 메소드
	 */
	@Override
	public List<PostDto> selectMyPlace(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectMyPlaceOfPlace", user_id);
	}
	/*
	 * 내 친구들의 맛집을 조회하는 메소드
	 */
	@Override
	public List<PostDto> selectFriendsPlace(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectFriendsPlaceOfPlace", user_id);
	}

	@Override
	public List<PostDto> selectALLPlace(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectAllPlaceOfPlace", user_id);
	}
	

}
