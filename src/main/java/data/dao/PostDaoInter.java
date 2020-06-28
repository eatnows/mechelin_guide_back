package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.PostDto;

public interface PostDaoInter {
	public void insertPost(PostDto dto);
	public void deleteUserPost(int id);
	public void deleteAdminPost(int id);
	public void updateLikePost(HashMap<String, Integer> id);
	public PostDto selectDataPost(int id);
	public void updatePost(PostDto dto);
	public PostDto selectLatelyPost(int id);
	public List<PostDto> selectUPDataPost(HashMap<String, Integer> map);
	public List<PostDto> selectAllOfPost(HashMap<String, Integer> map);
	public void updateFrontImagePost(HashMap<String, Object> map);
	public List<PostDto> selectSearchPost(HashMap<String, Object> map);
}
