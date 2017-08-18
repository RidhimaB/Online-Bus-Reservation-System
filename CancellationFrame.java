				/*    Cancellation Form */
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

class CancellationFrame extends JFrame{
	
	JComboBox source = null;
	JComboBox destination = null;
	JButton cancel = null;
	JTextField doj=null;
	JLabel inf = null;
	String username=null;

	PrintWriter os=null;
	BufferedReader is=null;

	
	public CancellationFrame(BufferedReader is, PrintWriter os, String user) {
		this.os=os;
		this.is=is;
		this.username = user;


		setTitle("Cancellation Form");
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		String[] src = {"montgomery","troy","dothan"};
		String[] dest = {"montgomery","troy","dothan"};
	
		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,30,330,50);	
	
		source = new JComboBox(src);
		destination = new JComboBox(dest);
		doj =new JTextField();
		cancel = new JButton("Cancel");

		Font f=new Font("Calibri",Font.PLAIN,18);

		JLabel lbl1=new JLabel("Boarding Point  ");
		lbl1.setFont(f);
		lbl1.setBounds(50,100,150,40);
		source.setBounds(200,100,100,30);

		JLabel lbl2=new JLabel("Destination Point  ");
		lbl2.setFont(f);
		lbl2.setBounds(50,150,150,40);
		destination.setBounds(200,150,100,30);

		JLabel lbl3=new JLabel("Journey Date  ");
		lbl3.setFont(f);
		lbl3.setBounds(50,200,150,40);
		doj.setBounds(200,200,100,30);

		JLabel lbl4 = new JLabel("(DD/MM/YYYY)");
		lbl4.setBounds(210,240,100,30);

		cancel.setBounds(150,300,100,40);


		setLayout(null);
		add(inf);
		add(lbl1);
		add(lbl2);
		add(lbl3);
		add(lbl4);
		add(source);
		add(destination);
		add(doj);
		add(cancel);

		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String src = (String)source.getSelectedItem();
				String dest = (String)destination.getSelectedItem();
				String dt = doj.getText();
				if(dt==null){
					JOptionPane.showMessageDialog(null,"Please check the input...");
				}
				else{
			   		cancelTicket(username,src,dest,dt,"Cancelled");
				}
			}
		});
	}

	public void cancelTicket(String username,String source,String destination,String doj, String status) {
		os.println(5+","+username+","+source+","+destination+","+doj+","+status);
		os.flush();
		try{
			String result=is.readLine();
			if(result.equals("success")){
				JOptionPane.showMessageDialog(null,"Ticket Cancelled....");
				this.setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(null,"Ticket is not reserved....");				
			}
		}catch(IOException e){}
	} 

}
	