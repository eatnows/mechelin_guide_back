package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.CommentDto;

public interface CommentDaoInter {
	public void insertComment(CommentDto cdto);
	public int getNowLiked(HashMap<String, String> map);
	public List<CommentDto> getAllComments(String post_id);
	public void updateCommentLikes(HashMap<String, String> map);
	public String getCommentLikes(String id);
	public void deleteComment(String id);
	public void updateComment(CommentDto cdto);
}
