package data.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.UserDto;

@Repository
public class UserDao extends SqlSessionDaoSupport implements UserDaoInter {

	@Override
	public void insertUser(UserDto dto) {
		// TODO Auto-generated method stub
		System.out.println("dao insert user called");
		getSqlSession().insert("insertOfUser", dto);
	}

	@Override
	public int mailCheck(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao mail check called");
		return getSqlSession().selectOne("mailCheckOfUser", email);
	}

	@Override
	public int nickCheck(String nickname) {
		// TODO Auto-generated method stub
		System.out.println("dao nick check called");
		return getSqlSession().selectOne("nickCheckOfUser", nickname);
	}

	@Override
	public void insertValid(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao insert valid called");
		getSqlSession().insert("holdInfoForMailValid", email);
	}
	
	@Override
	public void updateValid(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao update valid called");
		getSqlSession().update("againSendMailValid", email);
	}

	@Override
	public int hasInfo(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao hasinfo(valid) called");
		return getSqlSession().selectOne("didSendMailValid", email);
	}
	
	@Override
	public void gainValid(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao gain valid called");
		getSqlSession().update("joinGrantedOfMailValid", email);
	}

	@Override
	public boolean isGranted(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao is granted?(valid) called");
		return getSqlSession().selectOne("isUserDidMailValid", email);
	}
	
	@Override
	public void deleteValid(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao delete valid called");
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
		System.out.println("dao get pwd called");
		return getSqlSession().selectOne("getPassOfUser", email);
	}

	@Override
	public void changePwd(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePassOfUser", map);
	}

	@Override
	public int apiUserCheck(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao api user check called");
		return getSqlSession().selectOne("checkOfApiUser", email);
	}

	@Override
	public void insertApiUser(UserDto dto) {
		// TODO Auto-generated method stub
		System.out.println("dao insert api user called");
		getSqlSession().insert("insertOfApiUser", dto);
	}
	
	@Override
	public void updateApiUser(UserDto dto) {
		// TODO Auto-generated method stub
		System.out.println("dao update api user called");
		getSqlSession().update("updateProfileOfApiUser", dto);
	}

	@Override
	public void deleteApiUser(String email) {
		// TODO Auto-generated method stub
		System.out.println("dao delete api user called");
		getSqlSession().delete("deleteOfApiUser", email);
	}
	/*
	 * 이메일로 유저 id 반환
	 */
	@Override
	public int selectIdUser(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIdOfUser", email);
	}

}
