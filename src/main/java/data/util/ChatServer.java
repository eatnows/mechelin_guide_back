package data.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatServer {
	private ServerSocket serverSocket;
	private Socket socket;
	
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	
//	public void setting() throws IOException {
//		Collections.synchronizedMap(clientsMap);
//		serverSocket = new ServerSocket(7777);
//		while(true) {
//			System.out.println("서버 대기중");
//			socket = serverSocket.accept();
//			System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
//			 Receiver receiver = new Receiver(socket);
//             receiver.start();
//		}
//		
//	}
//	
//	
//	 class Receiver extends Thread {
//	        private DataInputStream in;
//	        private DataOutputStream out;
//	        private String nick;
//	 
//	        /** XXX 2. 리시버가 한일은 자기 혼자서 네트워크 처리 계속..듣기.. 처리해주는 것. */
//	        public Receiver(Socket socket) throws IOException {
//	            out = new DataOutputStream(socket.getOutputStream());
//	            in = new DataInputStream(socket.getInputStream());
//	            nick = in.readUTF();
//	            addClient(nick, out);
//	        }
//	 
//	        public void run() {
//	            try {// 계속 듣기만!!
//	                while (in != null) {
//	                    msg = in.readUTF();
//	                    sendMessage(msg);
//	                }
//	            } catch (IOException e) {
//	                // 사용접속종료시 여기서 에러 발생. 그럼나간거에요.. 여기서 리무브 클라이언트 처리 해줍니다.
//	                removeClient(nick);
//	            }
//	        }
//	    }
}
