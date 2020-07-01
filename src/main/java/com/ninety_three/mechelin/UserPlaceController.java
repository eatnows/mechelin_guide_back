package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.UserPlaceDao;
import data.dao.UserPlaceDaoInter;
import data.dto.PostDto;
import data.dto.UserPlaceDto;

@RestController
@CrossOrigin
@RequestMapping("/userplace")
public class UserPlaceController {

	@Autowired
	private UserPlaceDaoInter dao;
	
	/*
	 *  user_id와 place_id에 해당하는 데이터가 있는지 확인 있으면 id 없으면 null 반환
	 */
	@GetMapping("/testId")
	public Integer test(@RequestParam Integer user_id, @RequestParam Integer place_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", user_id);
		map.put("place_id", place_id);
		
		return dao.selectCheckUserPlace(map);
	}
	/*
	 * 나의 맛집 지도상에 안보이게 하는 필터기능 무조건 true값(혹은 1)을 보내야함
	 */
	@PutMapping("/show/{id}/{isshow}")
	public void test2(@PathVariable int id, @PathVariable boolean isshow) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isshow", isshow);
		map.put("id", id);
		dao.updateIsShowUserPlace(map);
	}
	
	/*
	 * 나의 총 맛집 갯수 반환
	 */
	@GetMapping("/count")
	public int selectCountMyPlace(@RequestParam int user_id) {
		return dao.selectCountMyPlace(user_id);
	}
	/*
	 * 나의 맛집 리스트 출력
	 */
	@GetMapping("/myplace")
	public List<PostDto> selectMyPlace(@RequestParam int user_id, @RequestParam int pageStart, @RequestParam int perPageNum){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", user_id);
		map.put("pageStart", pageStart);
		map.put("perPageNum", perPageNum);
		List<PostDto> list = dao.selectMyPlace(map);
		return list;
	}
	
	/*
	 * 블랙 리스트 추가
	 */
	@PutMapping("/blacklist/{user_place_id}")
	public String updateBlacklist(@PathVariable int user_place_id) {
		String str;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_place_id", user_place_id);
		// 블랙리스트인지 아닌지 확인
		if(dao.selectIsBlackList(user_place_id) == 1) {
			// 블랙 리스트면 1을 반환 1을 보내서 블랙리스트를 해제
			map.put("blacklist", 1);
			dao.updateBlackList(map);
			str = "블랙리스트에서 제외되었습니다.";
		} else {
			// 블랙리스트가 아니면 0을 보내 블랙리스트에 추가
			map.put("blacklist", 0);
			dao.updateBlackList(map);
			str = "블랙리스트에 추가되었습니다.";
		}
		return str;
	}
	
	/*
	 *  블랙리스트인지 아닌지 확인
	 */
	@GetMapping("/blacklist/exist")
	public int selectBlackListExist(@RequestParam int user_place_id) {
		return dao.selectIsBlackList(user_place_id);
	}
}
