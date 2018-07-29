package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Receive extends Thread{
	public Socket sock;
	BufferedReader br;
	Ui02 ui02;
	String[] msgs;
	public Receive(Socket sock,Ui02 ui02){//Client에서 생성할때 생성자
		this.sock=sock;//소켓
		this.ui02=ui02;//채팅 ui02의 주소
	}
	
	public void run() {
		try {
			br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String msg;//전송받는 데이터
			while(true){
				msg=br.readLine();//서버에서 전송되는 문장
				if(msg.equals(null)){break;}
				
				if(msg.contains("list")){//접속자 목록이 온건지 확인
					ui02.ta2.setText("");//접속자 목록을 비움
					msgs=msg.split("list");//접속자 목록의 분할
					for (int i = 1; i < msgs.length; i++) {//접속자 목록에 띄움
						ui02.ta2.append(msgs[i]+"\n");
					}//for end
				}else{
					ui02.ta1.append(msg+"\n");//채팅창에 띄움
				}//if end
			}//while end
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//try catch finally end
	}//메서드 run end
}//Class Receive end
