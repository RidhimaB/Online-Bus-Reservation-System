			/*     Details Form */
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


class DetailsFrame extends JFrame {
	JComboBox source = null;
	JComboBox destination = null;
	JButton display = null;
	JTextField doj=null;
	JLabel inf = null;
	JTable table = null;

	JLabel timing = null;
	JLabel cost = null;
	JLabel seats = null;
	
	PrintWriter os=null;
	BufferedReader is=null;
		
	DetailsFrame  (BufferedReader is, PrintWriter os) {
		this.os=os;
		this.is=is;
	
		setTitle("Enquiry Frame");
		setSize(600,500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		String[] src = {"montgomery","troy","dothan"};
		String[] dest = {"montgomery","troy","dothan"};

		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(100,15,330,50);	
	
		source = new JComboBox(src);
		destination = new JComboBox(dest);
		doj = new JTextField();
		display = new JButton("Display");


		Font f=new Font("Calibri",Font.PLAIN,18);
	
		JLabel lbl1=new JLabel("Boarding Point  ");
		lbl1.setFont(f);
		lbl1.setBounds(80,60,150,40);
		source.setBounds(80,100,110,30);

		JLabel lbl2=new JLabel("Destination Point  ");
		lbl2.setFont(f);
		lbl2.setBounds(280,60,150,40);
		destination.setBounds(280,100,120,30);

		JLabel lbl3=new JLabel("Date of Journey  : ");
		lbl3.setFont(f);
		lbl3.setBounds(80,150,150,40);
		doj.setBounds(280,150,120,30);

		JLabel lbl4 = new JLabel("(DD/MM/YYYY)");
		lbl4.setBounds(290,180,100,30);

		display.setBounds(180,250,100,40);

		JLabel lbl5 = new JLabel("Timing");
		lbl5.setFont(f);
		lbl5.setBounds(80,300,150,40);
		timing=new JLabel();
		timing.setFont(f);
		timing.setBounds(80,340,150,40);

		JLabel lbl6 = new JLabel("Cost");
		lbl6.setFont(f);
		lbl6.setBounds(180,300,150,40);
		cost=new JLabel();
		cost.setFont(f);
		cost.setBounds(180,340,150,40);

		JLabel lbl7 = new JLabel("Available Seats");
		lbl7.setFont(f);
		lbl7.setBounds(280,300,150,40);
		seats=new JLabel();
		seats.setFont(f);
		seats.setBounds(280,340,150,40);

		setLayout(null);
		add(inf);
		add(lbl1);
		add(lbl2);
		add(lbl3);
		add(lbl4);
		add(lbl5);
		add(lbl6);
		add(lbl7);
		add(source);
		add(destination);
		add(doj);
		add(display);
		add(timing);
		add(cost);
		add(seats);
	
		display.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String src = (String)source.getSelectedItem();
				String dest = (String)destination.getSelectedItem();
				String dt = doj.getText();
				showDetails(src,dest,dt);
			}
		});
	}

	public void showDetails(String source,String destination,String doj){
		os.println(3+","+source+","+destination+","+doj);
		os.flush();
		try{
			String res=is.readLine();
			if(!res.equals("fail")){
				String[] data=res.split(",");
				timing.setText(data[0]);
				cost.setText(data[1]);
				seats.setText(data[2]);
			}
			else{
				JOptionPane.showMessageDialog(null,"Sorry! Please Check Again...");
			}
		}catch(IOException e){}
	}
}