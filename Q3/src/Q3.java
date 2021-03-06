import java.sql.*;
import java.util.*;

public class Q3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PreparedStatement ps;
		Statement st;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/see","root","");
			con.setAutoCommit(false);
//			if(!con.isClosed()) {
//				System.out.println("Connected to DB");
//			} else {
//				System.out.println("Couldn't connect to DB");
//			}
			while(true) {
				System.out.println("Enter 1.Create Acc, 2.Display Acc details, 3.Transact Else.Exit");
				int opt  = sc.nextInt();
				int f = 0;
				switch(opt) {
					case 1:
						System.out.println("Enter id: ");
						int id = sc.nextInt();
						System.out.println("Enter Name: ");
						String name = sc.next();
						System.out.println("Enter balance: ");
						int balance = sc.nextInt();
						String query = "INSERT INTO `bank` VALUES (?,?,?)";
						ps = con.prepareStatement(query);
						ps.setInt(1, id);
						ps.setString(2, name);
						ps.setInt(3, balance);
						ps.execute();
						con.commit();
						
						break;
					case 2:
						System.out.println("ID, Name, Balance ");
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM `bank`");
						while(rs.next()) {
							System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
						}
						break;
					case 3:
						System.out.println("Transact, please enter your id");
						id = sc.nextInt();
						System.out.println("Enter 1.withdraw 2.deposit 3.displayDetails else.main menu");
						int temp = sc.nextInt();
						switch(temp) {
							case 1:
								Savepoint sp1 = con.setSavepoint();
								System.out.println("Enter amount to withdraw: ");
								int amt = sc.nextInt();
								query = "select balance from bank where id = " + id;
								st = con.createStatement();
								rs = st.executeQuery(query);
								int bal = 0;
								while(rs.next()) {
									bal = rs.getInt(1);
								}
								bal -= amt;
								System.out.println(bal);
								query = "update bank set balance = " + bal + " where id = " + id;
								st.executeUpdate(query);
								if(bal < 0) {
									con.rollback(sp1);
								}
								con.releaseSavepoint(sp1);
								con.commit();
								System.out.println("Withdraw successful");
								
								 break;
							case 2:
								System.out.println("Enter amount to deposit: ");
								amt = sc.nextInt();
								query = "select balance from bank where id = " + id;
								st = con.createStatement();
								rs = st.executeQuery(query);
								bal = 0;
								while(rs.next()) {
									bal = rs.getInt(1);
								}
								bal += amt;
								System.out.println(bal);
								query = "update bank set balance = " + bal + " where id = " + id;
								st.executeUpdate(query);
								System.out.println("Deposit successful");							
								con.commit();
								 break;
							case 3:
								query = "select * from bank where id = " + id;
								st = con.createStatement();
								rs = st.executeQuery(query);
								while(rs.next()) {
									System.out.println("ID = " + rs.getInt(1) + ", Name = " + rs.getString(2) + ", Balance = " + rs.getInt(3));
								}
								 break;
							default:
								 break;
								 
						}
						
						
						break;
					default:
						f = 1;
						break;
					
				}	
				if(f == 1) {
					break;
				}
			}	
			sc.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
