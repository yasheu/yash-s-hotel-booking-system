<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Room - Hotelora Admin</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .room-label { margin-right: 15px; font-weight: normal; }
        .search-box {
            display: flex; flex-direction: column; align-items: center;
            background: rgba(255,255,255,0.1);
            padding: 30px; border-radius: 15px;
            margin: 30px auto; max-width: 900px;
        }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        table th, table td { padding: 8px; border: 1px solid #ccc; text-align: left; }
        table th { background-color: #f8f8f8; }
        input, select { padding: 5px; margin-bottom: 10px; width: 100%; }
        button { padding: 10px 20px; margin-top: 10px; }
    </style>
</head>
<body class="center-page">

<header>
    <h1>ADMIN PANEL</h1>
</header>

<!-- ===================== AVAILABLE ROOMS TABLE ===================== -->
<section class="search-box">
    <h2>All Available Rooms</h2>
    <table>
        <thead>
            <tr>
                <th>Room Number</th>
                <th>Type Name</th>
                <th>Hotel Location</th>
                <th>Room Size (sqft)</th>
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
                    <td colspan="5" style="text-align:center; padding: 20px;">No rooms found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</section>

<!-- ===================== UPDATE ROOM FORM ===================== -->
<section class="search-box">
    <h2>Update Room Details</h2>
    <form action="UpdateRoomServlet" method="post">

        <label>Select Room Number</label>
        <select name="roomNumber" required>
            <option value="">-- Select Room --</option>
            <c:forEach var="room" items="${availableRooms}">
                <option value="${room.roomNumber}">Room ${room.roomNumber} - ${room.location}</option>
            </c:forEach>
        </select>

       <label>New Hotel Location</label>
		<select name="hotelLocation" required>
		    <option value="">-- Select Location --</option>
		    <option value="Kuala Lumpur">Kuala Lumpur</option>
		    <option value="Pulau Pinang">Pulau Pinang</option>
		</select>



        <label>New Room Price (RM)</label>
        <input type="number" name="roomPrice" step="0.01" min="0" required>

        <label>New Room Size (sqft)</label>
        <input type="number" name="roomSize" step="0.1" min="0" required>

        <label>New Room Type Name</label>
        <input type="text" name="roomTypeName" required>

        <button type="submit">Update Room</button>
        <div style="margin-top: 15px;">
            <a href="admin_homepage.html" style="color: #333; text-decoration: none; font-weight: bold;">Cancel</a>
        </div>
    </form>
</section>

</body>
</html>
