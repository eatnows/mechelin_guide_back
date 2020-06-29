package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.UserPlaceDaoInter;
import data.dao.WishListDaoInter;
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
	public void insertWishList(@RequestBody WishListDto dto) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", dto.getUser_id());
		map.put("place_id", dto.getPlace_id());
		map.put("post_id", dto.getPost_id());
		
		String result = "";
		// 이미 찜등록이 되어있는지, 내 맛집에 등록된 곳인지 판단하여 추가
		if(dao.selectIsExistWishList(map) == 0 && updao.selectCheckUserPlace(map) == 0) {
			dao.insertWishList(map);
			result = "위시리스트에 추가 되었습니다!";
		} else {
			result = "이미 등록된 맛집입니다!";
		}
	}
	
	/*
	 * 삭제 버튼을 눌렀을시 위시 리스트에서 삭제
	 */
	@DeleteMapping("/delete")
	public void deleteWishList(@RequestParam int id) {
		dao.deleteClickWishList(id);
	}
}
