package data.dao;

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

}
