package com.ninety_three.mechelin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.CommentDaoInter;
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
	@Autowired
	private CommentDaoInter cdao;
	
	/*
	 * 리뷰글에 좋아요 버튼을 눌렀을때 실행되는 메소드
	 */
	@PostMapping("/post")
	public int insertPostLikes(@RequestBody LikesDto dto) {
		System.out.println("실행됨");
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
	
	@PostMapping("/comment")
	public String commLikes(@RequestBody LikesDto dto) {
		
		String comment_id = Integer.toString(dto.getComment_id());
		
		HashMap<String, Integer> lmap = new HashMap<String, Integer>();
		lmap.put("user_id", dto.getUser_id());
		lmap.put("comment_id", dto.getComment_id());
		
		// likes TB에 없을 경우
		if(dao.selectCommentLikes(lmap) == null) {
			// likes TB에 insert
			dao.insertCommentLikes(dto);
			
			// comment TB의 좋아요 +1
			HashMap<String, String> cmap = new HashMap<String, String>();
			cmap.put("comment_id", comment_id);
			cmap.put("islike", "up");
			cdao.updateCommentLikes(cmap);
		} else {		// likes TB에 있을 경우
			if(dao.selectCommentLikes(lmap) == 0) {
				// islike 가 false 일 경우, true로 update
				dao.updateCommentLikes(lmap);
				
				// comment TB 의 좋아요 +1
				HashMap<String, String> cmap = new HashMap<String, String>();
				cmap.put("comment_id", comment_id);
				cmap.put("islike", "up");
				cdao.updateCommentLikes(cmap);
			} else {
				// islike 가 true 일 경우, false 로 update
				lmap.put("comment_islike", 1);
				dao.updateCommentLikes(lmap);
				
				// comment TB 의 좋아요 -1
				HashMap<String, String> cmap = new HashMap<String, String>();
				cmap.put("comment_id", comment_id);
				cmap.put("islike", "down");
				cdao.updateCommentLikes(cmap);
			}
		}
		
		return cdao.getCommentLikes(comment_id);
	}
	/*
	 * 해당 리뷰글에 좋아요를 눌렀는지 판단하는 메소드
	 */
	@GetMapping("/ispost")
	public Integer isLikePost(@RequestParam int post_id, @RequestParam int user_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("post_id", post_id);
		map.put("user_id", user_id);
		System.out.println(dao.selectPostLikes(map));
		return dao.selectPostLikes(map);
	}
}
