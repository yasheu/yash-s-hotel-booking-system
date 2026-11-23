package hotel;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/ViewAllBookingsServlet")
public class ViewAllBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Use the Booking object list instead of a Map to match ViewMyBookings style
        List<Booking> allBookings = new ArrayList<>();

        // SQL updated to match your ER diagram: Table 'room_type' and column 'RoomNumber'
        String sql = "SELECT b.BookingID, b.BookingDate, b.BookingStatus, b.NumOfGuest, " +
                     "b.CheckInDate, b.CheckOutDate, c.Name AS CustomerName, " +
                     "c.CustomerPhoneNum, h.Location, rt.RoomTypeName, rt.RoomNumber " +
                     "FROM booking b " +
                     "JOIN customer c ON b.CustomerID = c.CustomerID " +
                     "JOIN room_type rt ON b.RoomTypeID = rt.RoomTypeID " + 
                     "JOIN hotel h ON rt.HotelID = h.HotelID";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking b = new Booking();
                // Populating the Booking object using the same setters as ViewMyBookings
                b.setBookingID(rs.getInt("BookingID"));
                b.setBookingDate(rs.getDate("BookingDate"));
                b.setBookingStatus(rs.getString("BookingStatus"));
                b.setNumOfGuest(rs.getInt("NumOfGuest"));
                b.setCheckInDate(rs.getDate("CheckInDate"));
                b.setCheckOutDate(rs.getDate("CheckOutDate"));
                b.setCustomerName(rs.getString("CustomerName"));
                b.setCustomerPhone(rs.getString("CustomerPhoneNum"));
                b.setRoomTypeName(rs.getString("RoomTypeName"));
                b.setLocation(rs.getString("Location"));
                b.setRoomNumber(rs.getInt("RoomNumber"));
                
                allBookings.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setting the attribute to be used in the JSP
        request.setAttribute("allBookings", allBookings);
        request.getRequestDispatcher("viewallbooking.jsp").forward(request, response);
    }
}