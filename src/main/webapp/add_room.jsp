<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Room - Hotelora Admin</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="center-page">

<header>
    <h1>ADMIN PANEL</h1>
</header>

<!-- ===================== SUCCESS MESSAGE ===================== -->
<c:if test="${param.add eq 'success'}">
    <p style="color:green;">Room added successfully!</p>
    <form action="ViewAvailableRoomsServlet" method="get">
        <button type="submit">View All Rooms</button>
    </form>
</c:if>

<!-- ===================== AVAILABLE ROOMS TABLE ===================== -->
<section class="search-box" style="margin-top: 30px;">
    <h2>All Available Rooms</h2>
    <table style="width:100%; border-collapse: collapse; margin-top: 10px;" border="1">
        <thead style="background-color: #f8f8f8;">
            <tr>
                <th>Room Number</th>
                <th>Type Name</th>
                <th>Hotel Location</th>
                <th>Room Size</th>
                <th>Price (RM)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="room" items="${availableRooms}">
                <tr>
                    <td>${room.roomNumber}</td>
                    <td>${room.roomTypeName}</td>
                    <td>${room.location}</td>
                    <td>${room.roomSize}</td>
                    <td>${room.price}</td>
                </tr>
            </c:forEach>

            <c:if test="${empty availableRooms}">
                <tr>
                    <td colspan="5" style="text-align:center; padding: 20px;">
                        No rooms found.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</section>

<!-- ===================== ADD NEW ROOM FORM ===================== -->
<section class="search-box" style="margin-top: 30px;">
    <h2>Add New Room</h2>
    <form action="AddRoomServlet" method="post">

        <label>Hotel Location</label>
        <select name="hotelLocation" required>
            <option value="">-- Select Location --</option>
            <option value="Kuala Lumpur">Kuala Lumpur</option>
            <option value="Pulau Pinang">Pulau Pinang</option>
        </select>

        <label>Room Number (ID)</label>
        <input type="number" name="roomNumber" required>

        <label>Room Price (RM)</label>
        <input type="number" name="roomPrice" step="0.01" min="0" required>

        <label>Room Size (sqm)</label>
        <input type="number" name="roomSize" step="0.1" min="1" placeholder="Enter size in sqm" required>
        <br><br>

        <label>Room Type</label>
        <input type="text" name="roomTypeName" placeholder="Enter room type" required>
        <br><br>

        <button type="submit">Add Room</button>
    </form>

    <div style="margin-top: 15px;">
        <a href="admin_homepage.html" style="color: #333; text-decoration: none;">Cancel</a>
    </div>
</section>

</body>
</html>
