package hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import login.ConnectionManager;

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId"); 

        
        Booking booking = new Booking();
        
        try {
            booking.setBookingDate(new java.sql.Date(System.currentTimeMillis()));
            booking.setBookingStatus("Confirmed");
            booking.setNumOfGuest(Integer.parseInt(request.getParameter("numGuests")));
            booking.setRoomTypeID(Integer.parseInt(request.getParameter("roomNumber")));
            booking.setCheckInDate(Date.valueOf(request.getParameter("checkin")));
            booking.setCheckOutDate(Date.valueOf(request.getParameter("checkout")));
        } catch (Exception e) {
            response.sendError(400, "Invalid form data: " + e.getMessage());
            return;
        }

        String guestName = request.getParameter("customerName");
        String guestPhone = request.getParameter("customerPhone");

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false); 

            // 2. GET OR CREATE CUSTOMER ID 
            int customerId = -1;
            if (userIdObj != null) {
                int userId = (int) userIdObj;
                String checkCustSql = "SELECT CustomerID FROM customer WHERE userid = ?";
                PreparedStatement psCust = conn.prepareStatement(checkCustSql);
                psCust.setInt(1, userId);
                ResultSet rsCust = psCust.executeQuery();
                if (rsCust.next()) {
                    customerId = rsCust.getInt("CustomerID");
                }
            } else {
                if (guestPhone != null && !guestPhone.startsWith("0")) {
                    guestPhone = "0" + guestPhone;
                }

                String insertGuestSql = "INSERT INTO customer (Name, CustomerPhoneNum, userid) VALUES (?, ?, NULL)";
                PreparedStatement psGuest = conn.prepareStatement(insertGuestSql, Statement.RETURN_GENERATED_KEYS);
                psGuest.setString(1, guestName);
                psGuest.setString(2, guestPhone); 
                psGuest.executeUpdate();
                
                ResultSet gk = psGuest.getGeneratedKeys();
                if (gk.next()) {
                    customerId = gk.getInt(1);
                }
            }
            
           
            booking.setCustomerID(customerId);
            
           
            String sqlBooking = "INSERT INTO booking (BookingDate, BookingStatus, NumOfGuest, CustomerID, RoomTypeID, CheckInDate, CheckOutDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psBook = conn.prepareStatement(sqlBooking);
            
            
            psBook.setDate(1, booking.getBookingDate());
            psBook.setString(2, booking.getBookingStatus());
            psBook.setInt(3, booking.getNumOfGuest());
            psBook.setInt(4, booking.getCustomerID());
            psBook.setInt(5, booking.getRoomTypeID());
            psBook.setDate(6, booking.getCheckInDate());
            psBook.setDate(7, booking.getCheckOutDate());
            
            psBook.executeUpdate();
            conn.commit();

            
            session.setAttribute("customerID", customerId);
            response.sendRedirect("ViewMyBookingsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Database Error: " + e.getMessage());
        }
    }
}