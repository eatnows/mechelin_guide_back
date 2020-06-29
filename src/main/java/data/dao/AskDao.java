package data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.AskDto;

@Repository
public class AskDao extends SqlSessionDaoSupport implements AskDaoInter {

	@Override
	public void insertAsk(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfAsk", map);
	}

	@Override
	public List<AskDto> selectAskUser(int user_id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectAskUserOfAsk", user_id);
	}

	
}
