package com.ninety_three.mechelin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.AdminDaoInter;
import data.dao.CommentDaoInter;
import data.dto.UserDto;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminDaoInter adao;
	
	@GetMapping("/user")
	public List<UserDto> selectAllOfUser(@RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.selectAllOfUser(map);
		return list;
	}
	@GetMapping("/usercount")
	public int selectCountOfUser() {
		return adao.selectCountOfUser();
	}
}
