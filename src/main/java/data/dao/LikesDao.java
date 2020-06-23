package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.LikesDto;

@Repository
public class LikesDao extends SqlSessionDaoSupport implements LikesDaoInter {
	/*
	 * 리뷰글에 있는 좋아요 버튼 누를시 테이블에 데이터 생성
	 */
	@Override
	public void insertPostLikes(LikesDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertPostOflikes", dto);
	}
	/*
	 * 좋아요를 취소 했을경우 post_islikes의 값을 false로 반환 false에서 true
	 */
	@Override
	public void updatePostLikes(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePostOflikes", map);
	}

	/*
	 * user_id와 post_id를 가진 데이터의 post_islikes 반환
	 */
	@Override
	public Integer selectPostLikes(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectPostOflikes", map);
	}
	
	/*
		이 댓글, 이 유저의 좋아요 기록이 있는가?
	*/
	@Override
	public Integer selectCommentLikes(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCommentOflikes", map);
	}
	/*
		likes TB insert
	*/
	@Override
	public void insertCommentLikes(LikesDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertCommentOflikes", dto);
	}
	/*
		likes TB update
	*/
	@Override
	public void updateCommentLikes(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updateCommentOflikes", map);
	}

}
