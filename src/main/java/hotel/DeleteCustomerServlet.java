package hotel;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // This gets the ID from the <input type="hidden" name="userId"> in your JSP
        String userIdStr = request.getParameter("userId");

        if (userIdStr != null) {
            int targetUserId = Integer.parseInt(userIdStr);

            try (Connection conn = ConnectionManager.getConnection()) {
                conn.setAutoCommit(false); // Start transaction for safety

                try {
                    // Step 1: Delete Bookings
                    String sql1 = "DELETE b FROM booking b JOIN customer c ON b.CustomerID = c.CustomerID WHERE c.userid = ?";
                    try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                        ps1.setInt(1, targetUserId);
                        ps1.executeUpdate();
                    }

                    // Step 2: Delete Customer profile
                    String sql2 = "DELETE * FROM customer WHERE userid = ?";
                    try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                        ps2.setInt(1, targetUserId);
                        ps2.executeUpdate();
                    }

                    // Step 3: Delete User account
                    String sql3 = "DELETE * FROM users WHERE userid = ?";
                    try (PreparedStatement ps3 = conn.prepareStatement(sql3)) {
                        ps3.setInt(1, targetUserId);
                        ps3.executeUpdate();
                    }

                    conn.commit(); // Save all changes
                    
                    // IMPORTANT: Redirect back to the LIST SERVLET to refresh the table
                    response.sendRedirect("ViewAllCustomersServlet");

                } catch (SQLException e) {
                    conn.rollback(); // Undo if any step fails
                    e.printStackTrace();
                    response.sendRedirect("ViewAllCustomersServlet?error=database");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}