package data.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PostDto {
	private int id;
	private int user_place_id;
	private String subject;
	private String content;
	private float rating;
	private int likes;
	private String front_image;
	private Boolean isdelete;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp created_at;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp updated_at;
	// user_place 테이블 참조
	private int user_id;
	private String category;
	private int post_count;
	// 맛집 이름 place 테이블 참조
	private String name;
	private String address;
	private double latitude_x;
	private double longitude_y;
	// user 테이블 참조
	private String nickname;
	private String profile_url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_place_id() {
		return user_place_id;
	}
	public void setUser_place_id(int user_place_id) {
		this.user_place_id = user_place_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getFront_image() {
		return front_image;
	}
	public void setFront_image(String front_image) {
		this.front_image = front_image;
	}
	public Boolean getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
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
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPost_count() {
		return post_count;
	}
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude_x() {
		return latitude_x;
	}
	public void setLatitude_x(double latitude_x) {
		this.latitude_x = latitude_x;
	}
	public double getLongitude_y() {
		return longitude_y;
	}
	public void setLongitude_y(double longitude_y) {
		this.longitude_y = longitude_y;
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
	
}