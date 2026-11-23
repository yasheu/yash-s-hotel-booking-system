package hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.ConnectionManager; 

@WebServlet("/ViewRoomsForUpdateServlet")
public class ViewRoomsForUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Room> allRooms = new ArrayList<>();

        String sql = "SELECT rt.RoomNumber, rt.RoomTypeName, rt.RoomSize, rt.RoomPrice, h.Location "
        		+ "FROM room_type rt JOIN hotel h ON rt.HotelID = h.HotelID";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomNumber(rs.getInt("RoomNumber"));
                room.setRoomTypeName(rs.getString("RoomTypeName"));
                room.setRoomSize(rs.getString("RoomSize"));
                room.setLocation(rs.getString("Location"));
                room.setPrice(rs.getDouble("RoomPrice"));

                allRooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("availableRooms", allRooms);
        // Only forward once to the update page
        request.getRequestDispatcher("updateroom.jsp").forward(request, response);
    }
}
