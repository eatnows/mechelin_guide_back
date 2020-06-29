package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.AskDaoInter;
import data.dto.AskDto;

@RestController
@CrossOrigin
@RequestMapping("/ask")
public class AskController {
	
	@Autowired
	private AskDaoInter dao;
	
	@PostMapping("/add")
	public void insertAsk(@RequestBody AskDto dto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", dto.getUser_id());
		map.put("subject", dto.getSubject());
		map.put("content", dto.getContent());
		dao.insertAsk(map);
	}
	
	@GetMapping("/askdata")
	public List<AskDto> selectAskUser(@RequestParam int user_id){
		List<AskDto> list = dao.selectAskUser(user_id);
		return list;
	}
}
