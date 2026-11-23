package hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import login.ConnectionManager;

@WebServlet("/ProcessPaymentServlet")
public class ProcessPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingIDStr = request.getParameter("bookingID");
        String paymentMethod = request.getParameter("paymentType"); 
 
        if (bookingIDStr == null || bookingIDStr.isEmpty()) {
            request.setAttribute("errorMessage", "Booking ID is missing.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        int bookingID = Integer.parseInt(bookingIDStr);

        try (Connection con = ConnectionManager.getConnection()) {

            // 1. Fetch booking info
            String sql = "SELECT c.Name, b.CheckInDate, b.CheckOutDate, r.RoomPrice " +
                         "FROM booking b " +
                         "LEFT JOIN customer c ON b.CustomerID = c.CustomerID " +
                         "LEFT JOIN room_type r ON b.RoomTypeID = r.RoomTypeID " +
                         "WHERE b.BookingID=?";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setInt(1, bookingID);
            ResultSet rs = ps2.executeQuery();

            if (!rs.next()) {
                request.setAttribute("errorMessage", "Booking not found.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            String name = rs.getString("Name");
            Date checkIn = rs.getDate("CheckInDate");
            Date checkOut = rs.getDate("CheckOutDate");
            double price = rs.getDouble("RoomPrice");
            long days = Math.max(1, (checkOut.getTime() - checkIn.getTime()) / (1000*60*60*24));
            double totalAmount = price * days * 1.02; // include 2% fee

            // 2. Update booking status to Paid
            String updateBooking = "UPDATE booking SET BookingStatus='Paid' WHERE BookingID=?";
            PreparedStatement ps = con.prepareStatement(updateBooking);
            ps.setInt(1, bookingID);
            ps.executeUpdate();

            // 3. Insert payment record
            String insertPayment = "INSERT INTO payment (BookingID, Amount, PaymentDate, PaymentMethod) " +
                                   "VALUES (?, ?, NOW(), ?)";
            PreparedStatement psPayment = con.prepareStatement(insertPayment);
            psPayment.setInt(1, bookingID);
            psPayment.setDouble(2, totalAmount);
            psPayment.setString(3, paymentMethod != null ? paymentMethod : "N/A");
            psPayment.executeUpdate();

            // 4. Generate TXT receipt
            response.setContentType("text/html"); // changed from text/plain
            response.setHeader("Content-Disposition", "inline; filename=receipt_" + bookingID + ".html");

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>HOTELORA Payment Receipt</title>");
            out.println("<style>");
            out.println("body { font-family: monospace; padding: 20px; background: #f9f9f9; }");
            out.println(".receipt { max-width: 700px; margin: auto; border: 1px solid #333; padding: 20px; background: #fff; }");
            out.println(".center { text-align: center; }");
            out.println(".button { display: inline-block; padding: 10px 20px; margin-top: 20px; background: #333; color: #fff; text-decoration: none; border-radius: 5px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='receipt'>");
            out.println("<div class='center'>");
            out.println("<h2>HOTELORA PAYMENT RECEIPT</h2>");
            out.println("<hr>");
            out.println("</div>");
            out.printf("<p>Customer Name   : %s</p>%n", name);
            out.printf("<p>Booking ID      : %d</p>%n", bookingID);
            out.printf("<p>Check-in Date   : %s</p>%n", checkIn);
            out.printf("<p>Check-out Date  : %s</p>%n", checkOut);
            out.printf("<p>Total Stay      : %d Night(s)</p>%n", days);
            out.printf("<p>Room Price      : RM %.2f per night</p>%n", price);
            out.printf("<p>Payment Method  : %s</p>%n", paymentMethod != null ? paymentMethod : "N/A");
            out.println("<hr>");
            out.printf("<p>Total Amount    : RM %.2f</p>%n", totalAmount);
            out.println("<p>Payment Status  : Paid</p>");
            out.println("<hr>");
            out.println("<p class='center'>Thank you for choosing HOTELORA! We hope you enjoy your stay.</p>");
            out.printf("<p class='center'>Date of Issue   : %s</p>%n", new java.util.Date());

            // Add button to go back to ViewMyBookings
            out.println("<div class='center'>");
            out.println("<a href='ViewMyBookingsServlet' class='button'>Back to My Bookings</a>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            out.flush();


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Payment processing failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
