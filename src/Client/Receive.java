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
	public Receive(Socket sock,Ui02 ui02){//Client���� �����Ҷ� ������
		this.sock=sock;//����
		this.ui02=ui02;//ä�� ui02�� �ּ�
	}
	
	public void run() {
		try {
			br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String msg;//���۹޴� ������
			while(true){
				msg=br.readLine();//�������� ���۵Ǵ� ����
				if(msg.equals(null)){break;}
				
				if(msg.contains("list")){//������ ����� �°��� Ȯ��
					ui02.ta2.setText("");//������ ����� ���
					msgs=msg.split("list");//������ ����� ����
					for (int i = 1; i < msgs.length; i++) {//������ ��Ͽ� ���
						ui02.ta2.append(msgs[i]+"\n");
					}//for end
				}else{
					ui02.ta1.append(msg+"\n");//ä��â�� ���
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
	}//�޼��� run end
}//Class Receive end
