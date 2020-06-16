package com.ninety_three.mechelin;

import java.util.HashMap;

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
	@PutMapping("/test/{id}/{isshow}")
	public void test2(@PathVariable int id, @PathVariable boolean isshow) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isshow", isshow);
		map.put("id", id);
		dao.updateIsShowUserPlace(map);
	}
	
}
