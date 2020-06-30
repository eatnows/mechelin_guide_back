package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.UserPlaceDaoInter;
import data.dao.WishListDaoInter;
import data.dto.PostDto;
import data.dto.WishListDto;

@RestController
@CrossOrigin
@RequestMapping("/wishlist")
public class WishListController {
	
	@Autowired
	private WishListDaoInter dao;
	@Autowired
	private UserPlaceDaoInter updao;
	
	/*
	 * 찜 등록 
	 */
	@PostMapping("/add")
	public String insertWishList(@RequestBody WishListDto dto) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", dto.getUser_id());
		map.put("place_id", dto.getPlace_id());
		map.put("post_id", dto.getPost_id());
		System.out.println(map);

		System.out.println(dao.selectIsExistWishList(map));
		System.out.println(updao.selectCheckUserPlace(map));
		String result = "";
		// 이미 찜등록이 되어있는지, 내 맛집에 등록된 곳인지 판단하여 추가
		if(dao.selectIsExistWishList(map) == 0 && updao.selectCheckUserPlace(map) == null) {
			dao.insertWishList(map);
			result = "위시리스트에 추가 되었습니다!";
			System.out.println(result);
		} else {
			result = "이미 등록된 맛집입니다!";
			System.out.println(result);

		}
		return result;
	}
	
	/*
	 * 삭제 버튼을 눌렀을시 위시 리스트에서 삭제
	 */
	@DeleteMapping("/delete/{id}")
	public void deleteWishList(@PathVariable int id) {
		dao.deleteClickWishList(id);
	}
	
	/*
	 * 나의 위시 리스트 총 갯수 반환
	 */
	@GetMapping("/totalcount")
	public int selectTotalCount(@RequestParam int user_id) {
		return dao.selectCountWishList(user_id);
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
		List<PostDto> list = dao.selectDataWishList(map);
		return list;
	}
	
	/*
	 * 위시 리스트에 맛집이 추가되어있는지 확인하는 메소드
	 */
	@GetMapping("/exist")
	public int selectExistWishList(@RequestParam int user_id, @RequestParam int place_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", user_id);
		map.put("place_id", place_id);
		return dao.selectExistWishList(map);
	}
}
