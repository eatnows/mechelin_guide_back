package data.dao;

import java.util.HashMap;

public interface ChatDaoInter {
	/*
	 * chatroom 테이블
	 */
	public void insertChatRoom();
	public int selectLatelyChatRoom();
	public void deleteChatRoom(int id);
	public Integer selectIdChatRoom(int user_id);
	
	/*
	 * dm_member 테이블
	 */
	public void insertDmMember(HashMap<String, Object> map);
	
	/*
	 * chat 테이블
	 */
	public void insertChat(HashMap<String, Object> map);
}
