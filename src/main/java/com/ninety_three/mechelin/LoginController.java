package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.UserDaoInter;
import data.dto.UserDto;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	private UserDaoInter udao;
	
	@GetMapping("/testlogin")
	public String loginPage() {
		return "usertest";
	}
	
	/*
		메일/비밀번호 검사
		: 메일O 비번O "valid", 메일O 비번X "pwfalse", 메일X "mailfalse"
	*/
	@PostMapping("/login")		// 임시매핑
	public String loginResult(@RequestParam String email, @RequestParam String password) {
		if (udao.mailCheck(email)==1 && udao.loginMatch(email, password)==1) {
			return "valid";
		} else if (udao.mailCheck(email)==1) {
			return "pwfalse";
		} else {
			return "mailfalse";
		}
	}
	
	/*
		메일 중복검사
		: 사용가능 "usethis", 중복 "usenot"
	*/
	@GetMapping("/signupcheck/email")
	public String mailCheck(@RequestParam String email) {
		String result = "";
		if (udao.mailCheck(email) == 0) {
			result = "usethis";
		} else {
			result = "usenot";
		}
		return result;
	}
	
	/*
		회원가입
		: 실행 시 "success" 리턴
	*/	
	@PostMapping("/signup")		// 임시매핑
	public String signUp(@RequestBody UserDto dto) {
		udao.insertUser(dto);
		return "success";
	}
}
