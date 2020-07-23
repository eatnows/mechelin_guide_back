package com.ninety_three.mechelin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dao.ChatDaoInter;
import data.dao.FriendsDaoInter;
import data.dao.UserDaoInter;
import data.dto.FriendsDto;
import data.dto.UserDto;

@RestController
@CrossOrigin
@RequestMapping("/friends")
public class FriendsController {
	
	@Autowired
	FriendsDaoInter fdao;
	
	@Autowired
	UserDaoInter udao;
	
	// 채팅에 관한 dao
	@Autowired
	ChatDaoInter chdao;
	
	@Autowired
	private JavaMailSender sender;
	
	@PostMapping("/checkfriend")
	public String checkFriend(@RequestBody FriendsDto dto) {
		// 테이블에 있는가?
		if(fdao.haveData(dto) == 0) {
			// 테이블에도 없으면
			return "not a friend";
		} else {
			// 테이블에 있으면,
			// 친구 수락 상태인가?
			if(fdao.isFriend(dto)) {
				return "friend";
			} else {
				// 마지막 신청 시간과 현재 시간 비교
				long updated_at = fdao.howRecent(dto).getTime();
				Date now = new Date();
				
				long compare = TimeUnit.MILLISECONDS.toDays(now.getTime() - updated_at);
				// 이 사람이 신청한 적 있는가?
				int isfirst = fdao.isFirst(dto);
				if(isfirst == 1) {
					// 본인이 신청했다면
					if(compare < 3) {
						// 신청한 지 3일 미만이면
						return "wait";
					} else {
						return "send again";
					}
				} else {
					// 상대가 자신에게 요청했다면
					if(compare < 3) {
						// 신청한 지 3일 미만이면
						return "accept";
					}
				}
				// 친구 신청한 지 3일 이상이면
				// request 본인이 재신청, 또는 target 상대가 새로 신청 가능
				return "not a friend";
			}
		}
	}
	
	@PostMapping("/confirmfriend")
	public String confirmFriend(@RequestBody FriendsDto dto) {
		// 테이블에 있는가?
		if(fdao.isMyFriend(dto) == 0 ) {
			return "0";
		} else {
			return "1";
		}
	}
	
	
	@PostMapping("/addfriend")
	public void addFriend(@RequestBody FriendsDto dto) {
		//int target_user_id = udao.selectIdUser(dto.getEmail());
		int target_user_id;
		if(dto.getEmail() == null) {
			target_user_id = dto.getTarget_user_id();			
		} else {
			target_user_id = Integer.parseInt(udao.selectIdUser(dto.getEmail()).getId());
		}
		dto.setTarget_user_id(target_user_id);
		
		int havedata = fdao.haveData(dto);
		if (havedata == 0) {
			// 양쪽 다 첫 신청일 경우 TB insert
			fdao.addFriend(dto);
		} else {
			// 신청내역이 있을 경우
			fdao.updateFriend(dto);
		}
		
		// 친구신청 알림메일 보내기
		String email = fdao.getMailAddr(dto.getTarget_user_id());
		
		String subject = "MEchelin 가이드 친구요청 메일입니다.";
		String clickurl = "http://localhost:9000/mechelin/friends/acceptfriend"
				+ "?request=" + dto.getRequest_user_id()
				+ "&target=" + dto.getTarget_user_id();
		String content = "<a href='" + clickurl + "'>수락하려면 클릭하세요</a>";
		
		MimeMessage message = sender.createMimeMessage();
		
		try {
			message.setSubject(subject);
			message.setText(content, "UTF-8", "html");
			message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			
			sender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("mail sending error: " + e.getMessage());
		}
	}
	
	/*
		친구수락
	*/
	@GetMapping("/acceptfriend")
	public void acceptFriend(
		@RequestParam String request,
		@RequestParam String target
	) {
		FriendsDto dto = new FriendsDto();
		dto.setRequest_user_id(Integer.parseInt(request));
		dto.setTarget_user_id(Integer.parseInt(target));
		
		fdao.acceptFriend(dto);
		
		// 친구가 수락되었을때 채팅방 생성
		chdao.insertChatRoom();
		// 방금 생성한 채팅방의 id로 dm_member 테이블 insert
		int chatroom_id = chdao.selectLatelyChatRoom();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chatroom_id", chatroom_id);
		map.put("user_id", request);
		map.put("title", ""); // 채팅방이름
		chdao.insertDmMember(map);
		map.put("user_id", target);
		chdao.insertDmMember(map);
	}
	
	/*
		언팔로우/친구거절
	*/
	@PostMapping("/deletefriend")
	public void deleteFriend(@RequestBody FriendsDto dto) {
		// 친구를 삭제했을 경우 채팅방 삭제
		// 채팅방 id를 얻기
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myUserId", dto.getRequest_user_id());
		map.put("friendUserId", dto.getTarget_user_id());
		if(chdao.selectIdChatRoom(map) != null) {
			int chatroom_id = chdao.selectIdChatRoom(map);			
			System.out.println(chatroom_id);
			// 채팅방 삭제
			chdao.deleteChatRoom(chatroom_id);
		}
		
		// 친구삭제
		fdao.deleteFriend(dto);
		
	}
	
	/*
		유저프로필
	*/
	@GetMapping("/profile")
	public UserDto getUserProfile(@RequestParam String id) {
		return udao.getUserProfile(id);
	}
	
	/*
	 * 나의 모든 친구 데이터 반환
	 */
	@GetMapping("/myfriends")
	public List<UserDto> selectAllFriends(@RequestParam int user_id, 
			@RequestParam int pageStart, @RequestParam int perPageNum){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("user_id", user_id);
		map.put("pageStart", pageStart);
		map.put("perPageNum", perPageNum);
		List<UserDto> list = fdao.selectAllFriends(map);
		return list;
	}
	/*
	 * 나의 친구수 반환
	 */
	@GetMapping("/mycount")
	public int selectCountFriends(@RequestParam int user_id) {
		return fdao.selectCountFriends(user_id);
	}
	
}
