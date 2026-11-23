package hotel;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/ViewReportServlet")
public class ViewReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        double totalRevenue = 0.0;
        int totalTransactions = 0;
        List<Map<String, Object>> roomTypeRevenue = new ArrayList<>();
        List<Map<String, Object>> topCustomers = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            
            // 1. Get Overall Revenue from payment table
            String sqlRevenue = "SELECT SUM(Amount) as total, COUNT(PaymentID) as count FROM payment";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlRevenue);
                 ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next()) {
                    totalRevenue = rs1.getDouble("total");
                    totalTransactions = rs1.getInt("count");
                }
            }

            // 2. Get Revenue by Room Type
            String sqlRoomData = "SELECT rt.RoomTypeName, SUM(p.Amount) as revenue " +
                    "FROM room_type rt " +
                    "JOIN booking b ON rt.RoomTypeID = b.RoomTypeID " +
                    "JOIN payment p ON b.BookingID = p.BookingID " +
                    "GROUP BY rt.RoomTypeName";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlRoomData);
                 ResultSet rs2 = ps2.executeQuery()) {
                while (rs2.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("roomTypeName", rs2.getString("RoomTypeName"));
                    row.put("revenue", rs2.getDouble("revenue"));
                    roomTypeRevenue.add(row);
                }
            }

            // 3. Get Top Regular Customers
            String sqlTopCust = "SELECT c.Name, COUNT(b.BookingID) as stays " +
                    "FROM customer c " +
                    "JOIN booking b ON c.CustomerID = b.CustomerID " +
                    "GROUP BY c.Name ORDER BY stays DESC LIMIT 5";
            try (PreparedStatement ps3 = conn.prepareStatement(sqlTopCust);
                 ResultSet rs3 = ps3.executeQuery()) {
                while (rs3.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("name", rs3.getString("Name"));
                    row.put("bookingCount", rs3.getInt("stays"));
                    topCustomers.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set attributes to match the viewBookingReport.jsp tags
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalCompletedBookings", totalTransactions);
        request.setAttribute("roomTypeRevenue", roomTypeRevenue);
        request.setAttribute("topCustomers", topCustomers);
        
        request.getRequestDispatcher("viewBookingReport.jsp").forward(request, response);
    }
}