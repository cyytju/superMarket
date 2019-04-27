package superMarket;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class layout_try extends JFrame implements ActionListener{

    public static void main(String[] args) {
    	//创建顶层容器（窗口）
        JFrame jf = new JFrame("功能窗口");          // 创建窗口
        jf.setLayout(null);                         //取消布局管理器
        jf.setSize(850, 850);                     // 设置窗口大小 宽1200，高1000
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        
        //根据指定字体名称、样式和磅值大小，创建一个新 Font。
        Font f1 = new Font("华文行楷",Font.BOLD,40);
        Font f2 = new Font("宋体",Font.BOLD,20);
        
        //创建一个基本组件（按钮），并添加到面板容器中
        JButton btn1 = new JButton("查询");
        btn1.setBounds(50, 100, 150, 150);//x坐标50，y坐标100，宽300，高150
        btn1.setFont(f1);
        jf.add(btn1);
        // 文本域
        JTextArea area1 = new JTextArea();
        area1.setBounds(210, 100, 300, 150);
        area1.setFont(f2);
        jf.add(area1);
        
        
        JButton btn2 = new JButton("插入");
        btn2.setBounds(50, 260, 150, 150);
        btn2.setFont(f1);
        jf.add(btn2);
        JTextArea area2 = new JTextArea();
        area2.setBounds(210, 260, 300, 150);
        area2.setFont(f2);
        jf.add(area2);
        
        
        JButton btn3 = new JButton("删除");
        btn3.setBounds(50, 420, 150, 150);
        btn3.setFont(f1);
        jf.add(btn3);
        JTextArea area3 = new JTextArea();
        area3.setBounds(210, 420, 300, 150);
        area3.setFont(f2);
        jf.add(area3);
        
        
        JButton btn4 = new JButton("更新");
        btn4.setBounds(50, 580, 150, 150);
        btn4.setFont(f1);
        jf.add(btn4);
        JTextArea area4 = new JTextArea();
        area4.setBounds(210, 580, 300, 150);
        area4.setFont(f2);
        jf.add(area4);
        
        //当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        //显示窗口，前面创建的信息都在内存中，把内存中的窗口显示在屏幕上
        jf.setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
