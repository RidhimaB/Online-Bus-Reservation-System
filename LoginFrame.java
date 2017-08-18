		/*     Login Form */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class LoginFrame extends JFrame {

	JLabel lbl1,lbl2;
	JTextField tf;
	JPasswordField pf;
	JButton b1;
	JLabel inf;

	PrintWriter os=null;
	BufferedReader is=null;

	LoginFrame(BufferedReader is, PrintWriter os) {
		this.os=os;
		this.is=is;

		setTitle("LoginForm");
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);


		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,30,330,50);	

		Font f=new Font("Times New Roman",Font.PLAIN,15);

		lbl1=new JLabel("UserName : ");
		lbl1.setFont(f);

		lbl2=new JLabel("Password : ");
		lbl2.setFont(f);
		tf=new JTextField(15);
		tf.setFont(f);
		tf.requestFocus();

		pf=new JPasswordField(15);
		pf.setFont(f);

		b1=new JButton("Login");
		b1.setFont(f);
	
		setLayout(null);
	
		lbl1.setBounds(100,100,100,25);
		lbl2.setBounds(100,150,100,25);
		tf.setBounds(200,100,100,25);
		pf.setBounds(200,150,100,25);
		b1.setBounds(150,220,100,25);

		add(inf);
		add(lbl1);
		add(lbl2);
		add(tf);
		add(pf);				
		add(b1);

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String user=tf.getText();
				char passwrd[]=pf.getPassword();
				String pass=new String(passwrd);
				loginMe(user,pass);
			}
		});
	}

	public void loginMe(String user,String pass) {
		os.println(2+","+user+","+pass);
		os.flush();
		try{
			String data=is.readLine();
			if(data.equals("success")) {
				this.setVisible(false);
				new ReservationFrame(is,os,user);
			}
			else{
				JOptionPane.showMessageDialog(null,"Sorry! Please Login Again...");
			}
		}catch(IOException e){ }
		tf.setText(null);
		pf.setText(null);
	}
}