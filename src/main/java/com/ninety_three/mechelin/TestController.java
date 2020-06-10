package com.ninety_three.mechelin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import data.dao.TestDaoInter;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
public class TestController {
	
	@Autowired
	private TestDaoInter dao;
	
	@ApiOperation(value = "디비연결확인")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "입력한이름", required = true, 
				dataType = "String", paramType = "query", defaultValue = "")
	})
	//paramType, dataType은 자동으로 채워지기 때문에 생략가능
	// 명시하고 싶을때는 paramType은 @requestparam일 경우 "query", @PathVariable일 경우 "path"를 적어준다

	@PostMapping("/signup")
	public void insertTest(@RequestParam("name") String name) {
		System.out.println("controller 실행됨");
		dao.insertTest(name);
	}
}
