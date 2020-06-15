package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.PlaceDaoInter;
import data.dto.PlaceDto;

@RestController
@CrossOrigin
@RequestMapping("/place")
public class PlaceController {
	
	@Autowired
	private PlaceDaoInter dao;
	
	/*
	 * place 테이블의 데이터 추가하는 메소드
	 */
	@PostMapping("/add")
	public void insertPlace(@RequestBody PlaceDto dto) {
		dao.insertPlace(dto);
	}
	/*
	 * 테스트용 메소드
	 */
	@PutMapping("/test/{id}")
	public void test(@PathVariable int id) {
		dao.updateWishMinus(id);
	}
}
