package data.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import data.dto.FriendsDto;
import data.dto.UserDto;

public interface FriendsDaoInter {
	public int haveData(FriendsDto dto);
	public boolean isFriend(FriendsDto dto);
	public int isFirst(FriendsDto dto);
	public Timestamp howRecent(FriendsDto dto);
	
	public void addFriend(FriendsDto dto);
	public void updateFriend(FriendsDto dto);
	public void acceptFriend(FriendsDto dto);
	
	public void deleteFriend(FriendsDto dto);
	
	public String getMailAddr(int user_id);
	public List<UserDto> selectAllFriends(HashMap<String, Integer> map);
	public int selectCountFriends(int user_id);
}
