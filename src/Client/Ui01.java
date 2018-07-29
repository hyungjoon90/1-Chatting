package Client;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//������ �Է� ����

public class Ui01 extends JFrame implements ActionListener,KeyListener,MouseListener{
	ImageIcon background;
	ImageIcon icon;
	JButton enter;
	JTextField tfip;
	JTextField tfid;
	public Ui01() {
		background =new ImageIcon("paper1.jpg");
		JPanel jp =new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(background.getImage(), 0, 0, null);
                setOpaque(false); //�׸��� ǥ���ϰ� ����,�����ϰ� ����
                super.paintComponent(g);
            }
        };
        jp.setLayout(null);
		
      //��� ���� ��� �̹���
        icon =new ImageIcon("logo.png");
        JLabel logo =new JLabel(icon);
        logo.setBounds(230, 15, 200, 100);
        logo.setVisible(true);
        jp.add(logo);
        
        //ä�ù� �۾�
        JLabel letter =new JLabel("�츮���� �ӴڼӴ�");
        Font font1 = new Font("����", Font.BOLD, 35);
        letter.setFont(font1); //ä�� �Է��� ���� �۾���
//        letter.setForeground(Color.WHITE);//ä�� �Է� ���ڻ� 
        letter.setBounds(190, 125, 350, 50);
        letter.setVisible(true);
        jp.add(letter);
        
        //ip��
        JLabel ipLa =new JLabel("���� IP�ּ�");
        Font font2 = new Font("����", Font.BOLD, 15);
        ipLa.setFont(font2); //ä�� �Է��� ���� �۾���
        ipLa.setBounds(70, 188, 90, 30);
        ipLa.setVisible(true);
        jp.add(ipLa);
        
        //ip�Է¶�
        tfip=new JTextField(16);
        tfip.setBounds(160, 193, 180, 20);
        tfip.addKeyListener(this);
        tfip.addMouseListener(this);
        tfip.setVisible(true);
        jp.add(tfip);
        
        //id��
        JLabel idLa =new JLabel("ID�Է�");
        idLa.setFont(font2); //ä�� �Է��� ���� �۾���
        idLa.setBounds(380, 188, 90, 30);
        idLa.setVisible(true);
        jp.add(idLa);
        
        //id�Է¶�
        tfid=new JTextField(16);
        tfid.setBounds(435, 193, 140, 20);
        tfid.addKeyListener(this);
        tfid.addMouseListener(this);
        tfid.setVisible(true);
        jp.add(tfid);
		
        //�����ư
        icon =new ImageIcon("send1.png");
        ImageIcon send2 = new ImageIcon("send2.png");
        enter =new JButton("�����ϱ�",icon);
        enter.setPressedIcon(send2);
        enter.addActionListener(this);
        enter.setBorderPainted(false);
        enter.setContentAreaFilled(false);
        enter.setFocusPainted(false);
        enter.setBounds(280, 217, 120, 50);
        enter.setVisible(true);
        jp.add(enter);
        
		add(jp);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screan = kit.getScreenSize();
		Dimension frame=new Dimension(700,300);//����, ����
		int x=screan.width/2-frame.width/2;
		int y=screan.height/2-frame.height/2;
		
		Image img = kit.getImage("communication.png");
		setIconImage(img);
		
		setResizable(false);
		setBounds(x, y, frame.width, frame.height);//Frame�� ��ġ  
		setVisible(true);
	}//ui01 ������ end
	
////////////////event	
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).equals(enter)){
			String ipAddr =tfip.getText();
			String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
			if (!Pattern.matches(validIp, ipAddr)){//ip�˻� ���Խ�
				tfip.setText("IP�� �Է����ּ���");
				if((tfid.getText()).equals("")){tfid.setText("���̵� �Է����ּ���");}
			}else{
				if((tfid.getText()).equals("")){
					tfid.setText("���̵� �Է����ּ���");
				}else if((tfid.getText()).contains("�ߺ��Դϴ�")){
					tfid.setText("�ߺ��Դϴ�");
				}else if((tfid.getText()).contains("���̵� �Է����ּ���")&&!(tfip.getText()).equals("")){
					tfid.setText("���̵� �Է����ּ���");
				}else if(((tfip.getText()).contains("IP�� �Է����ּ���")&&!(tfid.getText()).equals(""))){
					tfip.setText("IP�� �Է����ּ���");
				}else if(!(tfid.getText()).equals("")&&!(tfip.getText()).equals("")&&
						!(tfid.getText()).contains("�ߺ��Դϴ�")&&!(tfip.getText()).equals("���Ұ��� IP�Դϴ�")&&
						!(tfid.getText()).contains("���̵� �Է����ּ���")){
					Client.ipaddress=tfip.getText();//ui�� ip�Է�â���� ip�� �޾� Client�� ip������ ����
					Client.id=tfid.getText();//���� ���� id�� �޾� ����
					String temp=Client.connect();//Ŭ���̾�Ʈ�� ������ �ϴ� �޼��带 ����
					if(temp.equals("true")){//�ߺ��� ���
						tfid.setText("�ߺ��Դϴ�");
					}else if(temp.equals(false)){
						dispose();//������ �޾� â�� ����
					}else if(temp.equals("isDead")){
						tfip.setText("���Ұ��� IP�Դϴ�");
					}//else if end
				}//if end
			}//if end ip���� �˻�
		}//if end
	}//actionPerformed end

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if ((e.getSource()==tfid&&e.getKeyCode() == KeyEvent.VK_ENTER)||(e.getSource()==tfip&&e.getKeyCode() == KeyEvent.VK_ENTER)){
			String ipAddr =tfip.getText();
			String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
			if (!Pattern.matches(validIp, ipAddr)){//ip�˻� ���Խ�
				tfip.setText("IP�� �Է����ּ���");
				if((tfid.getText()).equals("")){tfid.setText("���̵� �Է����ּ���");}
			}else{
				if((tfid.getText()).equals("")){
					tfid.setText("���̵� �Է����ּ���");
				}else if((tfid.getText()).contains("�ߺ��Դϴ�")){
					tfid.setText("�ߺ��Դϴ�");
				}else if((tfid.getText()).contains("���̵� �Է����ּ���")&&!(tfip.getText()).equals("")){
					tfid.setText("���̵� �Է����ּ���");
				}else if(((tfip.getText()).contains("IP�� �Է����ּ���")&&!(tfid.getText()).equals(""))){
					tfip.setText("IP�� �Է����ּ���");
				}else if(!(tfid.getText()).equals("")&&!(tfip.getText()).equals("")&&
						!(tfid.getText()).contains("�ߺ��Դϴ�")&&!(tfip.getText()).equals("���Ұ��� IP�Դϴ�")&&
						!(tfid.getText()).contains("���̵� �Է����ּ���")){
					Client.ipaddress=tfip.getText();//ui�� ip�Է�â���� ip�� �޾� Client�� ip������ ����
					Client.id=tfid.getText();//���� ���� id�� �޾� ����
					String temp=Client.connect();//Ŭ���̾�Ʈ�� ������ �ϴ� �޼��带 ����
					if(temp.equals("true")){//�ߺ��� ���
						tfid.setText("�ߺ��Դϴ�");
					}else if(temp.equals(false)){
						dispose();//������ �޾� â�� ����
					}else if(temp.equals("isDead")){
						tfip.setText("���Ұ��� IP�Դϴ�");
					}//else if end
				}//if end
			}//if end ip���� �˻�
		}//if end
	}//keyReleased end

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
		if((e.getSource()).equals(tfid)){
			tfid.setText("");
		}else if((e.getSource()).equals(tfip)){
			tfip.setText("");
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
////////////////event end
}//Class Ui01 end

