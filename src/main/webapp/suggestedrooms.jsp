<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.Base64, login.ConnectionManager" %>
<%
    // 1. CAPTURE: Get the search data sent from Homepage.jsp URL
    String selectedLocation = request.getParameter("location"); // New parameter
    String checkin = request.getParameter("checkin");
    String checkout = request.getParameter("checkout");
    String guests = request.getParameter("guests");

    // 2. SAVE: Store them in the Session
    if (selectedLocation != null) session.setAttribute("sessionLocation", selectedLocation);
    if (checkin != null) session.setAttribute("sessionCheckin", checkin);
    if (checkout != null) session.setAttribute("sessionCheckout", checkout);
    if (guests != null) session.setAttribute("sessionGuests", guests);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Suggested Rooms – Hotelora</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header>
    <h1 class="hotel-name">
		  <span class="letter1">H</span><span class="letter2">O</span><span class="letter3">T</span><span class="letter4">E</span><span class="letter5">L</span><span class="letter6">O</span><span class="letter7">R</span><span class="letter8">A</span>
		</h1>
</header>

<ul class="nav-bar">
    <li><a href="Homepage.jsp">Home</a></li>
    <li><a href="suggestedrooms.jsp" class="active">Suggested Rooms</a></li>
    <li><a href="#about">About</a></li>
</ul>  

<section class="room-container">
    <h2 class="text-white">Available Rooms in <%= (selectedLocation != null) ? selectedLocation : "Your Selected Location" %></h2>

<%
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = ConnectionManager.getConnection(); 
        
        // 3. SQL: Get rooms in selected location excluding already booked rooms
        String sql = "SELECT rt.*, h.Location " +
                     "FROM room_type rt " +
                     "JOIN hotel h ON rt.HotelID = h.HotelID " +
                     "WHERE h.Location = ? " +
                     (checkin != null && checkout != null ? 
                     "AND rt.RoomTypeID NOT IN ( " +
                     "    SELECT b.RoomTypeID FROM booking b " +
                     "    WHERE (b.CheckInDate <= ? AND b.CheckOutDate >= ?) " +
                     ")" : "");

        ps = con.prepareStatement(sql);
        ps.setString(1, selectedLocation);

        if (checkin != null && checkout != null) {
            ps.setString(2, checkout); // b.CheckInDate <= checkout
            ps.setString(3, checkin);  // b.CheckOutDate >= checkin
        }

        rs = ps.executeQuery();

        if (!rs.isBeforeFirst()) { // No rooms available
%>
            <div style="text-align:center; padding: 50px;">
                <h3 class="text-white">No rooms currently available in <%= selectedLocation %> for your selected dates.</h3>
                <a href="Homepage.jsp" class="book-btn">Change Location / Dates</a>
            </div>
<%
        }

        while(rs.next()) {
            int id = rs.getInt("RoomTypeID");
            String name = rs.getString("RoomTypeName");
            double price = rs.getDouble("RoomPrice");
            String desc = rs.getString("RoomDescription");

            byte[] imgData = rs.getBytes("roomImage");
            String base64Image = "images/placeholder.jpg"; // default image
            if (imgData != null && imgData.length > 0) {
                base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imgData);
            }
%>

    <div class="room-card">
        <div class="room-image-box">
            <img src="<%= base64Image %>" alt="Room Image" class="room-card-img">
            <span class="featured-badge">Featured</span>
        </div>

        <div class="room-details-box">
            <div class="room-header">
                <h3 class="room-name-link"><%= name %></h3>
            </div>
            
            <p class="room-specs">
                <%= (desc != null && !desc.isEmpty()) ? desc : "Experience comfort in our premium rooms." %>
            </p>
            <p class="inclusion-text">Free Cancellation </p>
        </div>

        <div class="room-price-box">
            <p class="price-value">MYR <%= String.format("%.2f", price) %></p>
            <p class="tax-info">+ 2% taxes </p>
            <button onclick="bookRoom('<%= id %>', '<%= name %>')" class="book-btn"> Book Now > </button>
        </div>
    </div>

<% 
        }
    } catch (Exception e) {
        out.println("<div style='color:red'>Database Error: " + e.getMessage() + "</div>");
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
%>
</section>

<script>
function bookRoom(roomNo, roomType) {
    window.location.href = "book_room.jsp?room=" + roomNo + "&type=" + encodeURIComponent(roomType);
}
</script>

</body>
</html>
