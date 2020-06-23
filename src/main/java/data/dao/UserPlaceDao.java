package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.UserPlaceDto;

@Repository
public class UserPlaceDao extends SqlSessionDaoSupport implements UserPlaceDaoInter {

	@Override
	public void insertUserPlace(UserPlaceDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfUserPlace", dto);
	}
	
	/*
	 * user_id와 place_id에 해당하는 데이터가 있는지 확인하는 메소드 
	 * 			있으면 id값을, 없으면 null값을 반환
	 */
	@Override
	public Integer selectCheckUserPlace(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCheckOfUserPlace", map);
	}
	/*
	 * 가장 최근에 insert된 데이터 하나를 반환하는 메소드
	 */
	@Override
	public UserPlaceDto selectLatelyUserPlace(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectLatelyOfUserPlace", user_id);
	}
	/*
	 * post_count 칼럼의 값을 1증가 시키는 메소드
	 */
	@Override
	public void updatePlusUserPlace(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePlusOfUserPlace", id);
	}
	/*
	 * post_count 칼럼의 값을 1감소 시키는 메소드
	 */
	@Override
	public void updateMinusUserPlace(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateMinusOfUserPlace", id);
	}

	@Override
	public void updateIsShowUserPlace(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateIsShowOfUserPlace", map);
	}
	/*
	 * user_place의 id로 place의 id를 반환하는 메소드
	 */
	@Override
	public int selectPostIdUserPlace(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectPlaceIdOfUserPlace", id);
	}

}
