package superMarket;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class layout_try extends JFrame implements ActionListener{

    public static void main(String[] args) {
    	//�����������������ڣ�
        JFrame jf = new JFrame("���ܴ���");          // ��������
        jf.setLayout(null);                         //ȡ�����ֹ�����
        jf.setSize(850, 850);                     // ���ô��ڴ�С ��1200����1000
        jf.setLocationRelativeTo(null);             // �Ѵ���λ�����õ���Ļ����
        
        //����ָ���������ơ���ʽ�Ͱ�ֵ��С������һ���� Font��
        Font f1 = new Font("�����п�",Font.BOLD,40);
        Font f2 = new Font("����",Font.BOLD,20);
        
        //����һ�������������ť��������ӵ����������
        JButton btn1 = new JButton("��ѯ");
        btn1.setBounds(50, 100, 150, 150);//x����50��y����100����300����150
        btn1.setFont(f1);
        jf.add(btn1);
        // �ı���
        JTextArea area1 = new JTextArea();
        area1.setBounds(210, 100, 300, 150);
        area1.setFont(f2);
        jf.add(area1);
        
        
        JButton btn2 = new JButton("����");
        btn2.setBounds(50, 260, 150, 150);
        btn2.setFont(f1);
        jf.add(btn2);
        JTextArea area2 = new JTextArea();
        area2.setBounds(210, 260, 300, 150);
        area2.setFont(f2);
        jf.add(area2);
        
        
        JButton btn3 = new JButton("ɾ��");
        btn3.setBounds(50, 420, 150, 150);
        btn3.setFont(f1);
        jf.add(btn3);
        JTextArea area3 = new JTextArea();
        area3.setBounds(210, 420, 300, 150);
        area3.setFont(f2);
        jf.add(area3);
        
        
        JButton btn4 = new JButton("����");
        btn4.setBounds(50, 580, 150, 150);
        btn4.setFont(f1);
        jf.add(btn4);
        JTextArea area4 = new JTextArea();
        area4.setBounds(210, 580, 300, 150);
        area4.setFont(f2);
        jf.add(area4);
        
        //��������ڵĹرհ�ťʱ�˳�����û����һ�䣬���򲻻��˳���
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        //��ʾ���ڣ�ǰ�洴������Ϣ�����ڴ��У����ڴ��еĴ�����ʾ����Ļ��
        jf.setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
