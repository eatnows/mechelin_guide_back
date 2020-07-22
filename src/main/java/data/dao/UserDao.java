package data.dao;

import java.math.BigInteger;
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

	@Override
	public String getpwd(UserDto dto) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("getPassOfUser", dto);
	}

	@Override
	public void changePwd(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePassOfUser", map);
	}

	@Override
	public int apiUserCheck(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("checkOfApiUser", email);
	}

	@Override
	public void insertApiUser(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfApiUser", dto);
	}
	
	@Override
	public void updateApiUser(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateProfileOfApiUser", dto);
	}

	@Override
	public void deleteApiUser(String email) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteOfApiUser", email);
	}
	/*
	 * �씠硫붿씪濡� �쑀�� id 諛섑솚
	 */
	@Override
	public int selectIdUser(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIdOfUser", email);
	}

	@Override
	public UserDto getUserProfile(String id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("profileOfUser", id);
	}
	/*
	 * �봽濡쒗븘 �씠誘몄� 蹂�寃쏀븯�뒗 硫붿냼�뱶
	 */
	@Override
	public void updateProfileImageUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateProfileImgOfUser", map);
	}

	@Override
	public void updateMarkerImageUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateMarkerImgOfUser", map);
	}
	
	@Override
	public void changeIntro(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateIntroduceOfUser", dto);
	}
	
	@Override
	public void changeNick(UserDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateNicknameOfUser", dto);
	}

	@Override
	public void dropUser(String id) {
		// TODO Auto-generated method stub
		getSqlSession().update("deleteAccountOfUser", id);
	}

	@Override
	public void insertNaverUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfnaveruser", map);
	}

	@Override
	public int selectExistNaverUser(int naverid) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIsExistNaverUser", naverid);
	}

	@Override
	public int selectGetUserIdNaver(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectUserIdOfNaver", email);
	}

	@Override
	public int selectCountEmailUser(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("SelctCountEmailUser", email);
	}

	@Override
	public void insertOfGoogleUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfGoogleUser", map);
	} 
	
	@Override
	public int existGoogleUser(BigInteger googleId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("existGoogleUser", googleId);
	}

	@Override
	public int selectUserIdOfGoogle(String email) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectUserIdOfGoogle", email);
	}
}

