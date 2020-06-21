package com.ninety_three.mechelin;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import data.dao.ImageDaoInter;
import data.dto.ImageDto;

@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private ImageDaoInter dao;
	
	/*
	 * image 테이블에 데이터 insert
	 */
	@PostMapping("/add")
	public void insertImage(@RequestParam MultipartFile images) {
		System.out.println(images.getOriginalFilename());
	}
}
