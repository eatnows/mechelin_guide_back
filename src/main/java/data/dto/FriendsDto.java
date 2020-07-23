package data.dto;

import java.sql.Timestamp;

public class FriendsDto {
	private int request_user_id;
	private int target_user_id;
	private Timestamp updated_at;
	private String email;
	
	public int getRequest_user_id() {
		return request_user_id;
	}
	public void setRequest_user_id(int request_user_id) {
		this.request_user_id = request_user_id;
	}
	public int getTarget_user_id() {
		return target_user_id;
	}
	public void setTarget_user_id(int target_user_id) {
		this.target_user_id = target_user_id;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
