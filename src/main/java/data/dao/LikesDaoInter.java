package data.dao;

import java.util.HashMap;

import data.dto.LikesDto;

public interface LikesDaoInter {
	public void insertPostLikes(LikesDto dto);
	public void updatePostLikes(HashMap<String, Integer> map);
	public Integer selectPostLikes(HashMap<String, Integer> map);
}
