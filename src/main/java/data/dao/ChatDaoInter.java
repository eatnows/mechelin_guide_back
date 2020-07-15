package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.ChatDto;

public interface ChatDaoInter {
	/*
	 * chatroom 테이블
	 */
	public void insertChatRoom();
	public int selectLatelyChatRoom();
	public void deleteChatRoom(int id);
	public Integer selectIdChatRoom(HashMap<String, Integer> map);
	
	/*
	 * dm_member 테이블
	 */
	public void insertDmMember(HashMap<String, Object> map);
	
	/*
	 * chat 테이블
	 */
	public void insertChat(HashMap<String, Object> map);
	public List<ChatDto> selectChatContent(HashMap<String, Integer> map);
}
