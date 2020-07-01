package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.CommentDaoInter;
import data.dto.CommentDto;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	CommentDaoInter cdao;
	
	@PostMapping("/insertcomment")
	public String insertcomm(@RequestBody CommentDto cdto) {
		cdao.insertComment(cdto);
		return "check comment insert";
	}
	
	@GetMapping("/deletecomment")
	public String deleteComm(@RequestParam String comment_id) {
		cdao.deleteComment(comment_id);
		return "hide done";
	}
	
	@PostMapping("/getcomments")
	public List<CommentDto> getAllComments(@RequestBody LinkedHashMap<String, String> obj) {
		String post_id = obj.get("post_id");
		String user_id = obj.get("user_id");
		System.out.println("post_id: " + post_id + ", user_id: " + user_id);
		List<CommentDto> list = cdao.getAllComments(post_id);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		
		for(CommentDto dto: list) {
			// 좋아요 중인지 확인
			map.put("id", dto.getId());
			int now = cdao.getNowLiked(map);
			if (now == 0) {
				// 좋아요 없음
				dto.setNow_liked(false);
			} else {
				// 좋아요 있음
				dto.setNow_liked(true);
			}
		}
		return list;
	}
	
	@PostMapping("/updatecomment")
	public String updateComm(@RequestBody CommentDto dto) {
		cdao.updateComment(dto);
		return "check comment update";
	}
}
