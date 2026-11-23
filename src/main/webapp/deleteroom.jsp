<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Rooms - Hotelora Admin</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .center-page { max-width: 900px; margin: auto; padding: 20px; }
        .search-box {
            display: flex; flex-direction: column; align-items: center;
            background: rgba(255,255,255,0.1);
            padding: 30px; border-radius: 15px;
            margin: 30px auto; width: 100%;
        }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #f8f8f8; }
        a.delete-link { color: red; text-decoration: none; }
        a.delete-link:hover { text-decoration: underline; }
    </style>
</head>
<body class="center-page">

<header>
    <h1>ADMIN PANEL</h1>
</header>

<!-- ===================== SUCCESS/ERROR MESSAGES ===================== -->
<c:if test="${not empty deleteSuccess}">
    <p style="color:green;">Room deleted successfully!</p>
</c:if>
<c:if test="${not empty deleteError}">
    <p style="color:red;">${deleteError}</p>
</c:if>

<!-- ===================== AVAILABLE ROOMS TABLE ===================== -->
<section class="search-box">
    <h2>All Available Rooms</h2>
    <table>
        <thead>
            <tr>
                <th>Room Number</th>
                <th>Type Name</th>
                <th>Hotel Location</th>
                <th>Room Size (sqm)</th>
                <th>Price (RM)</th>
                <th>Action</th>
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
                    <td>
                        <a href="DeleteRoomServlet?roomNumber=${room.roomNumber}" 
                           class="delete-link"
                           onclick="return confirm('Are you sure you want to delete this room?');">
                           Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty availableRooms}">
                <tr>
                    <td colspan="6" style="text-align:center; padding: 20px;">No rooms found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
     <!-- Back Button -->
    <div style="margin-top: 20px;">
        <a href="admin_homepage.html" class="book-btn">Back to Admin Homepage</a>
    </div>

</section>

</body>
</html>
