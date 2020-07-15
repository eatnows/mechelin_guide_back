package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDao extends SqlSessionDaoSupport implements ChatDaoInter {
	
	/*
	 * chatroom 테이블
	 */
	@Override
	public void insertChatRoom() {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfChatRoom");
	}

	@Override
	public int selectLatelyChatRoom() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectLatleyChatRoom");
	}

	@Override
	public void deleteChatRoom(int id) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteOfChatRoom", id);
	}

	@Override
	public Integer selectIdChatRoom(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIdOfChatRoom", map);
	}
	
	/*
	 * dm_member 테이블
	 */
	@Override
	public void insertDmMember(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfdm_member", map);
	}
	
	/*
	 * chat 테이블
	 */
	@Override
	public void insertChat(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println(map);
		System.out.println("실행");
		getSqlSession().insert("insertOfChat", map);
	}

}
