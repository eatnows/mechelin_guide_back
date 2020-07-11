package data.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.FriendsDto;
import data.dto.UserDto;

@Repository
public class FriendsDao extends SqlSessionDaoSupport implements FriendsDaoInter {

	@Override
	public int haveData(FriendsDto dto) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("isDataAtFriends", dto);
	}

	@Override
	public boolean isFriend(FriendsDto dto) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("isTrueOfFriends", dto);
	}

	@Override
	public int isFirst(FriendsDto dto) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("isFirstTime", dto);
	}

	@Override
	public Timestamp howRecent(FriendsDto dto) {
		// TODO Auto-generated method stub
		String when = getSqlSession().selectOne("whenAddedOfFriends", dto);
		return Timestamp.valueOf(when);
	}

	@Override
	public void addFriend(FriendsDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfFriends", dto);
	}

	@Override
	public void updateFriend(FriendsDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("addAgainOfFriends", dto);
	}
	
	@Override
	public void acceptFriend(FriendsDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().update("acceptOfFriends", dto);
	}

	@Override
	public void deleteFriend(FriendsDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteOfFriends", dto);
	}

	@Override
	public String getMailAddr(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("getMailAddrOfUser", user_id);
	}

	@Override
	public List<UserDto> selectAllFriends(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectAllFriendsOfUser", map);
	}

	@Override
	public int selectCountFriends(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCountFriendsOfUser", user_id);
	}

}
