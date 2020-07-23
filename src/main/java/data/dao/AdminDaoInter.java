package data.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.dto.AskDto;
import data.dto.ReportDto;
import data.dto.UserDto;

public interface AdminDaoInter {
 public List<UserDto> selectAllOfUser(HashMap<String, Integer> map); 
 public int selectCountOfUser(); 
 public List<UserDto> searchDataOfUser(HashMap<String, Object> map); 
 public List<UserDto> sortDataOfUser(HashMap<String, Object> map); 
 public List<UserDto> filterDataOfUser(HashMap<String, Object> map);
 public int filterDataCountOfUser(HashMap<String, String> map); 
 public List<ReportDto> selectAllOfReport(HashMap<String, Integer> map); 
 public int selectCountOfReport(); 
 public List<ReportDto> sortDataOfReport(HashMap<String, Object> map); 
 public void changeAuthorityOfReport(int id); 
 public void deleteOfReport(int id); 
 public List<AskDto> selectAllOfAsk(HashMap<String, Integer> map); 
 public int selectCountOfAsk(); 
 public List<AskDto> searchDataOfAsk(HashMap<String, Object> map); 
 public void answerOfAsk(HashMap<String, Object> map); 
}
