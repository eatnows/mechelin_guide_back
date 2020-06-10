package com.ninety_three.mechelin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SwaggerTestController {
	
	public string test() {
		return "성공";
	}
}
