package superMarket;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.text.SimpleDateFormat;
public class simpleFunction extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private static Connection con;
	//创建顶层容器(窗口)
	static JFrame jf = new JFrame("简单功能主窗口"); 
	// 创建基本组件(按钮)
    JButton btn_select = new JButton("查询");   // 输入商品的任意单一属性(Food,Clothes都行)
    JButton btn_insert = new JButton("新品");   // 必须是这种格式  裤子,贵人鸟,黑,L,青少年,198RMB,150
    JButton btn_delete = new JButton("下架");   // 下架的输入格式同新品 大麻花,什锦,8个月,2019-05-22,天津,58RMB,30
    JButton btn_update = new JButton("售卖");   // 第一个文本框输入商品名称,第二个输入数量
    JButton btn_stock = new JButton("补货");    // 补货的输入格式同售卖
    JButton btn_analysis = new JButton("统计"); // 输入起始截止时间2018-06-01
    JButton main_clear = new JButton("清空");
    JButton main_close = new JButton("关闭");       
    JButton main_export = new JButton("导出");	
    // 标签
    JLabel state = new JLabel("数据库连接状态");
    JLabel label_state = new JLabel("操作状态");
    JLabel label_Cname = new JLabel("名称");
    JLabel label_Cnum = new JLabel("数量");
    JLabel label_Fname = new JLabel("名称");
    JLabel label_Fnum = new JLabel("数量");
    JLabel label_food = new JLabel("食品");
    JLabel label_clothes = new JLabel("衣服");
    JLabel label_good = new JLabel("商品");
    JLabel label_reason = new JLabel("原因");
    JLabel label_start = new JLabel("起始");
    JLabel label_end = new JLabel("截止");
    JLabel label_analysis = new JLabel("统计结果展示");
    // 创建文本域
    JTextArea area_select = new JTextArea();
    JTextArea area_food = new JTextArea();
    JTextArea area_clothes = new JTextArea();
    JTextArea area_delete = new JTextArea();
    JTextArea area_reason = new JTextArea();
    JTextArea area_start = new JTextArea();
    JTextArea area_end = new JTextArea();
    JTextArea area_results = new JTextArea();
    JTextArea sale_name = new JTextArea();
    JTextArea sale_num = new JTextArea();
    JTextArea stock_name = new JTextArea();
    JTextArea stock_num = new JTextArea();
    JTextArea area_analysis = new JTextArea();
    static JTextArea area_state = new JTextArea();
    
    // 根据指定字体名称、样式和磅值大小，创建一个新 Font。
    Font f1 = new Font("华文行楷",Font.BOLD,40);
    Font f2 = new Font("宋体",Font.BOLD,20);
    Font f3 = new Font("华文行楷",Font.BOLD,20);
    Font f4 = new Font("华文行楷",Font.BOLD,30);
    Font f5 = new Font("宋体",Font.BOLD,30);
    Font f6 = new Font("宋体",Font.BOLD,40);
    Font f7 = new Font("华文行楷",Font.BOLD,35);
    Font f8 = new Font("华文行楷",Font.BOLD,50);
    
    // 查询结果展示窗口(新窗口)
    static JFrame frame = new JFrame("查询结果展示新窗口");
    JTextArea area_show = new JTextArea();
    JButton btn_close = new JButton(new AbstractAction("关闭") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
    		frame.setVisible(false); // 无需销毁
    		//frame.dispose();       // 关闭并销毁		
    	}
    });
    JButton area_clear = new JButton(new AbstractAction("清空") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
    		area_show.setText("");
    	}
    });
    
    JButton area_export = new JButton(new AbstractAction("导出") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
			FileWriter fw = null;
			try { //现在导出为.txt没有问题，导出.pdf生成的文件打不开
				fw = new FileWriter("D:\\MyDesktop\\查询结果.xls");
				for(String str : area_show.getText().split("\n")){
		            fw.write(str + "\r\n");
		        } //导出.xls .rtf或.doc会产生部分乱码,文件可以打开并且正常显示
		        fw.close();
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				System.out.println("导出失败：\n" + exc.getMessage());	
			}
    	}
    });
    
	public simpleFunction() {
		
        jf.setLayout(null);                        // 取消布局管理器
        jf.setSize(1200, 1390);                    // 设置窗口大小 宽1200，高1380
        jf.setLocationRelativeTo(null);            // 把窗口位置设置到屏幕中心
        
        main_export.setBounds(70, 10, 120, 60);    // x坐标70，y坐标10，宽120，高60
        main_export.setFont(f1);                   // 选定字体样式f1
        jf.add(main_export);                       // 添加到面板容器中
        
        state.setBounds(220, 20, 225, 30);
        state.setFont(f4);
        jf.add(state);
        area_state.setBounds(455, 20, 355, 30);
        area_state.setFont(f5);
        jf.add(area_state);
        
        main_clear.setBounds(835, 10, 120, 60);
        main_clear.setFont(f1);
        jf.add(main_clear);
        
        main_close.setBounds(960, 10, 120, 60);
        main_close.setFont(f1);
        jf.add(main_close);
        
        btn_select.setBounds(70, 80, 150, 150);
        btn_select.setFont(f8);
        jf.add(btn_select);
        area_select.setBounds(230, 80, 850, 150);
        area_select.setFont(f6);
        jf.add(area_select);
        
        
        btn_insert.setBounds(70, 240, 150, 150);
        btn_insert.setFont(f8);
        jf.add(btn_insert);
        label_clothes.setBounds(230, 240, 85, 70);
        label_clothes.setFont(f1);
        jf.add(label_clothes);
        area_clothes.setBounds(325, 240, 755, 70);
        area_clothes.setFont(f5);
        jf.add(area_clothes);
        label_food.setBounds(230, 320, 85, 70);
        label_food.setFont(f1);
        jf.add(label_food);
        area_food.setBounds(325, 320, 755, 70);
        area_food.setFont(f5);
        jf.add(area_food);
        
        btn_delete.setBounds(70, 400, 150, 150);
        btn_delete.setFont(f8);
        jf.add(btn_delete);
        label_good.setBounds(230, 400, 85, 70);
        label_good.setFont(f1);
        jf.add(label_good);
        area_delete.setBounds(325, 400, 755, 70);
        area_delete.setFont(f5);
        jf.add(area_delete);
        label_reason.setBounds(230, 480, 85, 70);
        label_reason.setFont(f1);
        jf.add(label_reason);
        area_reason.setBounds(325, 480, 755, 70);
        area_reason.setFont(f6);
        jf.add(area_reason);
        
        btn_update.setBounds(70, 560, 150, 150);
        btn_update.setFont(f8);
        jf.add(btn_update);
        label_Cname.setBounds(230, 560, 85, 70);
        label_Cname.setFont(f1);
        jf.add(label_Cname);
        sale_name.setBounds(325, 560, 755, 70);
        sale_name.setFont(f6);
        jf.add(sale_name);
        label_Cnum.setBounds(230, 640, 85, 70);
        label_Cnum.setFont(f1);
        jf.add(label_Cnum);
        sale_num.setBounds(325, 640, 755, 70);
        sale_num.setFont(f6);
        jf.add(sale_num);
        
        btn_stock.setBounds(70, 720, 150, 150);
        btn_stock.setFont(f8);
        jf.add(btn_stock);
        label_Fname.setBounds(230, 720, 85, 70);
        label_Fname.setFont(f1);
        jf.add(label_Fname);
        stock_name.setBounds(325, 720, 755, 70);
        stock_name.setFont(f6);
        jf.add(stock_name);
        label_Fnum.setBounds(230, 800, 85, 70);
        label_Fnum.setFont(f1);
        jf.add(label_Fnum);
        stock_num.setBounds(325, 800, 755, 70);
        stock_num.setFont(f6);
        jf.add(stock_num);
        
        btn_analysis.setBounds(70, 880, 150, 150);
        btn_analysis.setFont(f8);
        jf.add(btn_analysis);
        label_start.setBounds(230, 880, 85, 70);
        label_start.setFont(f1);
        jf.add(label_start);
        area_start.setBounds(325, 880, 755, 70);
        area_start.setFont(f6);
        jf.add(area_start);
        label_end.setBounds(230, 960, 85, 70);
        label_end.setFont(f1);
        jf.add(label_end);
        area_end.setBounds(325, 960, 755, 70);
        area_end.setFont(f6);
        jf.add(area_end);
        
        label_analysis.setBounds(440, 1040, 200, 30);
        label_analysis.setFont(f4);
        jf.add(label_analysis);
        area_analysis.setBounds(70, 1080, 1010, 150);
        area_analysis.setFont(f5);
        jf.add(area_analysis);
        
        label_state.setBounds(70, 1248, 150, 70);
        label_state.setFont(f7);
        jf.add(label_state);
        area_results.setBounds(230, 1240, 850, 80);
        area_results.setFont(f2);
        jf.add(area_results);
        
        btn_select.addActionListener(this);
        btn_insert.addActionListener(this);
        btn_delete.addActionListener(this);
        btn_update.addActionListener(this);
        btn_stock.addActionListener(this);
        btn_analysis.addActionListener(this);
        main_export.addActionListener(this);
        main_clear.addActionListener(this);
        main_close.addActionListener(this);
        
        // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        // 显示窗口，前面创建的信息都在内存中，把内存中的窗口显示在屏幕上
        jf.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_select) {
            if(area_select.getText().length() >= 1) {
            	
            	frame.setLocationRelativeTo(null);
    			frame.setLayout(null);
    			frame.setSize(900, 800);
    		    JLabel results = new JLabel("查询结果集显示");
    		    results.setBounds(300, 20, 300, 100);
    	        results.setFont(f1);
    	        frame.add(results);
    	        
    	        area_show.setBounds(100, 120, 700, 560);
    			area_show.setFont(f2);
    	        frame.add(area_show);
    	        
    	        btn_close.setBounds(750, 0, 120, 100);
    	        btn_close.setFont(f1);
    	        frame.add(btn_close);
    	        
    	        area_clear.setBounds(8, 0, 120, 100);
    	        area_clear.setFont(f1);
    	        frame.add(area_clear);
    	        
    	        area_export.setBounds(138, 0, 120, 100);
    	        area_export.setFont(f1);
    	        frame.add(area_export);
    	        
    	        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
    			frame.setVisible(true); // 打开新窗口
            	String sql_name = "select * from Clothes where name LIKE '%"+area_select.getText()+"%'";
    			String sql_brand = "select * from Clothes where brand LIKE '%"+area_select.getText()+"%'";
    			String sql_clolr = "select * from Clothes where color LIKE '%"+area_select.getText()+"%'";
    			String sql_size = "select * from Clothes where size LIKE '%"+area_select.getText()+"%'";
    			String sql_suit = "select * from Clothes where suit LIKE '%"+area_select.getText()+"%'";
    			String sql_price = "select * from Clothes where price LIKE '%"+area_select.getText()+"%'"; //= '"+area_select.getText()+"'";
    			//String sql_number = "select * from Clothes where number = "+Integer.parseInt(area_select.getText())+"";
    			                     //int a = Integer.parseInt("12345");
    			String sql_Fname = "select * from Food where Fname LIKE '%"+area_select.getText()+"%'";
    			String sql_Fbrand = "select * from Food where Fbrand LIKE '%"+area_select.getText()+"%'";
    			String sql_gdate = "select * from Food where gdate LIKE '%"+area_select.getText()+"%'";
    			String sql_deadline = "select * from Food where deadline LIKE '%"+area_select.getText()+"%'";
    			String sql_birthplace = "select * from Food where birthplace LIKE '%"+area_select.getText()+"%'";
    			String sql_Fprice = "select * from Food where Fprice LIKE '%"+area_select.getText()+"%'";
    			//String sql_Fnumber = "select * from Food where Fnumber = " + Integer.parseInt(area_select.getText());
    			//在number(int类型)精确查找时，276行和284行的方法都行
    			
    			ArrayList<String> arrayList = new ArrayList();
    			arrayList.add(sql_name);
    			arrayList.add(sql_brand);
    			arrayList.add(sql_clolr);
    			arrayList.add(sql_size);
    			arrayList.add(sql_suit);
    			arrayList.add(sql_price);
    			//arrayList.add(sql_number);
    			
    			arrayList.add(sql_Fname);
    			arrayList.add(sql_Fbrand);
    			arrayList.add(sql_gdate);
    			arrayList.add(sql_deadline);
    			arrayList.add(sql_birthplace);
    			arrayList.add(sql_Fprice);
    			//arrayList.add(sql_Fnumber);
    			
    			for(int i = 0; i < arrayList.size(); i++) {
    				try {
    					queryTable(arrayList.get(i));
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					// area_select.append("\n查询失败: \n" + e1.getMessage());
    				}
    			}		
            }
		}else if (e.getSource() == btn_insert) {
			
			if(area_clothes.getText().length() >= 1) {
				String listClothes[] = area_clothes.getText().split(",");
				String insert_clothes = "INSERT INTO Clothes VALUES ('"+listClothes[0]+"','"+listClothes[1]+"', '"+listClothes[2]+"','"+listClothes[3]+"','"+listClothes[4]+"','"+listClothes[5]+"',"+Integer.parseInt(listClothes[6])+")";
				/*第七列为int型，"+Integer.parseInt(listClothes[6])+"和'"+listClothes[6]+"'都行，
				    但我感觉第一种合理，第二种将char型'24'强制转换为整型24。
				    在JAVA中，char a = '4'; int b = a;
				  System.out.println(a); 输出4  System.out.println(b); 输出52
				    但是执行SQL语句时好像情况有些不同*/
				try {
					insertTable(insert_clothes);
				} catch (SQLException e1) {
					//area_insert.append("\n进货失败: \n" + e1.getMessage());
				}
			}
			if(area_food.getText().length() >= 1) {
				String listFood[] = area_food.getText().split(",");
				String insert_food = "INSERT INTO Food VALUES ('"+listFood[0]+"','"+listFood[1]+"', '"+listFood[2]+"','"+listFood[3]+"','"+listFood[4]+"','"+listFood[5]+"','"+listFood[6]+"')";
				try {
					insertTable(insert_food);
				} catch (SQLException e2) {
					//area_insert.append("\n进货失败: \n" + e2.getMessage());
				}
			}
			
		}else if (e.getSource() == btn_delete) {
			if(area_delete.getText().length() >= 1) {
				String list[] = area_delete.getText().split(",");
				String delete_food = "DELETE FROM Food WHERE Fname = '"+list[0]+"' and Fbrand = '"+list[1]+"' and gdate = '"+list[2]+"' and deadline = '"+list[3]+"' and birthplace = '"+list[4]+"' and Fprice = '"+list[5]+"' and Fnumber = "+Integer.parseInt(list[6])+"";
				String delete_clothes = "DELETE FROM Clothes WHERE name = '"+list[0]+"' and brand = '"+list[1]+"' and color = '"+list[2]+"' and size = '"+list[3]+"' and suit = '"+list[4]+"' and price = '"+list[5]+"' and number = "+Integer.parseInt(list[6])+"";	
				try {
					delegteRecord(delete_food);
				} catch (SQLException e1) {
					//area_insert.append("\n进货失败: \n" + e1.getMessage());
				}
				try {
					delegteRecord(delete_clothes);
				} catch (SQLException e2) {
					//area_insert.append("\n进货失败: \n" + e2.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String sql_record =  "insert into Unshelve values('"+list[0]+"','"+list[1]+"', '"+list[2]+"','"+list[3]+"','"+list[4]+"','"+list[5]+"','"+list[6]+"','"+currentTime+"','"+area_reason.getText()+"')";
				try {
					insertTable(sql_record);
				} catch (SQLException e3) {
					//area_insert.append("\n进货失败: \n" + e3.getMessage());
				}
			}
			
		}else if (e.getSource() == btn_update) {
			if(sale_name.getText().length() >= 1) {
				String sale_clothes = "update Clothes C set C.number = C.number - "+Integer.parseInt(sale_num.getText())+" WHERE C.name = '"+sale_name.getText()+"'";	
				String sale_food = "update Food F set F.Fnumber = F.Fnumber - "+Integer.parseInt(sale_num.getText())+" WHERE F.Fname = '"+sale_name.getText()+"'";	
				try {
					updateTable(sale_clothes);
					String query_clothes = "Select number From Clothes C WHERE C.name = '"+sale_name.getText()+"'";	
					if(!con.isClosed()){	
					    Statement st = con.createStatement();
					    ResultSet rs = st.executeQuery(query_clothes);
					    while(rs.next()){
					    	if(rs.getInt("number") < 5){ 
						    	String inc_clothes = "update Clothes C set C.number = 100 WHERE C.name = '"+sale_name.getText()+"'";
						    	updateTable(inc_clothes);
						    }
					    }
				        rs.close();  // 关闭资源
				        st.close();  
				   }         
				} catch (SQLException e1) {
					//update_num.append("\n售卖失败: \n" + e1.getMessage());
				}
				try {
					updateTable(sale_food);
					String query_food = "Select Fnumber From Food  F WHERE F.Fname = '"+sale_name.getText()+"'";	
					if(!con.isClosed()){	
					    Statement st = con.createStatement();
					    ResultSet rs = st.executeQuery(query_food);
					    while(rs.next()){
					    	if(rs.getInt("Fnumber") < 5){
						    	String inc_food = "update Food  F set F.Fnumber = 100 WHERE F.Fname = '"+sale_name.getText()+"'";
						    	updateTable(inc_food);
						    }
					    } 
				        rs.close();  // 关闭资源
				        st.close();  
				   }         
				} catch (SQLException e2) {
					//update_num.append("\n售卖失败: \n" + e2.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String sale_record =  "insert into SaleRecord values('"+sale_name.getText()+"',"+Integer.parseInt(sale_num.getText())+",'"+currentTime+"')";
				try {
					insertTable(sale_record);
				} catch (SQLException e3) {
					//area_insert.append("\n记录失败: \n" + e3.getMessage());
				}
			}
		}else if (e.getSource() == btn_stock) {
			if(stock_name.getText().length() >= 1) {
				String stock_clothes = "update Clothes C set C.number = C.number + "+Integer.parseInt(stock_num.getText())+" WHERE C.name = '"+stock_name.getText()+"'";	
				String stock_food = "update Food F set F.Fnumber = F.Fnumber + "+Integer.parseInt(stock_num.getText())+" WHERE F.Fname = '"+stock_name.getText()+"'";	
				try {
					updateTable(stock_clothes);
				} catch (SQLException e4) {
					//update_num.append("\n售卖失败: \n" + e4.getMessage());
				}
				try {
					updateTable(stock_food);
				} catch (SQLException e4) {
					//update_num.append("\n售卖失败: \n" + e4.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String stock_record =  "insert into StockRecord values('"+stock_name.getText()+"',"+Integer.parseInt(stock_num.getText())+",'"+currentTime+"')";
				try {
					insertTable(stock_record);
				} catch (SQLException e3) {
					//area_insert.append("\n记录失败: \n" + e3.getMessage());
				}
			}
		}else if (e.getSource() == btn_analysis) {
			if(area_start.getText().length() >= 1 && area_end.getText().length() >= 1) {
            	String sql = "select * from SaleRecord where timeOfSale between '"+area_start.getText()+"' and '"+area_end.getText()+"'";
            	try {
					if(!con.isClosed()){
					   Statement st = con.createStatement();
					   ResultSet rs = st.executeQuery(sql);
					   boolean flag = true;
					   if(rs != null) {
					       while(rs.next()){
					    	 ResultSetMetaData rsmd = rs.getMetaData();  
					         int countcols = rsmd.getColumnCount(); 
					      	 if(flag) {
					             for(int i = 1; i <= countcols; i++) {  
					                area_analysis.append(rsmd.getColumnName(i) + "  ");  
					             } 
					             area_analysis.append("\n");
					         }
					         flag = false;
					         int i;
					         for(i = 1; i < countcols; i++) {
					            area_analysis.append(rs.getString(i)+"    "); // 将数据写到TextArea里面
					         }
					         area_analysis.append(rs.getString(3)+"\n");
					      }
					   }
					   if(!flag) {
				    		area_results.append("统计成功\n");
				       }
					   rs.close();
					   st.close();  
			        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		}else if (e.getSource() == main_export) {
			FileWriter fw = null;
			try {//.xls
				fw = new FileWriter("D:\\MyDesktop\\统计结果.xls");
				for(String str : area_analysis.getText().split("\n")){
		            fw.write(str + "\r\n");
		        } 
		        fw.close();
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				System.out.println("导出失败：\n" + exc.getMessage());	
			}
			
		}else if (e.getSource() == main_clear) {
			area_select.setText("");
		    area_clothes.setText("");
		    area_food.setText("");
		    area_delete.setText("");
		    area_results.setText("");
		    sale_name.setText("");
		    sale_num.setText("");
		    stock_name.setText("");
		    stock_num.setText("");
		    area_reason.setText("");
		    area_results.setText("");
		    area_start.setText("");
		    area_end.setText("");
		    area_analysis.setText("");
		}else if (e.getSource() == main_close) {
			//jf.dispose();  // 关闭并销毁
			jf.setVisible(false);// 无需销毁
		}
	}
    public void insertTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("操作成功\n");
           }
           st.close();  
        }         
    }
    public void updateTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("当前按钮引发的事件\n"
   					+ "操作成功\n");  
           }
           st.close();  
        }         
    }
    public void delegteRecord(String sql) throws SQLException {
    	
        if(!con.isClosed()){	
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("下架按钮引发的事件\n"
   					+ "下架成功\n");
           }
           st.close();  
        }         
    }
    public void mutipleResult(ResultSet rs) throws SQLException {
    	
    	boolean flag = true;
    	if(rs != null) {
            while(rs.next()){ // 读取结果集中的数据
            	ResultSetMetaData rsmd = rs.getMetaData();  
                int countcols = rsmd.getColumnCount(); 
            	if(flag) {
                    for(int i = 1; i <= countcols; i++) {  
                        area_show.append(rsmd.getColumnName(i) + "  ");  
                    } 
                    area_show.append("\n");
            	}
            	flag = false;
            	int i;
            	for(i = 1; i < countcols; i++) {
            		area_show.append(rs.getString(i)+"  "); // 将数据写到TextArea里面
            	}
                area_show.append(rs.getString(i)+"\n");
          }
        }
    	if(!flag) {
    		area_results.append("查询成功\n");
    	}
    }
    public void queryTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){          //判断数据库是否链接成功
           //System.out.println("Connect Successfull.");	
	       Statement st = con.createStatement();
	       ResultSet rs = st.executeQuery(sql);//查询之后返回结果集 
	       mutipleResult(rs);
           rs.close();  // 关闭资源
           st.close();  
        }         
    }
    public static void jdbc() {
    	
    	String driver = "com.mysql.cj.jdbc.Driver";  // 驱动路径
        String URL = "jdbc:mysql://localhost:3306/supermarket" // 数据库地址
        		+ "?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=GMT";
        String user = "cyy";  // 访问数据库的用户名
        String password ="cyy7215217758"; // 用户密码        
        try {  
            Class.forName(driver);  // 加载驱动
        } catch(java.lang.ClassNotFoundException e) {  
            System.out.println("Cant't load Driver");  
        }  
        try {                                                                                 
            con = DriverManager.getConnection(URL,user,password); //链接数据库
            if(!con.isClosed()){  // 判断数据库是否链接成功
            	area_state.setText("Connect Successfull.");
            }
        }catch(Exception e){ 
            System.out.println("Connect fail:" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
		
    	jdbc();
    	new simpleFunction();  
    }
}
/* 不足之处(前三条再花一些时间可以实现)
 * 1.在售卖和补货时，在按钮处只根据商品名称一个属性，所以同名的都会做出相同动作
 *     但是添加几个JLabel和几个JTextField或JTextArea后,就可以弥补该缺点
 * 2.该系统不能够根据商品的任意属性的任意组合进行查询实时库存信息和商品明细，只能根据任意单一属性，
 *     但是添加几个CheckBox和几个JTextField或JTextArea后,就可以实现上面要求
 * 3.销售记录表，进货记录表都没记录金额，统计时也没有统计金额    
 *     但是这个自己可以实现，只是较为麻烦而已
 * 4.销售实现的过程尽量使用数据库事物
 *     没有使用数据库事物，纯JAVA知识(swing)和SQL相关知识
 */