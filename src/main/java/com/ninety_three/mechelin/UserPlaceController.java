package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	
	@GetMapping("/test")
	public Integer test(@RequestParam Integer user_id, @RequestParam Integer place_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", user_id);
		map.put("place_id", place_id);
		
		return dao.selectCheckUserPlace(map);
	}
	
}
