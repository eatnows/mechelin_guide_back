package data.dto;

import java.sql.Timestamp;

public class TestDto {
	private String name;
	private Timestamp create_at;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}
	
	
}
