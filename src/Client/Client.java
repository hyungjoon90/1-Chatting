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
		String msg = null;//�ߺ��˻����� �־� ���� msg
		boolean result=false;//�ߺ��˻� ��� ���� result
		try {
			InetAddress pingcheck = InetAddress.getByName(ipaddress);
			// �ش� �������� ������ ���� ��� true ��ȯ, ������ ���� ��� false ��ȯ
			// isReachable �Ű������� Ÿ�Ӿƿ� 
			boolean isAlive = pingcheck.isReachable(1000);
			if(isAlive==false){return "isDead";}
			sock=new Socket(ipaddress,port);
			pw=new PrintWriter(sock.getOutputStream());
			pw.println("idID"+id);//���̵� ������ ����
			pw.flush();
			BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			int cnt=0;
			while(true){//�������� �ߺ��˻縦 �Ͽ� ��ȯ���� while
				msg=br.readLine();//�ߺ��̸� true, �ƴϸ� false
				if(!msg.isEmpty()){//�ߺ��̰ų� �ƴϰų� �Ѵ� while Ż��
					break;
				}
				break;
				
			}//while end
			msg.toString();//booleanŸ���� ����� string�� ��ȯ
			if(msg.equals("false")){//�ƴҽ� ����
				ui01.dispose();//�α���â �ݱ�
				ui02.setTitle(id);
				ui02.setVisible(true);//ä��â ���
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
	}//�޼��� connect end
}//Class Client end
