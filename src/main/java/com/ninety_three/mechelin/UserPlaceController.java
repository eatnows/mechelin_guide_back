package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import data.dto.UserPlaceDto;

@Controller
@CrossOrigin
@RequestMapping("/userplace")
public class UserPlaceController {

	@Autowired
	private UserPlaceDto dto;
	
	
}
