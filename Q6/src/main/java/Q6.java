

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Q6
 */
@WebServlet("/Q6")
public class Q6 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Q6() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		int emp_id = Integer.parseInt(request.getParameter("emp_id"));
		String emp_name = request.getParameter("emp_name");
		String address = request.getParameter("address");
		Date dob = Date.valueOf(request.getParameter("dob"));
		response.setContentType("text/html");
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/see","root","");
			String query1 = "INSERT INTO `Employee` VALUES (?, ?, ?, ?) ";
			PreparedStatement ps = con.prepareStatement(query1);
			ps.setInt(1, emp_id);
			ps.setString(2, emp_name);
			ps.setString(3, address);
			ps.setDate(4, dob);
			ps.execute();
		
			String query2 = "SELECT * FROM Employee";
			
			ResultSet rs = ps.executeQuery(query2);
			PrintWriter out = response.getWriter();
			String start = "<table><tr><th>Emp_ID</th><th>Emp_name</th><th>Address</th><th>DOB</th></tr>";
			out.print(start);
			while(rs.next()) {
				emp_id = rs.getInt(1);
				emp_name = rs.getString(2);
				address = rs.getString(3);
				dob = rs.getDate(4);
				String fin = "<tr><td>"+ emp_id +"</td><td>"+ emp_name + "</td><td>" + address + "</td><td>" + String.valueOf(dob) + "</td></tr>";
				out.println(fin);
			}
			out.print("</table>");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
