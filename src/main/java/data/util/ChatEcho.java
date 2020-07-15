package data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import data.dao.ChatDao;
import data.dao.ChatDaoInter;
import data.dto.UserDto;

@Component
public class ChatEcho extends TextWebSocketHandler {
	//List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	Map<String, WebSocketSession> userSessions = new HashMap<String, WebSocketSession>();
	
	@Autowired
	private ChatDao chdao;
	
	/*
	 * 커넥션이 연결됐을때
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		HttpSession hsession;
		System.out.println("웹소켓 연결");
		System.out.println("afterConnectionEstablished : " + session);
		String senderId = getId(session);
		userSessions.put(senderId, session);
		//sessions.add(session);
	}
	
	/*
	 * 소켓에 메시지를 보냈을때
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage : " + session + " : " + message);
		System.out.println("페이로드");
		System.out.println(message.getPayload());
		String senderId = getId(session);
		// protocol = 챗타입, 보낸사람, 받는사람, 메시지, 채팅방번호
		String msg = message.getPayload();
		if(!StringUtils.isEmpty(msg)) {
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 5) {
				String cmd = strs[0];
				String myUserId = strs[1];
				String friendUserId = strs[2];
				String sendMessage = strs[3];
				String chatRoomId = strs[4];
				
				// chat테이블 insert
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("chatroom_id", Integer.parseInt(chatRoomId));
				map.put("user_id", Integer.parseInt(myUserId));
				map.put("content", sendMessage);
				chdao.insertChat(map);
				
				WebSocketSession receiveUserSession = userSessions.get(friendUserId);
				if(receiveUserSession != null) {
					receiveUserSession.sendMessage(new TextMessage(sendMessage+","+chatRoomId));
				}
			}
			
			
		}
        
        
//		for(WebSocketSession sess : sessions) {
//			String senderId = session.getId();
//			sess.sendMessage(new TextMessage(senderId + " : " + message.getPayload()));
//		}
	}
	/*
	 * 커넥션이 클로즈됐을때
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionEstablished : " + session + " : " + status);
	}
	
	
	private String getId(WebSocketSession session) {
		// httpsession 접근 맵으로 접근, httpsession값들을 map에 넣어줌
		String sessionQurey = session.getUri().getQuery().substring(7);
		return sessionQurey;
		
	}

}
