package Server;

import java.awt.PageAttributes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager extends Thread{//접속자들을 받아서 관리하는 매니저클래스
	public Socket sock;// 소켓 선언
	public String id;//id 선언
	BufferedReader br;//버퍼 선언
	public ClientManager(Socket sock,String id){//서버클래스에서 매니저를 선언하여 넘겨주는 소켓을 생성자로 받는문
		this.sock=sock;
		this.id=id;
	}
	@Override
	public void run() {//스레드가 시작되면 실행되는 메서드
		try {
			br=new BufferedReader(new InputStreamReader(sock.getInputStream()));//소켓을 통해 전해지는 input들을 받는 버퍼
			String msg;//받는 메세지들을 저장할 msg
			
			while(true){
				msg=br.readLine();//소켓을 통해 들어온 데이터를 버퍼에 저장했다가 다시 msg로 저장
				System.out.println(msg);
				String[] ids=msg.split("ID");//접속자리스트를 받을때 사용하는 id분할 문
				
				if(ids.length==2&&ids[0].equals("id")){//접속자리스트를 받으면 실행되는 if 문
					id=ids[1];//접속자리스트는 [0]=id [1]해당 아이디로 저장되어있음
					System.out.println(id+"님이 입장하셨습니다.");//위에서 [1]의 아이디를 변수id에 저장하여 사용
					for (int i = 0; i < Server.list.size(); i++) {//서버의 접속자리스트에 접속을 알림
						Server.list.get(i).println(id+"님이 입장하셨습니다");
						Server.list.get(i).flush();	
					}//for end
					for (int i = 0; i < Server.list.size(); i++) {//서버에 저장된 접속아이디리스트를 접속자들에게 전달
						String temp="";
						for (int j = 0; j < Server.idList.size(); j++) {//temp에 id리스트 저장
							temp+="list"+Server.idList.get(j);
						}//for end
						Server.list.get(i).println(temp);
						Server.list.get(i).flush();
					}//for end
					continue;//접속을 위한 문구라서 밑의 메세지 출력문을 패스하기위해 사용
				}//if end
				
				if(msg.equals("EXIT")){//종료를 눌렀을때 msg에 EXIT가 들어가 실행
					for (int i = 0; i < Server.list.size(); i++) {//서버에 있는 접속자리스트에 해당 접속자가 종료했다고 알리는 for문
						Server.list.get(i).println(id+"님이 나가셨습니다");
						Server.list.get(i).flush();
					}//for end
					for (int i = 0; i < Server.idList.size(); i++) {//접속자가 나갔을때 서버 아이디리스트에서 제거하는 for문
						boolean result=(Server.idList.get(i).equals(id));
						if(result){
							Server.idList.remove(i);	
						}
					}//for end
					for (int i = 0; i < Server.list.size(); i++) {//제거하고 난 아이디 리스트를 재전송
						String temp="";
						for (int j = 0; j < Server.idList.size(); j++) {
							temp+="list"+Server.idList.get(j);
						}
						Server.list.get(i).println(temp);
						Server.list.get(i).flush();
					}//for end
					break;
				}else{
					for (int i = 0; i < Server.list.size(); i++) {//위의 접속문 출력을 하지 않으면 실행되는 기본메세지 출력문
						Server.list.get(i).println(id+">"+msg);//서버의 접속자리스트에 id와 메세지를 보냄
						Server.list.get(i).flush();
					}//for end
				}//if end
			}//while end
			Server.list.remove(new PrintWriter(sock.getOutputStream()));//접속을 해제하면 서버 소켓리스트에서 삭제문
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
		}//try catch end finally end
	}//메서드 run end
}//class ClientManager end
