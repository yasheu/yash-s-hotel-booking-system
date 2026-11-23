package hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.ConnectionManager;

@WebServlet("/DeleteAllBookingsServlet")
public class DeleteAllBookingsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        String bookingIdStr = request.getParameter("id");

        if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
            String sql = "DELETE FROM booking WHERE BookingID = ?";

            try (Connection conn = ConnectionManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                
                ps.setString(1, bookingIdStr);

                
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        
        response.sendRedirect("ViewAllBookingsServlet");
    }
}
