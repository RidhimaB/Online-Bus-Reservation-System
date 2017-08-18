import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.*;

public class NetworkServer {
     public static void main(String args[]){

    	Socket s=null;
    	ServerSocket ss2=null;
	System.out.println("Server Listening......");
	try{
		ss2 = new ServerSocket(4445);	 // can also use static final PORT_NUM , when defined
	} catch(IOException e){
		e.printStackTrace();
		System.out.println("Server error");
	}

	while(true){
		try{
			s= ss2.accept();
			System.out.println("connection Established");
			ServerThread st=new ServerThread(s);
			st.start();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Connection Error");
		}
	}
     }
}

class ServerThread extends Thread {  

	String line=null;
	BufferedReader  is = null;
	PrintWriter os=null;
	Socket s=null;
	int choice;
	String data[];

	public ServerThread(Socket s){
		this.s=s;
	}

	public void run() {
		try{
			is= new BufferedReader(new InputStreamReader(s.getInputStream()));
			os=new PrintWriter(s.getOutputStream());
		}catch(IOException e){
			System.out.println("IO error in server thread");
		}    
		try {
		           do{
			line=is.readLine();
			System.out.println(line);
			data=line.split(",");
			choice=Integer.parseInt(data[0]);
			switch(choice) {
				case 1:
				     try{
					registerMe(data[1],data[2],data[3],data[4]);
					System.out.println("Registered Successfully...");
					os.println("User Registered Successfully.....");
					os.flush();
				    }catch(Exception e){ System.out.println("Reason : "+e.getMessage()); } 
				break;
				case 2:
				     try{
					String result=loginMe(data[1],data[2]);
					os.println(result);
					os.flush();
				    }catch(Exception e){ System.out.println("Reason : "+e.getMessage()); } 
				break;
				case 3:
				     try{
					String result=showDetails(data[1],data[2],data[3]);
					System.out.println(result);
					os.println(result);
					os.flush();
				    }catch(Exception e){ System.out.println("Reason : "+e.getMessage()); } 
				break;
				case 4:
				     try{
					String result=insertDetails(data[1],data[2],data[3],data[4],data[5],data[6]);
					os.println(result);
					os.flush();
				    }catch(Exception e){ System.out.println("Reason : "+e.getMessage()); } 
				break;
				case 5:
				     try{
					String result=cancelTicket(data[1],data[2],data[3],data[4],data[5]);
					os.println(result);
					os.flush();
				    }catch(Exception e){ System.out.println("Reason : "+e.getMessage()); } 
				break;
				case 6:
					os.println("Have a Happy Journey....");
					os.flush();
				break;
			}
		           }while(choice!=6);
		} catch (IOException e) {
			line=this.getName(); //reused String line for getting thread name
			System.out.println("IO Error/ Client "+line+" terminated abruptly");
		}catch(NullPointerException e){
			line=this.getName(); //reused String line for getting thread name
			System.out.println("Client "+line+" Closed");
		}finally{    
			try{
				System.out.println("Connection Closing..");
				if (is!=null){
					is.close(); 
					System.out.println(" Socket Input Stream Closed");
				}
				if(os!=null){
					os.close();
					System.out.println("Socket Out Closed");
				}
				if (s!=null){
					s.close();
					System.out.println("Socket Closed");
				}
			} catch(IOException ie){
				System.out.println("Socket Close Error");
			}
		}//end finally
	}

	public  void registerMe(String username, String pass, String mobile, String email){

		Connection con=null;
		PreparedStatement st=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","scott","tiger");
			st = con.prepareStatement("Insert into registration values(?,?,?,?)");
			st.setString(1,username);
			st.setString(2,pass);
			st.setString(3,mobile);
			st.setString(4,email);
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}finally{
			try{
				st.close();
				con.close();
			}catch(SQLException se){}
		}	
	}

		/*  Login Logic */

	public  String loginMe(String username, String pass){

		Connection con=null;
		PreparedStatement st=null;
		ResultSet res=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","scott","tiger");
			st = con.prepareStatement("Select * from registration where username=? and password=?");
			st.setString(1,username);
			st.setString(2,pass);
			res=st.executeQuery();
			if(res.next()){
				return "success";	
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}finally{
			try{
				st.close();
				con.close();
			}catch(SQLException se){}
		}
		return "fail";	
	}

			/* Details Logic */
	public  String showDetails(String source, String destination, String doj){

		Connection con=null;
		PreparedStatement st=null;
		ResultSet res=null;
		ResultSet res2=null;
		ResultSet res3=null;
		java.util.Date today=null;
		int fare=0;
		String timing=null;
		int seats=20;
		String selectedSeats=null;
		try{ 
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
			today=format.parse(doj);
		}catch(Exception e){ }

		java.sql.Date sqlDate=new java.sql.Date(today.getTime());

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","scott","tiger");
			st = con.prepareStatement("Select * from busdetails where source=? and destination=?");
			st.setString(1,source);
			st.setString(2,destination);
			res=st.executeQuery();
			if(res.next()){
				fare=res.getInt(3);
				timing=res.getString(4);
				st=con.prepareStatement("Select * from availabilitydetails where doj=? and source=? and destination=?");
				st.setDate(1,sqlDate);
				st.setString(2,source);
				st.setString(3,destination);
				res2=st.executeQuery();
				if(res2.next()){	
					seats=res2.getInt(4);
					st=con.prepareStatement("Select seatnum from reservationdetails where source=? and destination=? and doj=? and status=?");	
					st.setString(1,source);
					st.setString(2,destination);
					st.setDate(3,sqlDate);
					st.setString(4,"Reserved");
					res3=st.executeQuery();
					while(res3.next()){
						if(selectedSeats==null)
							selectedSeats=res3.getString(1)+":";
						else
							selectedSeats+=res3.getString(1)+":";
					}
					selectedSeats+="0";
					return timing+","+fare+","+seats+","+selectedSeats;	
				}
				else{
					selectedSeats="none";
					return timing+","+fare+","+seats+","+selectedSeats;
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}finally{
			try{
				st.close();
				con.close();
			}catch(SQLException se){}
		}
		return "fail";	
	}

				/* Reserve Ticket */
	public  String insertDetails(String username, String source, String destination, String doj, String seatNum, String status){

		Connection con=null;
		PreparedStatement st=null;
		ResultSet res=null;
		java.util.Date today=null;
		try{ 
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
			today=format.parse(doj);
		}catch(Exception e){ }

		java.sql.Date sqlDate=new java.sql.Date(today.getTime());

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","scott","tiger");
			st = con.prepareStatement("Select * from availabilitydetails where source=? and destination=? and doj=?");
			st.setString(1,source);
			st.setString(2,destination);
			st.setDate(3,sqlDate);
			res=st.executeQuery();
			if(!res.next()){	
				st=con.prepareStatement("insert into availabilitydetails values(?,?,?,?)");
				st.setDate(1,sqlDate);
				st.setString(2,source);
				st.setString(3,destination);
				st.setInt(4,19);
				st.executeUpdate();
			}
			else{
				int availSeats = res.getInt(4);
				--availSeats;
				st=con.prepareStatement("update availabilitydetails set seats=? where doj=? and source=? and destination=? ");
				st.setInt(1,availSeats);
				st.setDate(2,sqlDate);
				st.setString(3,source);
				st.setString(4,destination);
				st.executeUpdate();	
			}

			st = con.prepareStatement("Delete from reservationdetails where username=? and source=? and destination=? and doj=? and status=?");
			st.setString(1,username);
			st.setDate(4,sqlDate);
			st.setString(2,source);
			st.setString(3,destination);
			st.setString(5,"Cancelled");
			st.executeUpdate();			

			st=con.prepareStatement("insert into reservationdetails values(?,?,?,?,?,?)");
			st.setString(1,username);
			st.setString(2,source);
			st.setString(3,destination);
			st.setDate(4,sqlDate);
			st.setString(5,seatNum);
			st.setString(6,status);
			st.executeUpdate();

			return "success";
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}finally{
			try{
				res.close();
				st.close();
				con.close();
			}catch(SQLException se){}
		}
		return "fail";	
	}

				/* Cancellation of  Ticket */
	public  String cancelTicket(String username, String source, String destination, String doj, String status){

		Connection con=null;
		PreparedStatement st=null;
		ResultSet res=null;
		ResultSet res2=null;
		java.util.Date today=null;
		try{ 
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
			today=format.parse(doj);
		}catch(Exception e){ }

		java.sql.Date sqlDate=new java.sql.Date(today.getTime());

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","scott","tiger");
			st = con.prepareStatement("Select * from reservationdetails where username=? and doj=? and status=?");
			st.setString(1,username);
			st.setDate(2,sqlDate);
			st.setString(3,"Reserved");
			res=st.executeQuery();

			if(res.next()){	
				st=con.prepareStatement("update reservationdetails set status=? where username=? and doj=?");
				st.setString(1,status);
				st.setString(2,username);
				st.setDate(3,sqlDate); 
				
				st.executeUpdate();
		
				st = con.prepareStatement("Select * from availabilitydetails where source=? and destination=? and doj=?");
				st.setString(1,source);
				st.setString(2,destination);
				st.setDate(3,sqlDate);
				res2=st.executeQuery();

				if(res2.next()) {
					int availSeats = res2.getInt(4);
					++availSeats;
					st=con.prepareStatement("update availabilitydetails set seats=? where doj=? and source=? and destination=? ");
					st.setInt(1,availSeats);
					st.setDate(2,sqlDate);
					st.setString(3,source);
					st.setString(4,destination);
					st.executeUpdate();
				}	
			}
			return "success";
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}finally{
			try{
				res.close();
				st.close();
				con.close();
			}catch(SQLException se){}
		}
		return "fail";	
	}
}