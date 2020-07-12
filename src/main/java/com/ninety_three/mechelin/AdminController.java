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

	//유저 리스트 출력
	@GetMapping("/user")
	public List<UserDto> selectAllOfUser(@RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.selectAllOfUser(map);
		return list;
	}

	//유저 수 구하기
	@GetMapping("/usercount")
	public int selectCountOfUser() {
		return adao.selectCountOfUser();
	}

	//유저 검색
	@GetMapping("/searchdata")
	public List<UserDto> searchDataOfUser(@RequestParam String searchData, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("searchData",searchData);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.searchDataOfUser(map);
		return list;
	}
	
	//유저 권한으로 정렬
	@GetMapping("/sortdata")
	public List<UserDto> sortDataOfUser(@RequestParam String sorting, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("sorting",sorting);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		List<UserDto> list= adao.sortDataOfUser(map);
		return list;
	}
	
	//탈퇴 여부로 필터링
	@GetMapping("/filterdata")
	public List<UserDto> filterDataOfUser(@RequestParam String sorting, @RequestParam String filtering, @RequestParam int startPage, @RequestParam int dataCount){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("sorting",sorting);
		map.put("filtering",filtering);
		map.put("startPage",startPage);
		map.put("dataCount",dataCount);
		System.out.println(sorting+","+filtering+","+startPage+","+dataCount);
		List<UserDto> list= adao.filterDataOfUser(map);
		return list;
	}
	

}
