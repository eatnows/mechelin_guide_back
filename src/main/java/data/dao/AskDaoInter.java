package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.AskDto;

public interface AskDaoInter {
	public void insertAsk(HashMap<String, Object> map);
	public List<AskDto> selectAskUser(int user_id);
}
