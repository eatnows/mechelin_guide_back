package data.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao extends SqlSessionDaoSupport implements TestDaoInter {

	@Override
	public void insertTest(String name) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertOfTest", name);
	}

}
