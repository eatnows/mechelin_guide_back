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

	@Override
	public Integer selectCheckUserPlace(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCheckOfUserPlace", map);
	}

}
