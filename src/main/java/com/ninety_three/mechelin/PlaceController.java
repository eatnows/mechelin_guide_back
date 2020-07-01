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
	/*
	 * 내 친구들의 맛집을 조회하는 메소드
	 */
	@GetMapping("/friendsplace")
	public List<PostDto> selectFriendsPlace(@RequestParam int user_id){
		List<PostDto> list = dao.selectFriendsPlace(user_id);
		return list;
	}
	/*
	 * 나와 친구의 맛집 모두를 조회하는 메소드
	 */
	@GetMapping("/allplace")
	public List<PostDto> selectAllPlace(@RequestParam int user_id){
		List<PostDto> list = dao.selectALLPlace(user_id);
		return list;
	}
	
	
	/*
	 * 나의 등록 맛집 총 갯수 반환
	 */
	@GetMapping("/totalcount")
	public int selectTotalCount(@RequestParam int user_id) {
		return 0; //dao.selectCountWishList(user_id);
	}
	/*
	 * 페이징처리 데이터 반환
	 */
	@GetMapping("/pagedata")
	public List<PostDto> selectPageData(@RequestParam int pageStart, @RequestParam int perPageNum, @RequestParam int user_id){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		System.out.println("실행됨");
		map.put("pageStart", pageStart);
		map.put("perPageNum", perPageNum);
		map.put("user_id", user_id);
		List<PostDto> list = null;
		// list = dao.selectDataWishList(map);
		return list;
	}
	
}
