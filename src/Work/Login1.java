package Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login1 extends JFrame implements ActionListener {

	JButton jb1,jb2 = null;//按钮
	JPanel jp1,jp2,jp3 = null;//面板
	JTextField jtf1 = null;//文本输入框
	JPasswordField jpf1 = null;//密码框
	JLabel jlb1,jlb2 = null;//标签

	static String userword;
	static String pwd;
	static Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {

		Login1 login = new Login1();//实例化
		
	}

	// 这里可以设置数据库名称
	private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Cashier";
	private static final String USER = "sa";
	private static final String PASSWORD = "123";

	private static Connection conn = null;
	// 静态代码块（将加载驱动、连接数据库放入静态块中）
	static {
		try {
			// 1.加载驱动程序
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 2.获得数据库的连接
			conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 对外提供一个方法来获取数据库连接
	public static Connection getConnection() {
		return conn;
	}
	
	public Login1(){
		jb1 = new JButton("登录");
		jb2 = new JButton("退出");
		
		jlb1 = new JLabel("用户名");
		jlb2 = new JLabel("密码");
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jtf1 = new JTextField(10);
		jpf1 = new JPasswordField(10);
		
		jp1.add(jlb1);
		jp1.add(jtf1);
		
		jp2.add(jlb2);
		jp2.add(jpf1);
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		jb1.addActionListener(this);//设置监听
		jb2.addActionListener(this);
		
		jp1.setOpaque(false);//面板透明化
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		
        ImageIcon img = new ImageIcon("D:\\照片\\高清壁纸\\3.jpg"); 
        JLabel jl_bg = new JLabel(img); //背景
        jl_bg.setBounds(0, 0, 600, 440); //设置位置和大小。  
        this.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));  
        ((JPanel)this.getContentPane()).setOpaque(false); //设置透明  
        
		Image icon = Toolkit.getDefaultToolkit().getImage("D:\\照片\\高清壁纸\\s.jpg"); 
		this.setIconImage(icon); 
		//更换图标和背景
		
		
		this.setTitle("收银系统");
		this.setLayout(new GridLayout(3,1));//设置布局管理器
		this.setSize(400,250);//设置界面大小
		this.setLocation(800,300);//设置初始位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗口关闭时，保证JVM退出
		this.setVisible(true);//显示或隐藏窗口
		this.setResizable(false);//窗口大小是否可调
		
	}
	public void actionPerformed (ActionEvent e){
		if(e.getActionCommand() == "登录"){
			try{
				boolean flag = false;
				ct = getConnection();
				ps = ct.prepareStatement("select * from Manager");
				rs = ps.executeQuery();
				while(rs.next()){
					userword = rs.getString(1);
					pwd = rs.getString(3);
					userword = userword.replaceAll(" ", "");
					pwd = pwd.replaceAll(" ", "");
					if (userword.equals(jtf1.getText())){
						ManagerLogin();
						flag = true;
					}					
				}
				if(flag == false){
					clear();
					JOptionPane.showMessageDialog(null, "无此管理员", "提示消息", JOptionPane.WARNING_MESSAGE);
				}
			}catch(SQLException e1){
					e1.printStackTrace();
				}

		}else if(e.getActionCommand() == "退出"){
			System.exit(0);//退出系统
		}
	}
	
	public void ManagerLogin(){
		if(userword.equals(jtf1.getText())&&pwd.equals(jpf1.getText())){
			clear();
			JOptionPane.showMessageDialog(null, "登录成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
			dispose();
			UI ui = new UI(userword);
			
		}else {
			clear();
			JOptionPane.showMessageDialog(null, "用户名或密码错误，请重试", "提示消息", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void clear(){
		jtf1.setText("");
		jpf1.setText("");
	}

}
