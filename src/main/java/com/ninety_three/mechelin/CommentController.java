package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import data.dao.CommentDaoInter;
import data.dto.CommentDto;

@Controller
public class CommentController {
	
	@Autowired
	CommentDaoInter cdao;
	
	@GetMapping("/commtest")
	public String commtest() {
		return "commenttest";
	}
	
	@PostMapping("/insertcomm")
	public String insertcomm(@ModelAttribute CommentDto cdto) {
		cdao.insertComment(cdto);
		return "check DB";
	}
	
	@GetMapping("/upcommlike")
	public String commLikes(@RequestParam String comment_id) {
		cdao.updateCommentLikes(comment_id);
		return cdao.getCommentLikes(comment_id);
	}
	
	@GetMapping("/deletecomm")
	public String deleteComm(@RequestParam String comment_id) {
		cdao.deleteComment(comment_id);
		return "hide accessed";
	}
	
}
