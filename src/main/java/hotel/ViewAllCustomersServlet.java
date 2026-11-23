package hotel;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/ViewAllCustomersServlet")
public class ViewAllCustomersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Map<String, String>> customers = new ArrayList<>();

        // Use try-with-resources to automatically close the connection
        try (Connection conn = ConnectionManager.getConnection()) {
            // JOIN query to get data from both tables
            String sql = "SELECT u.userid, c.Name, u.email, c.CustomerPhoneNum " +
                         "FROM users u " +
                         "JOIN customer c ON u.userid = c.userid";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("userId", rs.getString("userid"));
                row.put("name", rs.getString("Name"));
                row.put("email", rs.getString("email"));
                row.put("phone", rs.getString("CustomerPhoneNum"));
                customers.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the list as an attribute for the JSP to read
        request.setAttribute("customerList", customers);
        // Forward to your JSP file
        request.getRequestDispatcher("deletecustomerdata.jsp").forward(request, response);
    }
}