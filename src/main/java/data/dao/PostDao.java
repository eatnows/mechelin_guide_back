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
	/*
	 * 유저가 게시글 삭제를 눌렀을때 실행되는 메소드
	 */
	@Override
	public void deleteUserPost(int id) {
		// TODO Auto-generated method stub
		getSqlSession().update("deleteUserOfPost", id);
	}
	/*
	 * 관리자가 게시글 삭제를 눌렀을때 실행되는 메소드
	 */
	@Override
	public void deleteAdminPost(int id) {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteAdminOfPost", id);
	}

}
