package data.dao;

import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.ReportDto;

@Repository
public class ReportDao extends SqlSessionDaoSupport implements ReportDaoInter {

	@Override
	public void insertReport(ReportDto dto) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfReport", dto);
	}

	@Override
	public Integer selectIsReport(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectIsReportOfReport", map);
	}

}
