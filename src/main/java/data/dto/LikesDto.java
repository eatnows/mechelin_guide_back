package data.dto;

import java.sql.Timestamp;

public class LikesDto {
	private int id;
	private int user_id;
	private int post_id;
	private Boolean post_islike;
	private int comment_id;
	private Boolean comment_islike;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public Boolean getPost_islike() {
		return post_islike;
	}
	public void setPost_islike(Boolean post_islike) {
		this.post_islike = post_islike;
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public Boolean getComment_islike() {
		return comment_islike;
	}
	public void setComment_islike(Boolean comment_islike) {
		this.comment_islike = comment_islike;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}
