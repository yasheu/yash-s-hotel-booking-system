package hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.ConnectionManager;

@WebServlet("/UpdateRoomServlet")
public class UpdateRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1️⃣ Get form data
        String roomNumberStr = request.getParameter("roomNumber");
        String hotelLocation = request.getParameter("hotelLocation");
        String roomPriceStr = request.getParameter("roomPrice");
        String roomSizeStr = request.getParameter("roomSize");
        String roomTypeName = request.getParameter("roomTypeName");

        // 2️⃣ Validate
        if (roomNumberStr == null || hotelLocation == null || roomPriceStr == null ||
            roomSizeStr == null || roomTypeName == null ||
            roomNumberStr.isEmpty() || hotelLocation.isEmpty() ||
            roomPriceStr.isEmpty() || roomSizeStr.isEmpty() || roomTypeName.isEmpty()) {

            response.getWriter().println("Form data missing!");
            return;
        }

        int roomNumber = Integer.parseInt(roomNumberStr);
        double roomPrice = Double.parseDouble(roomPriceStr);
        double roomSize = Double.parseDouble(roomSizeStr); // in sqft

        try (Connection conn = ConnectionManager.getConnection()) {

            // 3️⃣ Get HotelID from location
            int hotelID = 0;
            String hotelSql = "SELECT HotelID FROM hotel WHERE Location = ?";
            try (PreparedStatement psHotel = conn.prepareStatement(hotelSql)) {
                psHotel.setString(1, hotelLocation);
                ResultSet rs = psHotel.executeQuery();
                if (rs.next()) {
                    hotelID = rs.getInt("HotelID");
                } else {
                    response.getWriter().println("Invalid hotel location!");
                    return;
                }
            }

            // 4️⃣ Update room
            String updateSql = "UPDATE room_type SET HotelID=?, RoomPrice=?, RoomSize=?, RoomTypeName=? WHERE RoomNumber=?";

            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, hotelID);
                ps.setDouble(2, roomPrice);
                ps.setString(3, String.valueOf(roomSize)); // store as string in sqft
                ps.setString(4, roomTypeName);
                ps.setInt(5, roomNumber);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    // Redirect back to view page after successful update
                    response.sendRedirect("ViewRoomsForUpdateServlet");
                } else {
                    response.getWriter().println("Room not found!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        }
    }
}
