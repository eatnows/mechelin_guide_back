package com.ninety_three.mechelin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import data.dao.ImageDaoInter;
import data.dto.ImageDto;
import data.util.AwsS3;



@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private ImageDaoInter dao;
	@Autowired
	//private AwsS3ImageUpload S3;
	private AwsS3 s3; 
	
	ObjectMetadata metadata = new ObjectMetadata();

	
	
	
	/*
	 * image 테이블에 데이터 insert
	 */
	@PostMapping("/add")
	public HashMap<String, String> insertImage(@RequestParam MultipartFile images, @RequestParam int id) {
		System.out.println("메소드는 실행됨");
		Date today = new Date();
		System.out.println(id);
		UUID uuid = UUID.randomUUID();
		String extension = images.getOriginalFilename().substring(images.getOriginalFilename().lastIndexOf("."), images.getOriginalFilename().length());
		
		String bucketName = "버킷이름";
		String fileName = "images/place/"+id+"/"+uuid+extension;
		File file = new File(images.getOriginalFilename());
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(images.getBytes());
			fos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("업로드도됨");
			String jsonStr = s3.fileupload(bucketName, fileName, file);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("data", jsonStr);
			return map;

			// json으로 변경
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
}
