package superMarket;

import java.awt.*;
import javax.swing.*;

public class CommonComponent extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonComponent() {
 
        // ���
        JPanel p1 = new JPanel();
        add(p1);

        // ��ǩ
        JLabel name = new JLabel("�û���:");
        p1.add(name);
        name.setBounds(10, 10, 5, 5);

        // �ı���
        JTextField field = new JTextField(8);
        p1.add(field);

        // ��ǩ
        JLabel passwd = new JLabel("����");
        p1.add(passwd);
        // ������
        JPasswordField pass = new JPasswordField(8);
        p1.add(pass);

        /*
        // ��ѡ��ť
        JLabel gender = new JLabel("�Ա�");
        p1.add(gender);
        JRadioButton male = new JRadioButton("��");
        JRadioButton female = new JRadioButton("Ů");
        // ��ѡ��ť��,ͬһ����ѡ��ť��Ļ���.
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        // ע��,��ѡ��ť�鲻����ӽ�����
        p1.add(male);
        p1.add(female);

        // ��ѡ��
        JLabel like = new JLabel("����:");
        p1.add(like);
        JCheckBox eat = new JCheckBox("�Է�");
        JCheckBox movie = new JCheckBox("����Ӱ");
        JCheckBox sleep = new JCheckBox("˯��");
        p1.add(eat);
        p1.add(movie);
        p1.add(sleep);
        */
        
        // �ı���
        JLabel info = new JLabel("���˼��");
        p1.add(info);
        JTextArea area = new JTextArea(20, 20);
        p1.add(area);  
        
        /*
        // �б�
        String[] data = { "one", "two", "three" };
        JList list = new JList(data);
        p1.add(list);
        */

        // �˵���
        JMenuBar bar = new JMenuBar();
        // �˵�
        JMenu menu = new JMenu("�ļ�");
        // �˵�ѡ��
        JMenuItem myNew = new JMenuItem("�½�");
        JMenuItem myOpen = new JMenuItem("��");
        bar.add(menu);
        menu.add(myNew);
        menu.add(myOpen);
        add(bar, BorderLayout.NORTH);
        
        
        // ��ͨ��ť
        JButton button = new JButton("ע��");
        button.setBounds(15, 15, 5, 5);
        p1.add(button);

    }

    public static void main(String[] args) {
    	CommonComponent frame = new CommonComponent();
        frame.setTitle("�������");
        frame.setSize(400, 400);// ���óߴ�
        frame.setLocationRelativeTo(null);// JFrame����Ļ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// JFrame�ر�ʱ�Ĳ���
        frame.pack();// ����Ӧ
        frame.setVisible(true);// ��ʾJFrame
    }
}


