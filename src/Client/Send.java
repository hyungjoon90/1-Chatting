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
	public Send(Socket sock,String id,Ui02 ui02){//client���� �����ϴ� ������
		this.sock=sock;//����
		this.id=id;//���̵�
		this.ui02=ui02;//ä��â ui02�� �ּ�
	}
	
	public void run() {
		
		try {
		br=new BufferedReader(new InputStreamReader(System.in));
			pw=new PrintWriter(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("idID"+id);//���̵� ������ ����
		pw.flush();
		
		while(true){
			try {//Ŭ�������� �����ϱ����� ���/������� ������ ������ �� �ȵ�
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//try catch end
			if(ui02.msg!="~!@#"){//ui02�� msg���� ���۵� ������ ����ȴ�.�⺻���� ~!@#�� ����Ǿ��ִ�. �� ���ڰ� �ٲ� ����
				pw.println(ui02.msg);//�ٲ� ������ ����
				pw.flush();
				ui02.msg="~!@#";//�ٽ� �⺻�������� �ٲ�
			}else if(ui02.msg=="EXIT"){
				pw.println(ui02.msg);//���Ḧ �������� ���� ����
				pw.flush();
			}//if end
		}//while end
	}//�޼��� run end
}//Class Send end