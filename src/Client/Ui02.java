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
	String msg="~!@#";//������ �޼��� ���庯��
	TextArea ta1;//ä��
	TextArea ta2;//������
	TextField tf;//�Է�â
	public Ui02() {
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
        icon =new ImageIcon("chatimg.png");
        JLabel logo =new JLabel(icon);
        logo.setBounds(10, 10, 70, 50);
        logo.setVisible(true);
        jp.add(logo);
        
        //ä�ù� �۾�
        JLabel letter =new JLabel("�츮���� �ӴڼӴ�");
        Font font1 = new Font("����", Font.BOLD, 25);
        letter.setFont(font1); //ä�� �Է��� ���� �۾���
//        letter.setForeground(Color.WHITE);//ä�� �Է� ���ڻ� 
        letter.setBounds(100, 10, 250, 50);
        letter.setVisible(true);
        jp.add(letter);
        
        //ä��â
        ta1 = new TextArea("",20,40,TextArea.SCROLLBARS_VERTICAL_ONLY);// 20�� 40column text area �����
        ta1.setEditable(false);
        ta1.setBounds(10, 70, 200, 300);
        ta1.setVisible(true);
        jp.add(ta1);
        
        //������â �̸�
        JLabel letterli =new JLabel("������ ���");
        Font font2 = new Font("����", Font.BOLD, 15);
        letterli.setFont(font2); //ä�� �Է��� ���� �۾���
//        letterli.setForeground(Color.WHITE);//ä�� �Է� ���ڻ� 
        letterli.setBounds(235, 58, 100, 40);
        letterli.setVisible(true);
        jp.add(letterli);
        
        //������â
        ta2 = new TextArea("",20,10,TextArea.SCROLLBARS_VERTICAL_ONLY);// 20�� 40column text area �����
        ta2.setEditable(false);
        ta2.setBounds(225, 100, 110, 270);
        ta2.setVisible(true);
        jp.add(ta2);
        
        //ä�� �Է�â
        tf = new TextField(28);
        tf.setBounds(10, 380, 250, 30);
        tf.setVisible(true);
        tf.addKeyListener(this);
        jp.add(tf);
        
        //���� ��ư
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
        
        //���� ��ư
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
		Dimension frame=new Dimension(352,452);//����, ����
		int x=screan.width/2-frame.width/2;
		int y=screan.height/2-frame.height/2;
		
		//����ǥ��â ���� ������
		Image img = kit.getImage("communication.png");
		setIconImage(img);
		
		setResizable(false);
		setBounds(x, y, frame.width, frame.height);//Frame�� ��ġ  
		setVisible(false);
		
	}//structure end
////////////////event
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).equals(send)){
			msg=tf.getText();//msg�� �������� ����
			tf.setText("");//�Է�â �ʱ�ȭ
		}else if(((JButton)e.getSource()).equals(exit)){
			msg="EXIT";
			dispose();
		}
	}
	public void keyReleased(KeyEvent e) {//���ͽ� ����
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
