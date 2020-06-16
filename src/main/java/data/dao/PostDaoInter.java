package data.dao;

import data.dto.PostDto;

public interface PostDaoInter {
	public void insertPost(PostDto dto);
	public void deleteUserPost(int id);
	public void deleteAdminPost(int id);
}
