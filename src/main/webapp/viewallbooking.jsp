<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - All Bookings</title>
    <link rel="stylesheet" href="style.css">
</head>

<body class="center-page">

<header>
    <h1>ALL CUSTOMER BOOKINGS</h1>
</header>

<div style="text-align: center; margin-bottom: 20px;">
    <h3>Admin Dashboard</h3>
</div>

<table class="booking-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Phone</th>
            <th>Location</th>
            <th>Room</th>
            <th>Check-In</th>
            <th>Check-Out</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="b" items="${allBookings}">
    <tr>
        <td>BK${b.bookingID}</td>
        <td>${b.customerName}</td>
        <td>${b.customerPhone}</td> 
        <td>${b.location}</td>
        <td>${b.roomNumber}</td>
        <td>${b.checkInDate}</td>
        <td>${b.checkOutDate}</td>
        <td>
            <span style="color: ${b.bookingStatus == 'Confirmed' ? 'green' : 'red'}; font-weight:bold;">
                ${b.bookingStatus}
            </span>
        </td>
        <td>
            <div style="display: flex; gap: 10px; justify-content: center; align-items: center;">
                <a href="EditBookingServlet?id=${b.bookingID}" class="book-btn" style="padding: 5px 10px; font-size: 12px;">Edit</a>
                <a href="DeleteAllBookingsServlet?id=${b.bookingID}" class="book-btn" style="background:red; padding: 5px 10px; font-size: 12px;" onclick="return confirm('Are you sure?');">Delete</a>
            </div>
        </td>
    </tr>
	</c:forEach>

        <c:if test="${empty allBookings}">
            <tr>
                <td colspan="9" style="text-align:center; padding: 20px;">No bookings found.</td>
            </tr>
        </c:if>
    </tbody>
</table>

<div style="text-align: center; margin-top: 20px;">
    <a href="admin_homepage.html" class="book-btn">Back to Admin Home</a>
</div>

</body>
</html>