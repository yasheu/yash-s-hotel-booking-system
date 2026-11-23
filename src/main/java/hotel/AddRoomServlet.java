package hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import login.ConnectionManager;

@WebServlet("/AddRoomServlet")
public class AddRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String location = request.getParameter("hotelLocation");
        String roomSize = request.getParameter("roomSize");
        String roomTypeIDStr = request.getParameter("roomTypeID"); // just for type name
        String roomNumberStr = request.getParameter("roomNumber");
        String roomPriceStr = request.getParameter("roomPrice");

        if(location == null || roomSize == null || roomTypeIDStr == null 
           || roomNumberStr == null || roomPriceStr == null) {
            response.getWriter().println("Form data missing!");
            return;
        }

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ Get or create HotelID
            int hotelID = 0;
            String hotelQuery = "SELECT HotelID FROM hotel WHERE Location=?";
            try (PreparedStatement pstmtH = conn.prepareStatement(hotelQuery)) {
                pstmtH.setString(1, location);
                ResultSet rs = pstmtH.executeQuery();
                if(rs.next()) hotelID = rs.getInt("HotelID");
                else {
                    String insHotel = "INSERT INTO hotel (HotelName, Location) VALUES (?, ?)";
                    try (PreparedStatement pstmtI = conn.prepareStatement(insHotel, Statement.RETURN_GENERATED_KEYS)) {
                        pstmtI.setString(1, "Hotelora " + location);
                        pstmtI.setString(2, location);
                        pstmtI.executeUpdate();
                        ResultSet rsI = pstmtI.getGeneratedKeys();
                        if(rsI.next()) hotelID = rsI.getInt(1);
                    }
                }
            }

            // 2️⃣ Check duplicate RoomNumber in the same hotel
            String checkSql = "SELECT RoomNumber FROM room_type WHERE RoomNumber=? AND HotelID=?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, Integer.parseInt(roomNumberStr));
                psCheck.setInt(2, hotelID);
                ResultSet rsCheck = psCheck.executeQuery();
                if(rsCheck.next()) {
                    response.getWriter().println("Room number already exists in this hotel!");
                    return;
                }
            }

            // 3️⃣ Insert new room (RoomTypeID auto-increment)
            String roomSql = "INSERT INTO room_type "
                    + "(HotelID, RoomNumber, RoomSize, RoomPrice, RoomTypeName, RoomDescription, roomImage) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtR = conn.prepareStatement(roomSql)) {
                pstmtR.setInt(1, hotelID);
                pstmtR.setInt(2, Integer.parseInt(roomNumberStr));
                pstmtR.setString(3, roomSize);
                pstmtR.setDouble(4, Double.parseDouble(roomPriceStr));
                pstmtR.setString(5, "Room Type " + roomTypeIDStr);
                pstmtR.setString(6, "No description");
                pstmtR.setNull(7, Types.BLOB); // image optional

                int result = pstmtR.executeUpdate();
                if(result > 0) conn.commit();

                response.sendRedirect("add_room.jsp?add=success");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        }
    }
}
