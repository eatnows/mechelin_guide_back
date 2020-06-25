package com.ninety_three.mechelin;

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
	
	@GetMapping("/getcomments")
	public List<CommentDto> getAllComments(@RequestParam String post_id) {
		return cdao.getAllComments(post_id);
	}
	
	@PostMapping("/updatecomment")
	public String updateComm(@RequestBody CommentDto dto) {
		cdao.updateComment(dto);
		return "check comment update";
	}
}
