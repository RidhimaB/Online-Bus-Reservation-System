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

class MenuFrame extends JFrame {
	JButton register = null;
	JButton login = null;

	JLabel inf=null;
	PrintWriter os=null;
	BufferedReader is=null;
	MenuFrame (BufferedReader is, PrintWriter os) {
		this.os=os;
		this.is=is;

		setTitle("Welcome Form");
		setSize(400,280);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);


		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,10,330,50);

		register = new JButton("Register");
		register.setBounds(120,80,100,40);
		login = new JButton("Login");
		login.setBounds(120,150,100,40);

		setLayout(null);

		add(inf);
		add(register);
		add(login);


		register.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getRegistrationForm();
			}
		});

		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getLoginForm();
			}
		});

	}
		
	public void getLoginForm(){
		new LoginFrame(is,os);
	}

	public void getRegistrationForm(){
		new RegistrationFrame(is,os);
	}

}