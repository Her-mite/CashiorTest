package Work;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class UI extends JFrame implements ActionListener {

	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp0 = new JPanel();
	JPanel jp4 = new JPanel();
	JPanel jp5 = new JPanel();
	JPanel jp6 = new JPanel();
	JPanel jp7 = new JPanel();
	
	JLabel jlb1 = new JLabel("商品编号");
	JLabel jlb2 = new JLabel("商品名");
	JLabel jlb3 = new JLabel("商品金额");
	JLabel jlb0 = new JLabel("商品数量");
	JLabel jlb4 = new JLabel("是否VIP？");
	JLabel jlb5 = new JLabel("若否则不填");
	JLabel jlb6 = new JLabel("VIP编号");
	
	JButton jb1 = new JButton("确认收款");
	JButton jb2 = new JButton("确定");
	JButton jb3 = new JButton("下一件商品");
	JButton jb4 = new JButton("取消购买");
	
	String str[] = {"否","是"};	   
	JComboBox jcb = new JComboBox(str);
	
	JTextField jtf1 = new JTextField(10);//商品编号输入框	
	JTextField jtf2 = new JTextField(10);//商品名称输入框
	JTextField jtf0 = new JTextField(10);//商品数量输入诓
	JTextField jtf3 = new JTextField(10);//商品金额显示框
	JTextField jtf4 = new JTextField(10);//VIP编号输入框	
 	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	static String ID; //商品信息
	static String ID1;
	static String Name;
	static String amount;
	static int amount1;
	static int stock;
	static int count = 0;
	static int num;
	static Object[][] info = new Object[3][4];
	
	final String sa;
	private JScrollPane scpDemo;
	private JTableHeader jth;
    private JTable tabDemo;                                                                                                           
	static int VIPID;
	static int VIPscore;
    //这里可以设置数据库名称
    private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Cashier";
    private static final String USER="sa";
    private static final String PASSWORD="123";
    
    private static Connection conn=null;
    
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
            conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection(){
        return conn;
    }
	
	public UI(String a){	
		sa = a;
		
		jp1.add(jlb1);
		jp1.add(jtf1);
		jp1.add(jb2);
		jp2.add(jlb2);
		jp2.add(jtf2);
		jp3.add(jlb3);
		jp3.add(jtf3);
		jp0.add(jlb0);
		jp0.add(jtf0);
		jp4.add(jlb4);
		jp4.add(jcb);
		jp5.add(jb3);
		jp6.add(jb4);
		jp6.add(jb1);
		jp7.add(jlb6);
		jp7.add(jtf4);
		jp7.add(jlb5);
		
        this.scpDemo = new JScrollPane();//带滚动条的面板
        this.scpDemo.setBounds(100,300,600,500);
        
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		
		jp1.setOpaque(false);//面板透明化
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		jp0.setOpaque(false);
		jp4.setOpaque(false);
		jp5.setOpaque(false);
		jp6.setOpaque(false);
		jp7.setOpaque(false);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false);
		
		this.add(jp1);	
		this.add(jp2);
		this.add(jp3);
		this.add(jp0);
		this.add(jp4);	
		this.add(jp7);
		this.add(jp5);
		this.add(jp6);
		this.add(scpDemo);
		
		 	ImageIcon img = new ImageIcon("D:\\照片\\高清壁纸\\1.jpg"); 
	        JLabel jl_bg = new JLabel(img); //背景
	        jl_bg.setBounds(0, 0, 1200, 700); //设置位置和大小。  
	        this.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));  
	        ((JPanel)this.getContentPane()).setOpaque(false); //设置透明  
	       	        
			Image icon = Toolkit.getDefaultToolkit().getImage("D:\\照片\\高清壁纸\\s.jpg"); 
			this.setIconImage(icon); 
			//更换图标和背景
		

		this.setLayout(new GridLayout(9,2,5,5));
		this.setTitle("收银");
		this.setSize(400,550);
		this.setLocation(800,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true); 
		
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand() == "确定"){
			try{
				ct = getConnection();
				ps = ct.prepareStatement("select * from Product");
				rs = ps.executeQuery();
				boolean flag = false;
				jtf0.setText("1");//默认商品数量为1
				while(rs.next()){					
					ID = rs.getString(1);
					ID = ID.replaceAll(" ", "");
					Name = rs.getString(2);
					amount = rs.getString(3);					
					//stock = rs.getInt(4);
					if(ID.equals(jtf1.getText())){
						ID1 = rs.getString(1);					
						amount1 = rs.getInt(3);
						jtf2.setText(Name);
						jtf3.setText(amount);
						flag = true;
					}
				}
				if(!flag){
					clear();
					JOptionPane.showMessageDialog(null, "无此商品信息", "提示消息", JOptionPane.WARNING_MESSAGE);
				}
			}catch (SQLException e1){
				e1.printStackTrace();
			}	
			
		}else if(e.getActionCommand() == "下一件商品"){
			String s3 = (String) jcb.getSelectedItem();			
			if(s3.equals("是") && "".equals(jtf4.getText().trim()))//确保vip编号不为空
				{
				JOptionPane.showMessageDialog(null, "若是VIP请输入VIP号", "提示消息", JOptionPane.WARNING_MESSAGE);			
				}else if(count >= 3)
					JOptionPane.showMessageDialog(null, "一次最多购买三件商品！", "提示消息", JOptionPane.WARNING_MESSAGE);
				else if("".equals(jtf2.getText()))
					JOptionPane.showMessageDialog(null, "请先确认商品信息", "提示消息", JOptionPane.WARNING_MESSAGE);
				else 
				try{
					   Date d=new Date(System.currentTimeMillis());				
				  	   String sql = "insert into Records values(?,?,?,?,?,?,?,?,?) "; //向记录表内插入新的一行数据 
					   PreparedStatement pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					   int x = (int)(Math.random()*1000);					  
					   pst.setInt(1,x);//流水号
					   
					   pst.setDate(2,d);//时间
					   pst.setString(3,ID1);//商品编号
					   pst.setString(4,jtf2.getText());//商品名
					   if(s3.equals("否")){
						   pst.setInt(5,0);//0表示不是VIP
					   	   pst.setString(6,null);
					   } 						   
					   else if(s3.equals("是")){//若是VIP则查找是否有该VIP信息
						   try{
								ct = getConnection();
								ps = ct.prepareStatement("select * from VIP");
								rs = ps.executeQuery();
								boolean flag = false;
								
								while(rs.next()){					
									ID = rs.getString(1);
									ID = ID.replaceAll(" ", "");
									if(ID.equals(jtf4.getText())){//判断确实有该VIP 将信息写入
										pst.setInt(5, 1);         
								   		pst.setString(6, jtf4.getText());
								   		flag = true;
									}							
								}	
								if(!flag)
									JOptionPane.showMessageDialog(null, "无此VIP信息！！！", "提示消息", JOptionPane.WARNING_MESSAGE);
							}catch (SQLException e1){
								e1.printStackTrace();
							}
						   		
					   		}
					   pst.setInt(7,amount1);//商品金额
					   pst.setString(8,sa);//操作员
					   pst.setInt(9,0);
					   pst.executeUpdate();  
					   pst.close(); 
						
					   num = Integer.parseInt(jtf0.getText());
					   
                       info[count][0] = ID1;
                       info[count][1] = jtf2.getText();
                       info[count][2] = amount1;                        
                       info[count][3] = num;
                       
                       String[] title = {"商品编号","商品名","商品金额","购买数量"};
                       // 创建JTable
                       this.tabDemo = new JTable(info,title);
                       // 显示表头
                       this.jth = this.tabDemo.getTableHeader();
                       // 将JTable加入到带滚动条的面板中
                       this.scpDemo.getViewport().add(tabDemo); 
                       count ++;
					   clear();
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}else if(e.getActionCommand() == "确认收款"){
			int all = 0;
			for(int i = 0; i < count; i ++){
				String getname= tabDemo.getValueAt(i, 2).toString();
				int all1 = Integer.parseInt(getname);
				all += all1;				
			}
			System.out.println(all);
			JOptionPane.showMessageDialog(null, "付款成功，共" + all + "元", "提示消息", JOptionPane.WARNING_MESSAGE);
			clear();//清空文本框
			for(int i = 0;i < 3; i ++)    //将列表数据清空
			{
				info[i][0] = "";
				info[i][1] = "";
				info[i][2] = "";                        
				info[i][3] = ""; 
			}
			String[] title = {"商品编号","商品名","商品金额","购买数量"};
             // 创建JTable
             this.tabDemo = new JTable(info,title);
             // 显示表头
             this.jth = this.tabDemo.getTableHeader();
             // 将JTable加入到带滚动条的面板中
             this.scpDemo.getViewport().add(tabDemo); 
             count = 0;  //初始化计数变量 
		}else if(e.getActionCommand() == "取消购买"){

			dispose();
		}
		
	}

	public static void main(String[] args) {
		String a = "fsas";
		UI ui = new UI(a);
	}
	public void clear(){
		jtf1.setText("");
		jtf2.setText("");
		jtf3.setText("");
		jtf4.setText("");
		jtf0.setText("");
	}

}
