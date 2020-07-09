package data.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.dto.UserDto;

public interface AdminDaoInter {
 public List<UserDto> selectAllOfUser(HashMap<String, Integer> map); 
 public int selectCountOfUser(); 
}
