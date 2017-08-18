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

class BookingFrame extends JFrame {

	JComboBox source = null;
	JComboBox destination = null;
	JButton next = null;
	JTextField doj=null;
	JLabel inf = null;
	String username=null;

	PrintWriter os=null;
	BufferedReader is=null;

	BookingFrame(BufferedReader is, PrintWriter os, String username) {
		this.os=os;
		this.is=is;
		this.username=username;

		setTitle("Booking Form");
		setSize(400,400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] src = {"montgomery","troy","dothan"};
		String[] dest = {"montgomery","troy","dothan"};
	
		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,15,330,50);	
	
		

		
		source = new JComboBox(src);
		destination = new JComboBox(dest);
		doj =new JTextField();
		next = new JButton("Billing");
		
		Font f=new Font("Calibri",Font.PLAIN,18);
	
		JLabel lbl1=new JLabel("Boarding Point  ");
		lbl1.setFont(f);
		lbl1.setBounds(50,60,150,40);
		source.setBounds(50,100,110,30);

		JLabel lbl2=new JLabel("Destination Point  ");
		lbl2.setFont(f);
		lbl2.setBounds(200,60,150,40);
		destination.setBounds(200,100,120,30);

		JLabel lbl3=new JLabel("Date of Journey  : ");
		lbl3.setFont(f);
		lbl3.setBounds(50,150,150,40);
		doj.setBounds(200,150,120,30);

		JLabel lbl4 = new JLabel("(DD/MM/YYYY)");
		lbl4.setBounds(210,180,100,30);

		next.setBounds(100,250,100,40);

		setLayout(null);
		add(inf);
		add(lbl1);
		add(lbl2);
		add(lbl3);
		add(lbl4);
		add(source);
		add(destination);
		add(doj);
		add(next);

		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String src = (String)source.getSelectedItem();
				String dest = (String)destination.getSelectedItem();
				String dt = doj.getText();
				setVisible(false);
				getDetails(src,dest,dt);
			}
		});
	}

	public void getDetails(String source,String destination,String doj) {
		os.println(3+","+source+","+destination+","+doj);
		os.flush();
		try{
			String status=is.readLine();
			if(!status.equals("fail")){
				String[] data=status.split(",");
				String timing=data[0];
				int fare=Integer.parseInt(data[1]);
				String selectedSeats=data[3];
				this.setVisible(false);
				new BillingForm(username,source,destination,timing,doj,fare,selectedSeats);
			}
			else{
				JOptionPane.showMessageDialog(null,"Bus not available on that data");				
			}
		}catch(IOException e){}
	} 


	class BillingForm extends JFrame {

		JLabel userName=null;
		JLabel boarding=null;
		JLabel destination=null;
		JLabel cost=null;
		JLabel timing=null;
		JButton proceed=null;
		JLabel inf = null;
		JLabel dofj=null;

		JComboBox seatNum=null;

		String iuser;
		String isource;
		String idest;
		String itime;
		int ifare;
		String bookedSeats;
		String[] availSeats;
		String idoj;
		String iseat;

		BillingForm(String user, String source, String dest,String time, String doj,int fare, String seats) {

			this.iuser = user;
			this.isource = source;
			this.idest=dest;
			this.itime=time;
			this.ifare=fare;
			this.bookedSeats=seats;	
			this.idoj=doj;		

			setTitle("Billing Form");
			setSize(400,600);
			setVisible(true);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	
			inf = new JLabel("Online Bus Reserveration System");
			inf.setFont(new Font("Cambria",Font.PLAIN,22));
			inf.setBounds(30,20,330,50);	

			userName = new JLabel(user);
			boarding = new JLabel(source);
			destination = new JLabel(dest);
			dofj=new JLabel(doj);
			timing = new JLabel(time);
			cost = new JLabel(fare+"");
			
			if(bookedSeats.equals("none")) {
				availSeats = new String[20];
				int k=1;
				for( int i = 0; i<20; i++,k++) {
					availSeats[i] = new String(k+"");
				}
			}
			else {
				String[] bookSeats = bookedSeats.split(":");
				int size = bookSeats.length;
				--size;
				size=20-size;
				availSeats = new String[size];
				int i=0;
				for(int k=1;k<=20;k++) {
					String val = new String(k+"");
					if(!check(val,bookSeats)) {
						availSeats[i++]=val;
					}
				}
			}

			seatNum = new JComboBox(availSeats);	

			proceed = new JButton("Proceed...");

			Font f=new Font("Calibri",Font.PLAIN,18);

			JLabel lbl1=new JLabel("Passenger Name : ");
			lbl1.setFont(f);
			lbl1.setBounds(50,70,150,40);
			userName.setFont(f);
			userName.setBounds(200,70,120,40);

			JLabel lbl2=new JLabel("Boarding Point : ");
			lbl2.setFont(f);
			lbl2.setBounds(50,120,150,40);
			boarding.setFont(f);
			boarding.setBounds(200,120,120,40);
			
			JLabel lbl3=new JLabel("Destination Point : ");
			lbl3.setFont(f);
			lbl3.setBounds(50,170,150,40);
			destination.setFont(f);
			destination.setBounds(200,170,120,40);

			JLabel lbl4=new JLabel("Journey Date : ");
			lbl4.setFont(f);
			lbl4.setBounds(50,220,150,40);
			dofj.setFont(f);
			dofj.setBounds(200,220,120,40);

			JLabel lbl5=new JLabel("Journey Time : ");
			lbl5.setFont(f);
			lbl5.setBounds(50,270,150,40);
			timing.setFont(f);
			timing.setBounds(200,270,120,40);

			JLabel lbl6=new JLabel("Fare : ");
			lbl6.setFont(f);
			lbl6.setBounds(50,320,150,40);
			cost.setFont(f);
			cost.setBounds(200,320,120,40);

			JLabel lbl7=new JLabel("Seat Number : ");
			lbl7.setFont(f);
			lbl7.setBounds(50,370,150,40);
			seatNum.setFont(f);
			seatNum.setBounds(200,380,50,30);

			proceed.setBounds(150,460,100,40);	

			setLayout(null);
			add(inf);
			add(lbl1);
			add(lbl2);
			add(lbl3);
			add(lbl4);
			add(lbl5);
			add(lbl6);
			add(lbl7);
			add(userName);
			add(boarding);
			add(destination);
			add(dofj);
			add(timing);
			add(cost);
			add(seatNum);
			add(proceed);

			proceed.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					iseat =seatNum.getSelectedItem().toString();
					reserveTicket(iuser,isource,idest,idoj,iseat,"Reserved");
				}
			});
		}
		
		public boolean check(String num, String[] nums){
			for(String val:nums){
				if(val.equals(num)){
					return true;
				}
			}
			return false;
		}

		public void reserveTicket(String username, String source, String dest,String doj, String seat, String status) {
			os.println(4+","+username+","+source+","+dest+","+doj+","+seat+","+status);
			os.flush();
			try{
				String data=is.readLine();
				if(data.equals("success")) {
					JOptionPane.showMessageDialog(null,"Ticket Reserved.....");
					this.setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,"Sorry! Please Check...");
				}
			}catch(IOException e){ }
		}
	}
}