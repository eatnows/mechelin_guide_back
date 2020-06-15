package data.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.PostDto;

@Repository
public class PostDao extends SqlSessionDaoSupport implements PostDaoInter {

	@Override
	public void insertPost(PostDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfPost", dto);
	}

}
