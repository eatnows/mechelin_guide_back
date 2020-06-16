package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dao.LikesDaoInter;
import data.dao.PostDaoInter;
import data.dto.LikesDto;

@RestController
@CrossOrigin
@RequestMapping("/likes")
public class LikesController {
	
	@Autowired
	private LikesDaoInter dao;
	@Autowired
	private PostDaoInter pdao;
	
	@PostMapping("/add")
	public int insertPostLikes(@RequestBody LikesDto dto) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", dto.getUser_id());
		map.put("post_id", dto.getPost_id());
		map.put("post_islike", dao.selectPostLikes(map));
		
		HashMap<String, Integer> pmap = new HashMap<String, Integer>();
		// post의 id값을 말함
		pmap.put("id", dto.getPost_id());
		pmap.put("likes", 1);
		if(dao.selectPostLikes(map) == null) {
			// null 이면 처음 좋아요 버튼을 누른다는 의미 likes 테이블의 insert
			dao.insertPostLikes(dto);
			// post의 좋아요 숫자 1 증가 (증가하고 싶으면 likes의 값을 1로 보낸다)
			pdao.updateLikePost(pmap);
		} else {
			// 이미 존재할 경우 post_islikes의 값을 보고 판단하여 좋아요 증감
			if(dao.selectPostLikes(map) == 0) {
				// 0이면 false, 즉 좋아요를 취소했던 경우이기 때문에 다시 좋아요 1증가
				dao.updatePostLikes(map);
				// post의 좋아요 숫자 1 증가 (증가하고 싶으면 likes의 값을 1로 보낸다)
				pdao.updateLikePost(pmap);
			} else {
				// 1이면 true, 현재 좋아요하고 있는 상태
				dao.updatePostLikes(map);
				// post의 좋아요 숫자 1 감소 (감소시키고 싶으면 likes의 값을 0을 보낸다)
				pmap.put("likes", 0);
				pdao.updateLikePost(pmap);
			}
		}
		// post_id에 좋아요 숫자 반환
		return pdao.selectDataPost(dto.getPost_id()).getLikes();
	}
}
