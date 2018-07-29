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

public class Server {//������ �޾� �����ڵ��� �Ѱ��ִ� ����Ŭ����

	public static ArrayList<PrintWriter> list;//�����ڸ�� ����Ʈ����
	public static ArrayList<String> idList;
	public static void main(String[] args) throws IOException{
		
		list=new ArrayList<PrintWriter>();//����Ʈ �ʱ�ȭ
		idList=new ArrayList();//client���� ���� id����
		
			ServerSocket serv=new ServerSocket(3000);//���� ��Ʈ��ȣ ����
			while(true){
				System.out.println("���Ӵ����");
				Socket sock=serv.accept();//���ӹ޴¹�
				System.out.println(sock.getInetAddress());//�ֿܼ� ip���
				
				//�ߺ��˻�
				BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
				String msg=br.readLine();//client���� id����
				String[] ids=msg.split("ID");//�����ڸ���Ʈ�� ������ ����ϴ� id���� ��
				
				if(ids.length==2&&ids[0].equals("id")){//�����ڸ���Ʈ�� ������ ����Ǵ� if ��
					String id=ids[1];//�����ڸ���Ʈ�� [0]=id [1]�ش� ���̵�� ����Ǿ�����
					idList.add(id);//idlist�� id�߰�
					boolean result = false;
					int cnt=0;
					int[] j=new int[2];
					for (int i = 0; i < Server.idList.size(); i++) {//�ߺ��˻� ����
						result=(Server.idList.get(i).equals(id));//idlist�߿� �ߺ��� ������ true����
						if(result){//�ߺ��� ������ ����
							j[cnt]=i;//j�迭�� j[0]���� ���� ���� ���̵��� �ε��� j[1]���� 2��°�� ���� ���̵��� �ε���
							cnt++;//�⺻ 1���� �ߺ��Ǹ� 2����
						}//if end
					}//for end
					if(cnt>=2){result=true;}else{result=false;}//�ߺ��̸� ����Ǵ� if
					//�ߺ��˻�end
					
					PrintWriter pw=new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
					if(result==true){//�ߺ��̸� ����
						pw.println(result);//����� ����
						pw.flush();
						Server.idList.remove(j[1]);//j[1]�� �ִ� �ߺ��� ���̵��� �ε������� �����Ͽ�, ���̵𸮽�Ʈ���� ����
					}else if(result==false){//�ߺ�X ����
						pw.println(result);//��� ����
						pw.flush();
						ClientManager manage=new ClientManager(sock,id);//Ŭ���̾�Ʈ�� �����ϴ� �Ŵ��� ����� �ʱ�ȭ
						list.add(new PrintWriter(sock.getOutputStream()));//������ ������ ������ ��Ͽ� �߰�
						manage.start();//�����ڸ� �����ϴ� �Ŵ��� ������ ����
						System.out.println("���ӿϷ�");
						
					}//if end
				}//if end
			}//while end
	}//Server main end
}//class Server end