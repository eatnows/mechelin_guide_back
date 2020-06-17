package data.dao;

import java.util.Map;

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
	public void insertValid(String email) {
		// TODO Auto-generated method stub
		getSqlSession().insert("holdInfoForMailValid", email);
	}
	
	@Override
	public void updateValid(String email) {
		// TODO Auto-generated method stub
		getSqlSession().update("againSendMailValid", email);
	}

	@Override
	public int hasInfo(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("didSendMailValid", email);
	}
	
	@Override
	public void gainValid(String email) {
		// TODO Auto-generated method stub
		getSqlSession().update("joinGrantedOfMailValid", email);
	}

	@Override
	public boolean isGranted(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("isUserDidMailValid", email);
	}
	
	@Override
	public void deleteValid(String email) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteMailValidWhenJoined", email);
	}

	/*
	@Override
	public String whenAdded(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("whenSendMailValid", email);
	}
	*/

	@Override
	public String getpwd(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("getPassOfUser", email);
	}

	@Override
	public int apiUserCheck(String id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("checkOfApiUser", id);
	}

	@Override
	public void insertApiUser(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfApiUser", dto);
	}

}