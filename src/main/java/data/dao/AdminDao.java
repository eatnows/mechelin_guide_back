package data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import data.dto.UserDto;
@Repository
public class AdminDao extends SqlSessionDaoSupport implements AdminDaoInter{

	@Override
	public List<UserDto> selectAllOfUser(HashMap<String, Integer> map) {
		
		return getSqlSession().selectList("selectAllOfUser", map);
	}
	@Override
	public int selectCountOfUser() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCountOfUser");
	}
	@Override
	public List<UserDto> searchDataOfUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("searchDataOfUser", map);
	}
	@Override
	public List<UserDto> sortDataOfUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("sortDataOfUser", map);
	}
	@Override
	public List<UserDto> filterDataOfUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("filterDataOfUser", map);
	}
	@Override
	public List<UserDto> selectAllOfReport(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectAllOfReport", map);
	}
	@Override
	public int selectCountOfReport() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("selectCountOfReport");
	}
	@Override
	public List<UserDto> sortDataOfReport(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("sortDataOfReport", map);
	}
	@Override
	public void changeAuthorityOfReport(int id) {
		getSqlSession().update("changeAuthorityOfReport",id);
	}
	@Override
	public void deleteOfReport(int id) {
		getSqlSession().delete("deleteOfReport",id);
	}

}
