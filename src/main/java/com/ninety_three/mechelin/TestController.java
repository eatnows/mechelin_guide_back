package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import data.dao.TestDaoInter;

@Controller
public class TestController {
	
	@Autowired
	private TestDaoInter dao;
	
	@PostMapping("/signup")
	public void insertTest(@RequestParam String name) {
		System.out.println("controller 실행됨");
		dao.insertTest(name);
	}
}
