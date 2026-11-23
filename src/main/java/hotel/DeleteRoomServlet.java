package hotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import login.ConnectionManager;

@WebServlet("/DeleteRoomServlet")
public class DeleteRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String roomNumberStr = request.getParameter("roomNumber");

        if (roomNumberStr == null || roomNumberStr.isEmpty()) {
            request.setAttribute("deleteError", "Room number missing!");
            request.getRequestDispatcher("ViewRoomsForDeleteServlet").forward(request, response);
            return;
        }

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM room_type WHERE RoomNumber = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(roomNumberStr));
                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    conn.commit();
                    request.setAttribute("deleteSuccess", true);
                } else {
                    request.setAttribute("deleteError", "Room not found!");
                }

            } catch (SQLException e) {
                // Catch foreign key constraint violation
                if (e.getErrorCode() == 1451) { // MySQL FK constraint error code
                    request.setAttribute("deleteError", "This room has already been booked!");
                } else {
                    request.setAttribute("deleteError", "Database error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("deleteError", "Database error: " + e.getMessage());
        }

        // Forward back to view page
        request.getRequestDispatcher("ViewRoomsForDeleteServlet").forward(request, response);
    }
}
