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

class ReservationFrame extends JFrame {

	JButton reservation = null;
	JButton cancellation = null;
	JButton details=null;
	JLabel inf=null;

	PrintWriter os=null;
	BufferedReader is=null;	
	String username=null;

	ReservationFrame(BufferedReader ins, PrintWriter ops,String user) {
		this.os=ops;
		this.is=ins;
		this.username=user;

		setTitle("Reservation Form");
		setSize(400,300);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,20,330,50);	

		reservation = new JButton("Reservation");
		reservation.setBounds(100,80,150,40);
		cancellation = new JButton("Cancellation");
		cancellation.setBounds(100,130,150,40);
		details = new JButton("Availability");
		details.setBounds(100,180,150,40);

		setLayout(null);
		add(inf);
		add(reservation);
		add(cancellation);
		add(details);

		reservation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new BookingFrame(is,os,username);
			}
		});

		cancellation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CancellationFrame(is,os,username);
			}
		});

		details.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new DetailsFrame(is,os);
			}
		});
	}
}