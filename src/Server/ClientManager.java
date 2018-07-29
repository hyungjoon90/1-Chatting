package Server;

import java.awt.PageAttributes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager extends Thread{//�����ڵ��� �޾Ƽ� �����ϴ� �Ŵ���Ŭ����
	public Socket sock;// ���� ����
	public String id;//id ����
	BufferedReader br;//���� ����
	public ClientManager(Socket sock,String id){//����Ŭ�������� �Ŵ����� �����Ͽ� �Ѱ��ִ� ������ �����ڷ� �޴¹�
		this.sock=sock;
		this.id=id;
	}
	@Override
	public void run() {//�����尡 ���۵Ǹ� ����Ǵ� �޼���
		try {
			br=new BufferedReader(new InputStreamReader(sock.getInputStream()));//������ ���� �������� input���� �޴� ����
			String msg;//�޴� �޼������� ������ msg
			
			while(true){
				msg=br.readLine();//������ ���� ���� �����͸� ���ۿ� �����ߴٰ� �ٽ� msg�� ����
				System.out.println(msg);
				String[] ids=msg.split("ID");//�����ڸ���Ʈ�� ������ ����ϴ� id���� ��
				
				if(ids.length==2&&ids[0].equals("id")){//�����ڸ���Ʈ�� ������ ����Ǵ� if ��
					id=ids[1];//�����ڸ���Ʈ�� [0]=id [1]�ش� ���̵�� ����Ǿ�����
					System.out.println(id+"���� �����ϼ̽��ϴ�.");//������ [1]�� ���̵� ����id�� �����Ͽ� ���
					for (int i = 0; i < Server.list.size(); i++) {//������ �����ڸ���Ʈ�� ������ �˸�
						Server.list.get(i).println(id+"���� �����ϼ̽��ϴ�");
						Server.list.get(i).flush();	
					}//for end
					for (int i = 0; i < Server.list.size(); i++) {//������ ����� ���Ӿ��̵𸮽�Ʈ�� �����ڵ鿡�� ����
						String temp="";
						for (int j = 0; j < Server.idList.size(); j++) {//temp�� id����Ʈ ����
							temp+="list"+Server.idList.get(j);
						}//for end
						Server.list.get(i).println(temp);
						Server.list.get(i).flush();
					}//for end
					continue;//������ ���� ������ ���� �޼��� ��¹��� �н��ϱ����� ���
				}//if end
				
				if(msg.equals("EXIT")){//���Ḧ �������� msg�� EXIT�� �� ����
					for (int i = 0; i < Server.list.size(); i++) {//������ �ִ� �����ڸ���Ʈ�� �ش� �����ڰ� �����ߴٰ� �˸��� for��
						Server.list.get(i).println(id+"���� �����̽��ϴ�");
						Server.list.get(i).flush();
					}//for end
					for (int i = 0; i < Server.idList.size(); i++) {//�����ڰ� �������� ���� ���̵𸮽�Ʈ���� �����ϴ� for��
						boolean result=(Server.idList.get(i).equals(id));
						if(result){
							Server.idList.remove(i);	
						}
					}//for end
					for (int i = 0; i < Server.list.size(); i++) {//�����ϰ� �� ���̵� ����Ʈ�� ������
						String temp="";
						for (int j = 0; j < Server.idList.size(); j++) {
							temp+="list"+Server.idList.get(j);
						}
						Server.list.get(i).println(temp);
						Server.list.get(i).flush();
					}//for end
					break;
				}else{
					for (int i = 0; i < Server.list.size(); i++) {//���� ���ӹ� ����� ���� ������ ����Ǵ� �⺻�޼��� ��¹�
						Server.list.get(i).println(id+">"+msg);//������ �����ڸ���Ʈ�� id�� �޼����� ����
						Server.list.get(i).flush();
					}//for end
				}//if end
			}//while end
			Server.list.remove(new PrintWriter(sock.getOutputStream()));//������ �����ϸ� ���� ���ϸ���Ʈ���� ������
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
	}//�޼��� run end
}//class ClientManager end
