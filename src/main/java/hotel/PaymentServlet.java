package hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import login.ConnectionManager; // Importing your existing connection manager

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
  


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String bookingIDStr = request.getParameter("bookingID");
        
        if (bookingIDStr == null || bookingIDStr.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int bookingID = Integer.parseInt(bookingIDStr);
        Connection con = null;

        try {
            // 2. Use your ConnectionManager
            con = ConnectionManager.getConnection();

            
            String sql =
            	    "SELECT c.Name, b.CheckInDate, b.CheckOutDate, r.RoomPrice " +
            	    "FROM booking b " +
            	    "LEFT JOIN customer c ON b.CustomerID = c.CustomerID " +
            	    "LEFT JOIN room_type r ON b.RoomTypeID = r.RoomTypeID " +
            	    "WHERE b.BookingID = ?";

            
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bookingID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
            	
            	// ... inside the rs.next() block, use the exact column names from the SELECT:
                String customerName = rs.getString("Name");
                Date checkIn = rs.getDate("CheckInDate");
                Date checkOut = rs.getDate("CheckOutDate");
                double pricePerNight = rs.getDouble("RoomPrice");

                // 4. Calculate total days (Difference between dates)
                long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diffInDays == 0) diffInDays = 1; // Minimum 1 night stay

                double totalAmount = (diffInDays * pricePerNight) * 1.02;

                // 5. Create the Payment bean and set values
                Payment paymentView = new Payment(bookingID, totalAmount, checkOut, customerName, bookingID);
                paymentView.setBookingID(bookingID);
                paymentView.setCustomerName(customerName);
                paymentView.setCheckInDate(checkIn);
                paymentView.setCheckOutDate(checkOut);
                paymentView.setTotalStay((int)diffInDays);
                paymentView.setAmount(totalAmount);

                // 6. Pass the object to payment.jsp
                request.setAttribute("payment", paymentView);
                request.getRequestDispatcher("payment.jsp").forward(request, response);
                
            } else {
                // If booking ID doesn't exist
                response.sendRedirect("error.jsp?msg=InvalidBooking");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            // Good practice: close the connection if it's not handled by the manager
            try { if(con != null) con.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
    }
}