package data.dto;

import java.sql.Timestamp;

public class PlaceDto {
	private int id;
	private double latitude_x;
	private double longitude_y;
	private String name;
	private String address;
	private int post_count;
	private int wish_count;
	private Timestamp create_at;
	private Timestamp update_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getPost_count() {
		return post_count;
	}
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}
	public int getWish_count() {
		return wish_count;
	}
	public void setWish_count(int wish_count) {
		this.wish_count = wish_count;
	}
	public Timestamp getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}
	public Timestamp getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
	}
	
}
