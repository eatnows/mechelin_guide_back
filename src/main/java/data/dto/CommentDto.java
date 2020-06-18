package data.dto;

import java.sql.Timestamp;

public class CommentDto {
	private String post_id;
	private String user_id;
	private String content;
	private int likes;
	private String parent_comment_id;
	private Timestamp created_at;
	
	// 유저 프로필 출력할 변수 추가해야 함
	
	
	public String getPost_id() {
		return post_id;
	}
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getParent_comment_id() {
		return parent_comment_id;
	}
	public void setParent_comment_id(String parent_comment_id) {
		this.parent_comment_id = parent_comment_id;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
}
