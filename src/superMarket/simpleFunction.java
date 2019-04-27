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
	//������������(����)
	static JFrame jf = new JFrame("�򵥹���������"); 
	// �����������(��ť)
    JButton btn_select = new JButton("��ѯ");   // ������Ʒ�����ⵥһ����(Food,Clothes����)
    JButton btn_insert = new JButton("��Ʒ");   // ���������ָ�ʽ  ����,������,��,L,������,198RMB,150
    JButton btn_delete = new JButton("�¼�");   // �¼ܵ������ʽͬ��Ʒ ���黨,ʲ��,8����,2019-05-22,���,58RMB,30
    JButton btn_update = new JButton("����");   // ��һ���ı���������Ʒ����,�ڶ�����������
    JButton btn_stock = new JButton("����");    // �����������ʽͬ����
    JButton btn_analysis = new JButton("ͳ��"); // ������ʼ��ֹʱ��2018-06-01
    JButton main_clear = new JButton("���");
    JButton main_close = new JButton("�ر�");       
    JButton main_export = new JButton("����");	
    // ��ǩ
    JLabel state = new JLabel("���ݿ�����״̬");
    JLabel label_state = new JLabel("����״̬");
    JLabel label_Cname = new JLabel("����");
    JLabel label_Cnum = new JLabel("����");
    JLabel label_Fname = new JLabel("����");
    JLabel label_Fnum = new JLabel("����");
    JLabel label_food = new JLabel("ʳƷ");
    JLabel label_clothes = new JLabel("�·�");
    JLabel label_good = new JLabel("��Ʒ");
    JLabel label_reason = new JLabel("ԭ��");
    JLabel label_start = new JLabel("��ʼ");
    JLabel label_end = new JLabel("��ֹ");
    JLabel label_analysis = new JLabel("ͳ�ƽ��չʾ");
    // �����ı���
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
    
    // ����ָ���������ơ���ʽ�Ͱ�ֵ��С������һ���� Font��
    Font f1 = new Font("�����п�",Font.BOLD,40);
    Font f2 = new Font("����",Font.BOLD,20);
    Font f3 = new Font("�����п�",Font.BOLD,20);
    Font f4 = new Font("�����п�",Font.BOLD,30);
    Font f5 = new Font("����",Font.BOLD,30);
    Font f6 = new Font("����",Font.BOLD,40);
    Font f7 = new Font("�����п�",Font.BOLD,35);
    Font f8 = new Font("�����п�",Font.BOLD,50);
    
    // ��ѯ���չʾ����(�´���)
    static JFrame frame = new JFrame("��ѯ���չʾ�´���");
    JTextArea area_show = new JTextArea();
    JButton btn_close = new JButton(new AbstractAction("�ر�") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
    		frame.setVisible(false); // ��������
    		//frame.dispose();       // �رղ�����		
    	}
    });
    JButton area_clear = new JButton(new AbstractAction("���") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
    		area_show.setText("");
    	}
    });
    
    JButton area_export = new JButton(new AbstractAction("����") {      
		private static final long serialVersionUID = 1L;
		@Override   
    	public void actionPerformed(ActionEvent e) {
			FileWriter fw = null;
			try { //���ڵ���Ϊ.txtû�����⣬����.pdf���ɵ��ļ��򲻿�
				fw = new FileWriter("D:\\MyDesktop\\��ѯ���.xls");
				for(String str : area_show.getText().split("\n")){
		            fw.write(str + "\r\n");
		        } //����.xls .rtf��.doc�������������,�ļ����Դ򿪲���������ʾ
		        fw.close();
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				System.out.println("����ʧ�ܣ�\n" + exc.getMessage());	
			}
    	}
    });
    
	public simpleFunction() {
		
        jf.setLayout(null);                        // ȡ�����ֹ�����
        jf.setSize(1200, 1390);                    // ���ô��ڴ�С ��1200����1380
        jf.setLocationRelativeTo(null);            // �Ѵ���λ�����õ���Ļ����
        
        main_export.setBounds(70, 10, 120, 60);    // x����70��y����10����120����60
        main_export.setFont(f1);                   // ѡ��������ʽf1
        jf.add(main_export);                       // ��ӵ����������
        
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
        
        // ��������ڵĹرհ�ťʱ�˳�����û����һ�䣬���򲻻��˳���
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        // ��ʾ���ڣ�ǰ�洴������Ϣ�����ڴ��У����ڴ��еĴ�����ʾ����Ļ��
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
    		    JLabel results = new JLabel("��ѯ�������ʾ");
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
    			frame.setVisible(true); // ���´���
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
    			//��number(int����)��ȷ����ʱ��276�к�284�еķ�������
    			
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
    					// area_select.append("\n��ѯʧ��: \n" + e1.getMessage());
    				}
    			}		
            }
		}else if (e.getSource() == btn_insert) {
			
			if(area_clothes.getText().length() >= 1) {
				String listClothes[] = area_clothes.getText().split(",");
				String insert_clothes = "INSERT INTO Clothes VALUES ('"+listClothes[0]+"','"+listClothes[1]+"', '"+listClothes[2]+"','"+listClothes[3]+"','"+listClothes[4]+"','"+listClothes[5]+"',"+Integer.parseInt(listClothes[6])+")";
				/*������Ϊint�ͣ�"+Integer.parseInt(listClothes[6])+"��'"+listClothes[6]+"'���У�
				    ���Ҹо���һ�ֺ����ڶ��ֽ�char��'24'ǿ��ת��Ϊ����24��
				    ��JAVA�У�char a = '4'; int b = a;
				  System.out.println(a); ���4  System.out.println(b); ���52
				    ����ִ��SQL���ʱ���������Щ��ͬ*/
				try {
					insertTable(insert_clothes);
				} catch (SQLException e1) {
					//area_insert.append("\n����ʧ��: \n" + e1.getMessage());
				}
			}
			if(area_food.getText().length() >= 1) {
				String listFood[] = area_food.getText().split(",");
				String insert_food = "INSERT INTO Food VALUES ('"+listFood[0]+"','"+listFood[1]+"', '"+listFood[2]+"','"+listFood[3]+"','"+listFood[4]+"','"+listFood[5]+"','"+listFood[6]+"')";
				try {
					insertTable(insert_food);
				} catch (SQLException e2) {
					//area_insert.append("\n����ʧ��: \n" + e2.getMessage());
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
					//area_insert.append("\n����ʧ��: \n" + e1.getMessage());
				}
				try {
					delegteRecord(delete_clothes);
				} catch (SQLException e2) {
					//area_insert.append("\n����ʧ��: \n" + e2.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String sql_record =  "insert into Unshelve values('"+list[0]+"','"+list[1]+"', '"+list[2]+"','"+list[3]+"','"+list[4]+"','"+list[5]+"','"+list[6]+"','"+currentTime+"','"+area_reason.getText()+"')";
				try {
					insertTable(sql_record);
				} catch (SQLException e3) {
					//area_insert.append("\n����ʧ��: \n" + e3.getMessage());
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
				        rs.close();  // �ر���Դ
				        st.close();  
				   }         
				} catch (SQLException e1) {
					//update_num.append("\n����ʧ��: \n" + e1.getMessage());
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
				        rs.close();  // �ر���Դ
				        st.close();  
				   }         
				} catch (SQLException e2) {
					//update_num.append("\n����ʧ��: \n" + e2.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String sale_record =  "insert into SaleRecord values('"+sale_name.getText()+"',"+Integer.parseInt(sale_num.getText())+",'"+currentTime+"')";
				try {
					insertTable(sale_record);
				} catch (SQLException e3) {
					//area_insert.append("\n��¼ʧ��: \n" + e3.getMessage());
				}
			}
		}else if (e.getSource() == btn_stock) {
			if(stock_name.getText().length() >= 1) {
				String stock_clothes = "update Clothes C set C.number = C.number + "+Integer.parseInt(stock_num.getText())+" WHERE C.name = '"+stock_name.getText()+"'";	
				String stock_food = "update Food F set F.Fnumber = F.Fnumber + "+Integer.parseInt(stock_num.getText())+" WHERE F.Fname = '"+stock_name.getText()+"'";	
				try {
					updateTable(stock_clothes);
				} catch (SQLException e4) {
					//update_num.append("\n����ʧ��: \n" + e4.getMessage());
				}
				try {
					updateTable(stock_food);
				} catch (SQLException e4) {
					//update_num.append("\n����ʧ��: \n" + e4.getMessage());
				}
				Date date =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String currentTime = sdf.format(date);
				String stock_record =  "insert into StockRecord values('"+stock_name.getText()+"',"+Integer.parseInt(stock_num.getText())+",'"+currentTime+"')";
				try {
					insertTable(stock_record);
				} catch (SQLException e3) {
					//area_insert.append("\n��¼ʧ��: \n" + e3.getMessage());
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
					            area_analysis.append(rs.getString(i)+"    "); // ������д��TextArea����
					         }
					         area_analysis.append(rs.getString(3)+"\n");
					      }
					   }
					   if(!flag) {
				    		area_results.append("ͳ�Ƴɹ�\n");
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
				fw = new FileWriter("D:\\MyDesktop\\ͳ�ƽ��.xls");
				for(String str : area_analysis.getText().split("\n")){
		            fw.write(str + "\r\n");
		        } 
		        fw.close();
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				System.out.println("����ʧ�ܣ�\n" + exc.getMessage());	
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
			//jf.dispose();  // �رղ�����
			jf.setVisible(false);// ��������
		}
	}
    public void insertTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("�����ɹ�\n");
           }
           st.close();  
        }         
    }
    public void updateTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("��ǰ��ť�������¼�\n"
   					+ "�����ɹ�\n");  
           }
           st.close();  
        }         
    }
    public void delegteRecord(String sql) throws SQLException {
    	
        if(!con.isClosed()){	
	       Statement st = con.createStatement();
	       int checkCreate = st.executeUpdate(sql);          
           if(checkCreate != -1){  
        	   area_results.append("�¼ܰ�ť�������¼�\n"
   					+ "�¼ܳɹ�\n");
           }
           st.close();  
        }         
    }
    public void mutipleResult(ResultSet rs) throws SQLException {
    	
    	boolean flag = true;
    	if(rs != null) {
            while(rs.next()){ // ��ȡ������е�����
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
            		area_show.append(rs.getString(i)+"  "); // ������д��TextArea����
            	}
                area_show.append(rs.getString(i)+"\n");
          }
        }
    	if(!flag) {
    		area_results.append("��ѯ�ɹ�\n");
    	}
    }
    public void queryTable(String sql) throws SQLException {
    	
        if(!con.isClosed()){          //�ж����ݿ��Ƿ����ӳɹ�
           //System.out.println("Connect Successfull.");	
	       Statement st = con.createStatement();
	       ResultSet rs = st.executeQuery(sql);//��ѯ֮�󷵻ؽ���� 
	       mutipleResult(rs);
           rs.close();  // �ر���Դ
           st.close();  
        }         
    }
    public static void jdbc() {
    	
    	String driver = "com.mysql.cj.jdbc.Driver";  // ����·��
        String URL = "jdbc:mysql://localhost:3306/supermarket" // ���ݿ��ַ
        		+ "?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=GMT";
        String user = "cyy";  // �������ݿ���û���
        String password ="cyy7215217758"; // �û�����        
        try {  
            Class.forName(driver);  // ��������
        } catch(java.lang.ClassNotFoundException e) {  
            System.out.println("Cant't load Driver");  
        }  
        try {                                                                                 
            con = DriverManager.getConnection(URL,user,password); //�������ݿ�
            if(!con.isClosed()){  // �ж����ݿ��Ƿ����ӳɹ�
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
/* ����֮��(ǰ�����ٻ�һЩʱ�����ʵ��)
 * 1.�������Ͳ���ʱ���ڰ�ť��ֻ������Ʒ����һ�����ԣ�����ͬ���Ķ���������ͬ����
 *     ������Ӽ���JLabel�ͼ���JTextField��JTextArea��,�Ϳ����ֲ���ȱ��
 * 2.��ϵͳ���ܹ�������Ʒ���������Ե�������Ͻ��в�ѯʵʱ�����Ϣ����Ʒ��ϸ��ֻ�ܸ������ⵥһ���ԣ�
 *     ������Ӽ���CheckBox�ͼ���JTextField��JTextArea��,�Ϳ���ʵ������Ҫ��
 * 3.���ۼ�¼��������¼��û��¼��ͳ��ʱҲû��ͳ�ƽ��    
 *     ��������Լ�����ʵ�֣�ֻ�ǽ�Ϊ�鷳����
 * 4.����ʵ�ֵĹ��̾���ʹ�����ݿ�����
 *     û��ʹ�����ݿ������JAVA֪ʶ(swing)��SQL���֪ʶ
 */