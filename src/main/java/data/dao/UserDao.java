package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.UserDto;

@Repository
public class UserDao extends SqlSessionDaoSupport implements UserDaoInter {

	@Override
	public void insertUser(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfUser", dto);
	}

	@Override
	public int mailCheck(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("mailCheckOfUser", email);
	}

	@Override
	public int nickCheck(String nickname) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("nickCheckOfUser", nickname);
	}
	
	@Override
	public int loginMatch(String email, String password) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
		return getSqlSession().selectOne("matchDataOfUser", map);
	}
	
}
