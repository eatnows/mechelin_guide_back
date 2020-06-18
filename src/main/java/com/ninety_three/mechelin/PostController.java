package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public PostDto insertPost(@RequestBody PostDto dto) {
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
		// 해당 맛집에 전체 리뷰글 수 1증가
		pdao.updatePostCount(placeId);
		
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
			user_place_id = updao.selectLatelyUserPlace(dto.getUser_id()).getId();
		} else {
			// 있으면 해당 데이터의 id를 반환
			user_place_id = updao.selectCheckUserPlace(upmap);
			// id의 해당하는 리뷰글 수 1증가
			updao.updatePlusUserPlace(user_place_id);
		}
		// 게시글 등록을 위해 user_place_id의 값을 dto에 넣어줌
		dto.setUser_place_id(user_place_id);
		
		// 대표이미지를 설정했는지 체크 없으면 기본 이미지 출력
		if(dto.getFront_image() == null) {
			dto.setFront_image("front_basic_image.jpg");
		}
		dao.insertPost(dto);
		// 방금 작성한 글의 id값과 user_place_id값을 반환
		return dao.selectLatelyPost(user_place_id);
	}
	
	/*
	 * 유저가 삭제버튼을 눌렀을때 실행되는 메소드
	 */
	@PutMapping("/delete/{id}")
	public void deleteUserPost(@PathVariable int id) {
		dao.deleteUserPost(id);
	}
	/*
	 * 관리자가 삭제버튼을 눌렀을때 실제 삭제되는 메소드
	 */
	@DeleteMapping("/admin/delete")
	public void deleteAdminPost(@RequestParam int id) {
		dao.deleteAdminPost(id);
	}
	/*
	 * 리뷰글 수정, 대표이미지는 수정이 가능
	 * 하지만 내용 이미지까지는 아직 모르겠음
	 */
	@PutMapping("/update")
	public PostDto updatePost(@RequestBody PostDto dto) {
		dao.updatePost(dto);
		// 수정된 데이터 다시 전송
		return dao.selectDataPost(dto.getId());
	}
	/*
	 * user_place_id의 해당하는 모든 리뷰글 데이터 반환
	 */
	@GetMapping("/review")
	public List<PostDto> selectDataPost(@RequestParam int user_place_id) {
		
		return dao.selectUPDataPost(user_place_id);
	}
}
