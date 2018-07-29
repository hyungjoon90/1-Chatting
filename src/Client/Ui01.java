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

//아이피 입력 메인

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
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        jp.setLayout(null);
		
      //상단 좌측 상단 이미지
        icon =new ImageIcon("logo.png");
        JLabel logo =new JLabel(icon);
        logo.setBounds(230, 15, 200, 100);
        logo.setVisible(true);
        jp.add(logo);
        
        //채팅방 글씨
        JLabel letter =new JLabel("우리끼리 속닥속닥");
        Font font1 = new Font("굴림", Font.BOLD, 35);
        letter.setFont(font1); //채팅 입력은 굵은 글씨로
//        letter.setForeground(Color.WHITE);//채팅 입력 글자색 
        letter.setBounds(190, 125, 350, 50);
        letter.setVisible(true);
        jp.add(letter);
        
        //ip라벨
        JLabel ipLa =new JLabel("서버 IP주소");
        Font font2 = new Font("굴림", Font.BOLD, 15);
        ipLa.setFont(font2); //채팅 입력은 굵은 글씨로
        ipLa.setBounds(70, 188, 90, 30);
        ipLa.setVisible(true);
        jp.add(ipLa);
        
        //ip입력란
        tfip=new JTextField(16);
        tfip.setBounds(160, 193, 180, 20);
        tfip.addKeyListener(this);
        tfip.addMouseListener(this);
        tfip.setVisible(true);
        jp.add(tfip);
        
        //id라벨
        JLabel idLa =new JLabel("ID입력");
        idLa.setFont(font2); //채팅 입력은 굵은 글씨로
        idLa.setBounds(380, 188, 90, 30);
        idLa.setVisible(true);
        jp.add(idLa);
        
        //id입력란
        tfid=new JTextField(16);
        tfid.setBounds(435, 193, 140, 20);
        tfid.addKeyListener(this);
        tfid.addMouseListener(this);
        tfid.setVisible(true);
        jp.add(tfid);
		
        //입장버튼
        icon =new ImageIcon("send1.png");
        ImageIcon send2 = new ImageIcon("send2.png");
        enter =new JButton("입장하기",icon);
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
		Dimension frame=new Dimension(700,300);//가로, 세로
		int x=screan.width/2-frame.width/2;
		int y=screan.height/2-frame.height/2;
		
		Image img = kit.getImage("communication.png");
		setIconImage(img);
		
		setResizable(false);
		setBounds(x, y, frame.width, frame.height);//Frame의 위치  
		setVisible(true);
	}//ui01 생성자 end
	
////////////////event	
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).equals(enter)){
			String ipAddr =tfip.getText();
			String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
			if (!Pattern.matches(validIp, ipAddr)){//ip검사 정규식
				tfip.setText("IP를 입력해주세요");
				if((tfid.getText()).equals("")){tfid.setText("아이디를 입력해주세요");}
			}else{
				if((tfid.getText()).equals("")){
					tfid.setText("아이디를 입력해주세요");
				}else if((tfid.getText()).contains("중복입니다")){
					tfid.setText("중복입니다");
				}else if((tfid.getText()).contains("아이디를 입력해주세요")&&!(tfip.getText()).equals("")){
					tfid.setText("아이디를 입력해주세요");
				}else if(((tfip.getText()).contains("IP를 입력해주세요")&&!(tfid.getText()).equals(""))){
					tfip.setText("IP를 입력해주세요");
				}else if(!(tfid.getText()).equals("")&&!(tfip.getText()).equals("")&&
						!(tfid.getText()).contains("중복입니다")&&!(tfip.getText()).equals("사용불가한 IP입니다")&&
						!(tfid.getText()).contains("아이디를 입력해주세요")){
					Client.ipaddress=tfip.getText();//ui의 ip입력창에서 ip를 받아 Client의 ip변수에 저장
					Client.id=tfid.getText();//위와 같이 id를 받아 저장
					String temp=Client.connect();//클라이언트의 접속을 하는 메서드를 실행
					if(temp.equals("true")){//중복시 출력
						tfid.setText("중복입니다");
					}else if(temp.equals(false)){
						dispose();//내용을 받아 창을 닫음
					}else if(temp.equals("isDead")){
						tfip.setText("사용불가한 IP입니다");
					}//else if end
				}//if end
			}//if end ip숫자 검사
		}//if end
	}//actionPerformed end

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if ((e.getSource()==tfid&&e.getKeyCode() == KeyEvent.VK_ENTER)||(e.getSource()==tfip&&e.getKeyCode() == KeyEvent.VK_ENTER)){
			String ipAddr =tfip.getText();
			String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
			if (!Pattern.matches(validIp, ipAddr)){//ip검사 정규식
				tfip.setText("IP를 입력해주세요");
				if((tfid.getText()).equals("")){tfid.setText("아이디를 입력해주세요");}
			}else{
				if((tfid.getText()).equals("")){
					tfid.setText("아이디를 입력해주세요");
				}else if((tfid.getText()).contains("중복입니다")){
					tfid.setText("중복입니다");
				}else if((tfid.getText()).contains("아이디를 입력해주세요")&&!(tfip.getText()).equals("")){
					tfid.setText("아이디를 입력해주세요");
				}else if(((tfip.getText()).contains("IP를 입력해주세요")&&!(tfid.getText()).equals(""))){
					tfip.setText("IP를 입력해주세요");
				}else if(!(tfid.getText()).equals("")&&!(tfip.getText()).equals("")&&
						!(tfid.getText()).contains("중복입니다")&&!(tfip.getText()).equals("사용불가한 IP입니다")&&
						!(tfid.getText()).contains("아이디를 입력해주세요")){
					Client.ipaddress=tfip.getText();//ui의 ip입력창에서 ip를 받아 Client의 ip변수에 저장
					Client.id=tfid.getText();//위와 같이 id를 받아 저장
					String temp=Client.connect();//클라이언트의 접속을 하는 메서드를 실행
					if(temp.equals("true")){//중복시 출력
						tfid.setText("중복입니다");
					}else if(temp.equals(false)){
						dispose();//내용을 받아 창을 닫음
					}else if(temp.equals("isDead")){
						tfip.setText("사용불가한 IP입니다");
					}//else if end
				}//if end
			}//if end ip숫자 검사
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

