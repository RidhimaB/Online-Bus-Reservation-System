				/*    Registration Form */
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

class RegistrationFrame extends JFrame{
	JLabel lbl1,lbl2,lbl3,lbl4;
	JTextField tf,mobile,email;
	JPasswordField pf;
	JButton b1;
	JLabel inf=null;
	PrintWriter os=null;
	BufferedReader is=null;

	public RegistrationFrame(BufferedReader is, PrintWriter os) {
		this.os=os;
		this.is=is;

		setTitle("RegistrationForm");
		setSize(400,500);
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

		lbl3=new JLabel("Mobile : ");
		lbl3.setFont(f);

		lbl4=new JLabel("Email : ");
		lbl4.setFont(f);

		tf=new JTextField(15);
		tf.setFont(f);
		tf.requestFocus();

		pf=new JPasswordField(15);
		pf.setFont(f);
		b1=new JButton("Submit");
		b1.setFont(f);

		mobile=new JTextField(15);
		mobile.setFont(f);

		email=new JTextField(15);
		email.setFont(f);

		setLayout(null);

		lbl1.setBounds(80,100,100,25);
		lbl2.setBounds(80,150,100,25);
		lbl3.setBounds(80,200,100,25);
		lbl4.setBounds(80,250,100,25);

		tf.setBounds(150,100,150,25);
		pf.setBounds(150,150,150,25);
		mobile.setBounds(150,200,150,25);
		email.setBounds(150,250,150,25);


		b1.setBounds(120,320,100,25);

		add(inf);
		add(lbl1);
		add(lbl2);
		add(lbl3);
		add(lbl4);
		add(tf);
		add(pf);				
		add(mobile);
		add(email);
		add(b1);

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String user=tf.getText();
				char passwrd[]=pf.getPassword();
				String pass=new String(passwrd);
				String mobnum = mobile.getText();
				String mail = email.getText();
				registerMe(user,pass,mobnum,mail);
			}
		});
	}

	public void registerMe(String user,String pass,String mobnum, String mail){
		os.println(1+","+user+","+pass+","+mobnum+","+mail);
		os.flush();
		try{
			String data=is.readLine();
			JOptionPane.showMessageDialog(null,data);
		}catch(IOException e){}
		tf.setText(null);
		pf.setText(null);
		mobile.setText(null);
		email.setText(null);
		tf.requestFocus();
	}
}
	