package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Ui02 extends JFrame implements ActionListener,KeyListener{
	ImageIcon background;
	ImageIcon icon;
	JButton send;
	JButton exit;
	String msg="~!@#";//전달할 메세지 저장변수
	TextArea ta1;//채팅
	TextArea ta2;//접속자
	TextField tf;//입력창
	public Ui02() {
		background =new ImageIcon("paper1.jpg");
		JPanel jp =new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(background.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        jp.setLayout(null);
        
        //상단 좌측 상단 이미지
        icon =new ImageIcon("chatimg.png");
        JLabel logo =new JLabel(icon);
        logo.setBounds(10, 10, 70, 50);
        logo.setVisible(true);
        jp.add(logo);
        
        //채팅방 글씨
        JLabel letter =new JLabel("우리끼리 속닥속닥");
        Font font1 = new Font("굴림", Font.BOLD, 25);
        letter.setFont(font1); //채팅 입력은 굵은 글씨로
//        letter.setForeground(Color.WHITE);//채팅 입력 글자색 
        letter.setBounds(100, 10, 250, 50);
        letter.setVisible(true);
        jp.add(letter);
        
        //채팅창
        ta1 = new TextArea("",20,40,TextArea.SCROLLBARS_VERTICAL_ONLY);// 20행 40column text area 만들기
        ta1.setEditable(false);
        ta1.setBounds(10, 70, 200, 300);
        ta1.setVisible(true);
        jp.add(ta1);
        
        //접속자창 이름
        JLabel letterli =new JLabel("접속자 목록");
        Font font2 = new Font("굴림", Font.BOLD, 15);
        letterli.setFont(font2); //채팅 입력은 굵은 글씨로
//        letterli.setForeground(Color.WHITE);//채팅 입력 글자색 
        letterli.setBounds(235, 58, 100, 40);
        letterli.setVisible(true);
        jp.add(letterli);
        
        //접속자창
        ta2 = new TextArea("",20,10,TextArea.SCROLLBARS_VERTICAL_ONLY);// 20행 40column text area 만들기
        ta2.setEditable(false);
        ta2.setBounds(225, 100, 110, 270);
        ta2.setVisible(true);
        jp.add(ta2);
        
        //채팅 입력창
        tf = new TextField(28);
        tf.setBounds(10, 380, 250, 30);
        tf.setVisible(true);
        tf.addKeyListener(this);
        jp.add(tf);
        
        //전송 버튼
        icon =new ImageIcon("send1.png");
        ImageIcon send2 = new ImageIcon("send2.png");
        send =new JButton(icon);
        send.setPressedIcon(send2);
        send.addActionListener(this);
        send.setBorderPainted(false);
        send.setContentAreaFilled(false);
        send.setFocusPainted(false);
        send.setBounds(260, 370, 50, 50);
        send.setVisible(true);
        jp.add(send);
        
        //종료 버튼
        icon =new ImageIcon("exit3.png");
        ImageIcon exit2 = new ImageIcon("exit4.png");
        exit =new JButton(icon);
        exit.setPressedIcon(exit2);
        exit.addActionListener(this);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.setBounds(300, 370, 50, 50);
        exit.setVisible(true);
        jp.add(exit);
        
        add(jp);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screan = kit.getScreenSize();
		Dimension frame=new Dimension(352,452);//가로, 세로
		int x=screan.width/2-frame.width/2;
		int y=screan.height/2-frame.height/2;
		
		//제목표시창 왼쪽 아이콘
		Image img = kit.getImage("communication.png");
		setIconImage(img);
		
		setResizable(false);
		setBounds(x, y, frame.width, frame.height);//Frame의 위치  
		setVisible(false);
		
	}//structure end
////////////////event
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).equals(send)){
			msg=tf.getText();//msg에 보낼문장 저장
			tf.setText("");//입력창 초기화
		}else if(((JButton)e.getSource()).equals(exit)){
			msg="EXIT";
			dispose();
		}
	}
	public void keyReleased(KeyEvent e) {//엔터시 실행
	      if (e.getSource()==tf&&e.getKeyCode() == KeyEvent.VK_ENTER){
	         msg=tf.getText();
	         tf.setText("");
	      }
	   }
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
	}
////////////////event end
}//class Ui02 end
