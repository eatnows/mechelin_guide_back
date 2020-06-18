package data.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.CommentDto;

@Repository
public class CommentDao extends SqlSessionDaoSupport implements CommentDaoInter {

	@Override
	public void insertComment(CommentDto cdto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfComment", cdto);
	}

	@Override
	public List<CommentDto> getAllComments(String post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCommentLikes(String id) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateLikesOfComment", id);
	}

	@Override
	public String getCommentLikes(String id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("likesOfComment", id);
	}

	@Override
	public void deleteComment(String id) {
		// TODO Auto-generated method stub
		getSqlSession().update("hideOfDeletedComment", id);
	}

	@Override
	public void updateComment(CommentDto cdto) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateOfComment", cdto);
	}
	
}
