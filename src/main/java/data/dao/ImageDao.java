package data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.ImageDto;

@Repository
public class ImageDao extends SqlSessionDaoSupport implements ImageDaoInter {

	@Override
	public void insertImage(ImageDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfImage", dto);
	}

	/*
	 * 리뷰글 등록버튼 눌렀을때 null 값이던 post_id를 변경하는 메소드
	 */
	@Override
	public void updatePostIdImage(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		getSqlSession().update("updatePostIdOFImage", map);
	}
	/*
	 * 가장 최근에 등록된 데이터의 id 반환
	 */
	@Override
	public int selectLatelyImage() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectLatelyOfImage");
	}
	/*
	 * post_id의 값이 null인채로 1일이 지난 데이터의 key_name 반환
	 */
	@Override
	public List<String> selectKeyNameImage() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectKeyNameOfImage");
	}

	@Override
	public void deleteDayImage() {
		// TODO Auto-generated method stub
		getSqlSession().delete("deleteDayOfImage");
	}

}
