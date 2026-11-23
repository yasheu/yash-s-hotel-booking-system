<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Booking Info</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="center-page">
    <header><h1>BOOKING INFO</h1></header>

    <table class="booking-table">
        <thead>
            <tr>
                <th>Booking ID</th>
                <th>Customer Name</th>
                <th>Hotel Location</th>
                <th>Room Number</th>
                <th>Check-In Date</th>
                <th>Check-Out Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${myBookings}">
                <tr>
                    <td>BK${b.bookingID}</td>
                    <td>${b.customerName}</td>
                    <td>${b.location}</td>
                    <td>${b.roomNumber}</td>
                    <td>${b.checkInDate}</td>
                    <td>${b.checkOutDate}</td>
                    <td>
                        <span style="color: ${b.bookingStatus == 'Paid' ? 'green' : (b.bookingStatus == 'Confirmed' ? 'blue' : 'red')}; font-weight:bold;">
                            ${b.bookingStatus}
                        </span>
                    </td>
                    <td>
                        <div style="display: flex; gap: 10px; justify-content: center;">
                            <a href="EditBookingServlet?id=${b.bookingID}" class="book-btn">Edit</a>
                            <a href="DeleteBookingServlet?id=${b.bookingID}" class="book-btn" style="background:red;" 
                               onclick="return confirm('Cancel this booking?');">Cancel Book</a>

                            <!-- Only show Pay Now if status is NOT Paid -->
                            <c:if test="${b.bookingStatus != 'Paid'}">
                                <a href="PaymentServlet?bookingID=${b.bookingID}" class="book-btn">Pay Now</a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            
            <c:if test="${empty myBookings}">
                <tr><td colspan="8" style="text-align:center;">No bookings found.</td></tr>
            </c:if>
        </tbody>
    </table>

    <div style="text-align: center; margin-top: 20px;">
        <a href="Homepage.jsp" class="book-btn">Back to Home</a>
    </div>
</body>
</html>
