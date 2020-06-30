package data.dto;

import java.sql.Timestamp;

public class UserDto {
	private String id;
	private String email;
	private String password;
	private String nickname;
	private String profile_url;
	private String pin_url;
	private String introduce;
	private String authority;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	private String access_token;
	private String check_item;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile_url() {
		return profile_url;
	}
	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}
	public String getPin_url() {
		return pin_url;
	}
	public void setPin_url(String pin_url) {
		this.pin_url = pin_url;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getCheck_item() {
		return check_item;
	}
	public void setCheck_item(String check_item) {
		this.check_item = check_item;
	}
	
}
