package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Server.Server;

public class Send extends Thread{

	Socket sock;
	String id;
	BufferedReader br;
	PrintWriter pw;
	Ui02 ui02;
	public Send(Socket sock,String id,Ui02 ui02){//client에서 생성하는 생성자
		this.sock=sock;//소켓
		this.id=id;//아이디
		this.ui02=ui02;//채팅창 ui02의 주소
	}
	
	public void run() {
		
		try {
		br=new BufferedReader(new InputStreamReader(System.in));
			pw=new PrintWriter(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("idID"+id);//아이디를 서버로 전달
		pw.flush();
		
		while(true){
			try {//클릭했을때 전송하기위해 사용/사용하지 않으면 전송이 잘 안됨
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//try catch end
			if(ui02.msg!="~!@#"){//ui02의 msg에는 전송될 문장이 저장된다.기본은로 ~!@#가 저장되어있다. 그 문자가 바뀔때 실행
				pw.println(ui02.msg);//바뀐 문장을 전달
				pw.flush();
				ui02.msg="~!@#";//다시 기본문자으로 바꿈
			}else if(ui02.msg=="EXIT"){
				pw.println(ui02.msg);//종료를 눌렀을때 보낼 문장
				pw.flush();
			}//if end
		}//while end
	}//메서드 run end
}//Class Send end