package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dao.PlaceDaoInter;
import data.dao.PostDaoInter;
import data.dao.UserPlaceDaoInter;
import data.dto.PlaceDto;
import data.dto.PostDto;
import data.dto.UserPlaceDto;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostDaoInter dao;
	@Autowired
	private PlaceDaoInter pdao;
	@Autowired
	private UserPlaceDaoInter updao;
	
	
	/*
	 * 리뷰글 등록하는 메소드
	 */
	@PostMapping("/add")
	public void insertPost(@RequestBody PostDto dto) {
		// DB에 해당 좌표값에 해당하는 맛집이 있는지 체크
		HashMap<String, Double> geomap = new HashMap<String, Double>();
		geomap.put("latitude_x", dto.getLatitude_x());
		geomap.put("longitude_y", dto.getLongitude_y());
		Integer placeId = 0;
		int user_place_id = 0;
		if(pdao.selectCheckPlace(geomap) == null) {
			// 좌표에 해당하는 맛집이 없으면 새로 추가
			PlaceDto pdto = new PlaceDto();
			pdto.setLatitude_x(dto.getLatitude_x());
			pdto.setLongitude_y(dto.getLongitude_y());
			pdto.setName(dto.getName());
			pdto.setAddress(dto.getAddress());
			pdao.insertPlace(pdto);
			// 방금 새로 등록한 맛집 id 값 반환
			placeId = pdao.selectLatelyId();
		} else {
			// 좌표에 해당하는 맛집이 있으면 해당 맛집 id 반환
			placeId = pdao.selectCheckPlace(geomap);
		}
		// user_place 테이블에 user의 id와 place의 id를 가진값이 있는지 확인
		HashMap<String, Integer> upmap = new HashMap<String, Integer>();
		upmap.put("user_id", dto.getUser_id());
		upmap.put("place_id", placeId);
		if(updao.selectCheckUserPlace(upmap) == null) {
			// null이면 값이 없다는 의미 새로 user_place 테이블에 insert
			UserPlaceDto updto = new UserPlaceDto();
			updto.setUser_id(dto.getUser_id());
			updto.setPlace_id(placeId);
			updto.setCategory(dto.getCategory());
			updao.insertUserPlace(updto);
			user_place_id = updao.selectLatelyUserPlace(dto.getUser_id()).getUser_id();
		} else {
			// 있으면 해당 데이터의 id를 반환
			user_place_id = updao.selectCheckUserPlace(upmap);
		}
		dto.setUser_place_id(user_place_id);
		// 대표이미지를 설정했는지 체크 없으면 기본 이미지 출력
		if(dto.getFront_image() == null) {
			dto.setFront_image("front_basic_image.jpg");
		}
		dao.insertPost(dto);
	}
}
