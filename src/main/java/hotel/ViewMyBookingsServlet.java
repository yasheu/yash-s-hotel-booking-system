package hotel;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import login.ConnectionManager;

@WebServlet("/ViewMyBookingsServlet")
public class ViewMyBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");
        Object customerIdObj = session.getAttribute("customerID"); 

        List<Booking> myBookings = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            String baseQuery = "SELECT b.BookingID, b.BookingDate, b.BookingStatus, b.NumOfGuest, " +
                               "b.CustomerID, b.RoomTypeID, b.CheckInDate, b.CheckOutDate, " +
                               "c.Name AS CustomerName, h.Location, rt.RoomTypeName, rt.RoomNumber " +
                               "FROM booking b " +
                               "JOIN customer c ON b.CustomerID = c.CustomerID " +
                               "JOIN room_type rt ON b.RoomTypeID = rt.RoomTypeID " +
                               "JOIN hotel h ON rt.HotelID = h.HotelID ";

            PreparedStatement ps = null;

            if (userIdObj != null) {
                ps = conn.prepareStatement(baseQuery + " WHERE c.userid = ?");
                ps.setInt(1, Integer.parseInt(userIdObj.toString()));
            } else if (customerIdObj != null) {
                ps = conn.prepareStatement(baseQuery + " WHERE b.CustomerID = ?");
                ps.setInt(1, Integer.parseInt(customerIdObj.toString()));
            }

            if (ps != null) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingID(rs.getInt("BookingID"));
                    b.setBookingDate(rs.getDate("BookingDate"));
                    b.setBookingStatus(rs.getString("BookingStatus"));
                    b.setNumOfGuest(rs.getInt("NumOfGuest"));
                    b.setCheckInDate(rs.getDate("CheckInDate"));
                    b.setCheckOutDate(rs.getDate("CheckOutDate"));
                    b.setCustomerName(rs.getString("CustomerName"));
                    b.setRoomTypeName(rs.getString("RoomTypeName"));
                    b.setLocation(rs.getString("Location"));
                    b.setRoomNumber(rs.getInt("RoomNumber"));
                    myBookings.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("myBookings", myBookings);
        request.getRequestDispatcher("BookingInfo.jsp").forward(request, response);
    }
}