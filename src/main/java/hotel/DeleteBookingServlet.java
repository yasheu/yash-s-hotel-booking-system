package hotel;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/DeleteBookingServlet")
public class DeleteBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String bookingId = request.getParameter("id");

        if (bookingId != null) {
            try (Connection conn = ConnectionManager.getConnection()) {
                String sql = "DELETE FROM booking WHERE BookingID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(bookingId));
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Redirect back to the view servlet so the list refreshes automatically
        response.sendRedirect("ViewMyBookingsServlet");
    }
}