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


public class NetworkClient {
	
	InetAddress address=null;
	Socket s1=null;
	String line=null;
	BufferedReader is=null;
	PrintWriter os=null;
	String data;
	JFrame mainWindow=null;
	JLabel inf=null;
	JButton connect = null;
	JButton disconnect = null;
	String status=null;

	NetworkClient() {

		mainWindow = new JFrame("Welcome");
		mainWindow.setSize(400,280);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow. setLocationRelativeTo(null);

		inf = new JLabel("Online Bus Reserveration System");
		inf.setFont(new Font("Cambria",Font.PLAIN,22));
		inf.setBounds(30,30,330,50);

		connect = new JButton("Connect...");
		connect.setBounds(50,100,120,50);

		disconnect = new JButton("Disconnect...");
		disconnect.setBounds(200,100,120,50);
		disconnect.setEnabled(false);
		
		mainWindow.setLayout(null);		
		mainWindow.add(inf);
		mainWindow.add(connect);
		mainWindow.add(disconnect);

		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status=startConnection();
				if(status.equals("success")) {
					disconnect.setEnabled(true);
					connect.setEnabled(false);
					new MenuFrame(is,os);
				}
				else {
					JOptionPane.showMessageDialog(null,"Run the Server First....");
				}
			}
		});

		disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeConnection();
				disconnect.setEnabled(false);
				connect.setEnabled(true);
			}
		});
	}

	public String startConnection() {
	    String status = "Connection failed...";
	    try {
		address=InetAddress.getLocalHost();
		s1=new Socket(address, 4445); 	// You can use static final constant PORT_NUM
		is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
		os= new PrintWriter(s1.getOutputStream());
		status = "success";
		return status;
	    }catch (IOException e){
		e.printStackTrace();
		System.err.print("IO Exception");
	    }
	    return status;  
	}
	
	public void closeConnection(){
		try{
			is.close();os.close();s1.close();
		}catch(IOException e){ }
	}

     public static void main(String args[]) throws IOException{
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			new NetworkClient();
		} 
	});
     }

}


