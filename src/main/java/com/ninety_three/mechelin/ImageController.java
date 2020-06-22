package com.ninety_three.mechelin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.sun.mail.iap.ByteArray;

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
	public HashMap<String, Object> insertImage(@RequestParam MultipartFile images, @RequestParam int id) {
		ImageDto dto = new ImageDto();
		UUID uuid = UUID.randomUUID();
		String extension = images.getOriginalFilename().substring(images.getOriginalFilename().lastIndexOf("."), images.getOriginalFilename().length());
		
		String bucketName = "버킷이름";
		String fileName = "images/place/"+id+"/"+uuid+extension;
		File file = new File(images.getOriginalFilename());
		InputStream input = null;
		byte[] bytes; 		
		try {
			input = images.getInputStream();
			bytes = IOUtils.toByteArray(images.getInputStream());
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(images.getContentType());
			metadata.setContentLength(bytes.length);
			images.transferTo(file);
		} catch (IllegalStateException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			// s3에 업로드
			String path = s3.fileupload(bucketName, fileName, input, metadata);
			// image 테이블에 insert
			dto.setOrigin_name(images.getOriginalFilename());
			dto.setSave_name(uuid+extension);
			dto.setUrl(path);
			dao.insertImage(dto);
			// insert된 id의 값을 반환
			int image_id = dao.selectLatelyImage();
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", path);
			map.put("image_id", image_id);
			return map;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
}
