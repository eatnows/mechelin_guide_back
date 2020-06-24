package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import data.dto.PostDto;

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
	 * 내가 찍은 좌표 주위에 맛집 리스트 출력
	 */
	@GetMapping("/around/place")
	public List<PlaceDto> test(@RequestParam double x, @RequestParam double y) {
		double x1 = x + 0.001;
		double x2 = x - 0.001;
		double y1 = y + 0.001;
		double y2 = y - 0.001;
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("x1", x1);
		map.put("x2", x2);
		map.put("y1", y1);
		map.put("y2", y2);
		List<PlaceDto> list = dao.selectAroundPlace(map);
		return list;
	}
	
	/*
	 * 나의 맛집을 조회하는 메소드
	 */
	@GetMapping("/myplace")
	public List<PostDto> selectMyPlace(@RequestParam int user_id){
		System.out.println("시작됨");
		List<PostDto> list = dao.selectMyPlace(user_id);
		return list;
	}
}
