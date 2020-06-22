package data.dao;

import java.util.HashMap;
import java.util.List;

import data.dto.ImageDto;

public interface ImageDaoInter {
	public void insertImage(ImageDto dto);
	public void updatePostIdImage(HashMap<String, Integer> map);
	public int selectLatelyImage();
	public List<String> selectKeyNameImage();
	public void deleteDayImage();
}
