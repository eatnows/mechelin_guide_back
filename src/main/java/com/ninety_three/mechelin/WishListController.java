package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
		HashMap<String, Integer> map2 = new HashMap<String, Integer>();
		map.put("user_id", dto.getUser_id());
		map.put("place_id", dto.getPlace_id());
		map.put("post_id", dto.getPost_id());
		System.out.println(map);
		
		map2.put("user_id", dto.getUser_id());
		map2.put("place_id", dto.getPlace_id());
		System.out.println(map2);
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
		System.out.println("result"+result);

		return result;
	}
	
	/*
	 * 삭제 버튼을 눌렀을시 위시 리스트에서 삭제
	 */
	@DeleteMapping("/delete")
	public void deleteWishList(@RequestParam int id) {
		dao.deleteClickWishList(id);
	}
	
	/*
	 * 나의 위시 리스트 전체 목록 조회
	 */
	@GetMapping("/alldata")
	public List<PostDto> selectDataWishList(@RequestParam int user_id){
		
		List<PostDto> list = dao.selectDataWishList(user_id);
		return list;
	}
}
