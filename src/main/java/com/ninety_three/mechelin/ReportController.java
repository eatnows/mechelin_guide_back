package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
