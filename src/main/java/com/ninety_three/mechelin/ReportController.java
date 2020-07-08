package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.ReportDaoInter;
import data.dto.ReportDto;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportDaoInter dao;
	
	/*
	 * 리뷰글 신고하기
	 */
	@PostMapping("/add")
	public void insertReport(@RequestBody ReportDto dto) {
		dao.insertReport(dto);
	}
	
	/*
	 * 이미 신고한 게시글인지, report의 id를 반환
	 */
	@GetMapping("/isreport")
	public Integer selectIsReport(@RequestParam int user_id, @RequestParam int post_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("register_user_id", user_id);
		map.put("post_id", post_id);
		return dao.selectIsReport(map);
	}
}
