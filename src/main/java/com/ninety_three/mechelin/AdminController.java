package com.ninety_three.mechelin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/searchdata")
	public List<UserDto> searchDataOfUser(@RequestParam String searchData, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("searchData",searchData);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.searchDataOfUser(map);
		return list;
	}
	
	@GetMapping("/sortdata")
	public List<UserDto> sortDataOfUser(@RequestParam String sorting, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("sorting",sorting);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.sortDataOfUser(map);
		return list;
	}
	
	@GetMapping("/filterdata")
	public List<UserDto> filterDataOfUser(@RequestParam String authority, @RequestParam String dropuser, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("authority",authority);
		map.put("dropuser",dropuser);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.filterDataOfUser(map);
		return list;
	}
	

}
