package com.ninety_three.mechelin;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import data.dao.UserDaoInter;
import data.dto.ImageDto;
import data.util.AwsS3;



@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private ImageDaoInter dao;
	@Autowired
	private AwsS3 s3;
	@Autowired
	private UserDaoInter udao;
	
	ObjectMetadata metadata = new ObjectMetadata();
	String bucketName = "버킷이름";

	/*
	 * image 테이블에 데이터 insert
	 */
	@PostMapping("/add")
	public HashMap<String, Object> insertImage(@RequestParam MultipartFile images, @RequestParam int id) {
		ImageDto dto = new ImageDto();
		UUID uuid = UUID.randomUUID();
		String extension = images.getOriginalFilename().substring(images.getOriginalFilename().lastIndexOf("."), images.getOriginalFilename().length());
		
		
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
			dto.setKey_name(fileName);
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
	
	/*
	 * post_id가 null값인 채로 1일이 지나면 자동삭제
	 */
	@Scheduled(cron = "0 0 12 * * *")
	public void deleteAuto() {
		List<String> list = new ArrayList<String>();
		
		list = dao.selectKeyNameImage();
		for(int i=0; i<list.size(); i++) {
			s3.filedelete(bucketName, list.get(i));
		}
		dao.deleteDayImage();
	}
	
	/*
	 * profile image s3에 저장
	 */
	@PostMapping("/profile/image")
	public void profileImgUpload(@RequestParam MultipartFile avatar, @RequestParam int id){
		String extension = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().lastIndexOf("."), avatar.getOriginalFilename().length());
		String fileName = "images/profile/"+id+"/"+"profile_image_"+id+extension;
		File file = new File(avatar.getOriginalFilename());
		
		InputStream input = null;
			
		byte[] bytes; 		
		try {
			input = avatar.getInputStream();
			bytes = IOUtils.toByteArray(avatar.getInputStream());
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(avatar.getContentType());
			metadata.setContentLength(bytes.length);
			avatar.transferTo(file);
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
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("profile_url", path);
			// 프로필 url 주소 변경
			udao.updateProfileImageUser(map);
			
			int newWidth = 64;
			int newHeight = 69;
			// 프로필 사진으로 마커 만들기
			URL profileUrl = new URL(path);
			URL markerUrl = new URL("https://mechelinbucket.s3.ap-northeast-2.amazonaws.com/images/profle/hole_marker.png");
			BufferedImage profile = ImageIO.read(profileUrl);
			BufferedImage marker = ImageIO.read(markerUrl);
			// 마커 크기를 기준으로 크기를 구함
			int width = marker.getWidth();
			int height = marker.getHeight();
			BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			// 메모리 이미지에서 Graphics2D를 얻어온다
			Graphics2D g = tmp.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
			// 메모리 이미지에 그리자
			g.drawImage(profile, 9, 7, 35, 35, null, null);
			// 그 위에 덮을 이미지
			g.drawImage(marker, null, 0, 0);
	        
			// s3 저장을 위해 필요한 변수들
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        ImageIO.write(tmp, "png", os);
	        InputStream markerInput = new ByteArrayInputStream(os.toByteArray());
	        ObjectMetadata markerMetadata = new ObjectMetadata();
	        markerMetadata.setContentType("image/png");
	        String markerFilename = "images/profile/"+id+"/"+"marker_image_"+id+".png";
	        String markerPath = s3.fileupload(bucketName, markerFilename, markerInput, markerMetadata);
	        map.put("pin_url", markerPath);
	        udao.updateMarkerImageUser(map);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
