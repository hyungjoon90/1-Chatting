package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {//접속을 받아 접속자들을 넘겨주는 서버클래스

	public static ArrayList<PrintWriter> list;//접속자목록 리스트선언
	public static ArrayList<String> idList;
	public static void main(String[] args) throws IOException{
		
		list=new ArrayList<PrintWriter>();//리스트 초기화
		idList=new ArrayList();//client에게 받은 id저장
		
			ServerSocket serv=new ServerSocket(3000);//소켓 포트번호 설정
			while(true){
				System.out.println("접속대기중");
				Socket sock=serv.accept();//접속받는문
				System.out.println(sock.getInetAddress());//콘솔에 ip출력
				
				//중복검사
				BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
				String msg=br.readLine();//client에서 id받음
				String[] ids=msg.split("ID");//접속자리스트를 받을때 사용하는 id분할 문
				
				if(ids.length==2&&ids[0].equals("id")){//접속자리스트를 받으면 실행되는 if 문
					String id=ids[1];//접속자리스트는 [0]=id [1]해당 아이디로 저장되어있음
					idList.add(id);//idlist에 id추가
					boolean result = false;
					int cnt=0;
					int[] j=new int[2];
					for (int i = 0; i < Server.idList.size(); i++) {//중복검사 실행
						result=(Server.idList.get(i).equals(id));//idlist중에 중복이 있으면 true저장
						if(result){//중복이 있으면 실행
							j[cnt]=i;//j배열에 j[0]에는 먼저 만든 아이디의 인덱스 j[1]에는 2번째로 만든 아이디의 인덱스
							cnt++;//기본 1저장 중복되면 2저장
						}//if end
					}//for end
					if(cnt>=2){result=true;}else{result=false;}//중복이면 실행되는 if
					//중복검사end
					
					PrintWriter pw=new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
					if(result==true){//중복이면 실행
						pw.println(result);//결과를 전달
						pw.flush();
						Server.idList.remove(j[1]);//j[1]에 있는 중복된 아이디의 인덱스값을 전달하여, 아이디리스트에서 삭제
					}else if(result==false){//중복X 실행
						pw.println(result);//결과 전달
						pw.flush();
						ClientManager manage=new ClientManager(sock,id);//클라이언트를 관리하는 매니저 선언및 초기화
						list.add(new PrintWriter(sock.getOutputStream()));//위에서 선언한 접속자 목록에 추가
						manage.start();//접속자를 관리하는 매니저 스레드 시작
						System.out.println("접속완료");
						
					}//if end
				}//if end
			}//while end
	}//Server main end
}//class Server end