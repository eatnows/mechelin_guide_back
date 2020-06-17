package data.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.CommentDto;

@Repository
public class CommentDao extends SqlSessionDaoSupport implements CommentDaoInter {

	@Override
	public void insertComment(CommentDto cdto) {
		// TODO Auto-generated method stub
		
	}
	
}
