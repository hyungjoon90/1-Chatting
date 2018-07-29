package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;



import Client.Receive;
import Client.Send;

public class Client{
	public static String id;
	public static Ui01 ui01;
	public static String ipaddress="";
	static Socket sock;
	static PrintWriter pw;
	static int port=3000;
	static Ui02 ui02=new Ui02();
	public static void main(String[] args){
		int port=3000;
		ui01=new Ui01();
	}
	public static String connect(){
		String msg = null;//중복검사결과를 넣어 보낼 msg
		boolean result=false;//중복검사 결과 넣을 result
		try {
			InetAddress pingcheck = InetAddress.getByName(ipaddress);
			// 해당 서버에서 응답이 있을 경우 true 반환, 응답이 없을 경우 false 반환
			// isReachable 매개변수는 타임아웃 
			boolean isAlive = pingcheck.isReachable(1000);
			if(isAlive==false){return "isDead";}
			sock=new Socket(ipaddress,port);
			pw=new PrintWriter(sock.getOutputStream());
			pw.println("idID"+id);//아이디를 서버로 보냄
			pw.flush();
			BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			int cnt=0;
			while(true){//서버에서 중복검사를 하여 반환받을 while
				msg=br.readLine();//중복이면 true, 아니면 false
				if(!msg.isEmpty()){//중복이거나 아니거나 둘다 while 탈출
					break;
				}
				break;
				
			}//while end
			msg.toString();//boolean타입인 결과를 string을 변환
			if(msg.equals("false")){//아닐시 실행
				ui01.dispose();//로그인창 닫기
				ui02.setTitle(id);
				ui02.setVisible(true);//채팅창 띄움
				Receive receive=new Receive(sock,ui02);
				receive.start();
				Send send=new Send(sock, id,ui02);
				send.start();
			}//if end
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//try catch end
		return msg.toString();
	}//메서드 connect end
}//Class Client end
