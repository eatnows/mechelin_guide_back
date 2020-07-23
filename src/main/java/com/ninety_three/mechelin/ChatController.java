package com.ninety_three.mechelin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.ChatDaoInter;
import data.dao.TestDaoInter;
import data.dto.ChatDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatDaoInter dao;
	
	
	
	@GetMapping("/log")
	public List<ChatDto> selectChatContent(@RequestParam int chatroom_id, @RequestParam int page){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("chatroom_id", chatroom_id);
		map.put("page", page);
		System.out.println(map);
		List<ChatDto> list = dao.selectChatContent(map);
		System.out.println(list.size());
		return list;
	}
}
