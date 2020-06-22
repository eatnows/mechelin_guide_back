package data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.CommentDto;

@Repository
public class CommentDao extends SqlSessionDaoSupport implements CommentDaoInter {

	@Override
	public void insertComment(CommentDto cdto) {
		// TODO Auto-generated method stub
		System.out.println("cdao insert comment called");
		getSqlSession().insert("insertOfComment", cdto);
	}

	@Override
	public List<CommentDto> getAllComments(String post_id) {
		// TODO Auto-generated method stub
		System.out.println("cdao get all comments called");
		return getSqlSession().selectList("listOfComments", post_id);
	}

	@Override
	public void updateCommentLikes(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		System.out.println("cdao update comment likes called");
		getSqlSession().update("updateLikesOfComment", map);
	}

	@Override
	public String getCommentLikes(String id) {
		// TODO Auto-generated method stub
		System.out.println("cdao get comment likes called");
		return getSqlSession().selectOne("likesOfComment", id);
	}

	@Override
	public void deleteComment(String id) {
		// TODO Auto-generated method stub
		System.out.println("cdao delete comment called");
		getSqlSession().update("hideOfDeletedComment", id);
	}

	@Override
	public void updateComment(CommentDto cdto) {
		// TODO Auto-generated method stub
		System.out.println("cdao update comment called");
		getSqlSession().update("updateOfComment", cdto);
	}
	
}
