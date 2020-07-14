package data.dto;

import java.sql.Timestamp;

public class ChatDto {
	private int id;
	private int chatroom_id;
	private int user_id;
	private String content;
	private Timestamp created_at;
	
	// dm_member 테이블
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChatroom_id() {
		return chatroom_id;
	}

	public void setChatroom_id(int chatroom_id) {
		this.chatroom_id = chatroom_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
