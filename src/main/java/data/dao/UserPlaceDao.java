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

}
