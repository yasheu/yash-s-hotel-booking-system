<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    
    String roomID = request.getParameter("room"); 
    String roomCategory = request.getParameter("type");

   
    String sessCheckin = (String) session.getAttribute("sessionCheckin");
    String sessCheckout = (String) session.getAttribute("sessionCheckout");
    String sessGuests = (String) session.getAttribute("sessionGuests");

    
    if (sessCheckin == null || roomID == null) {
        response.sendRedirect("Homepage.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Booking - Hotelora</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="center-page">

<header><h1>CONFIRM YOUR STAY</h1></header>

<section class="search-box">
    <h2>Booking Details</h2>
    <form action="ConfirmBookingServlet" method="post">
        
        <input type="hidden" name="numGuests" value="<%= (sessGuests != null) ? sessGuests : "" %>">

        <div class="booking-summary">
            <label>Selected Room ID:</label>
            <input type="text" name="roomNumber" value="<%= roomID %>" readonly style="background:#eee; font-weight:bold;">
            
            <label>Room Category:</label>
            <input type="text" name="roomType" value="<%= roomCategory %>" readonly style="background:#eee; font-weight:bold;">
        
            <label>Guests:</label>
            <input type="text" value="<%= (sessGuests != null) ? sessGuests : "0" %> Adults" readonly style="background:#eee;">
        </div>

        <h3>Customer Information</h3>
        <label>Full Name</label>
        <input type="text" name="customerName" 
               value="${not empty sessionScope.fullname ? sessionScope.fullname : ''}" 
               placeholder="As per ID/Passport" required>

        <label>Email Address</label>
        <input type="email" name="customerEmail" 
               value="${not empty sessionScope.email ? sessionScope.email : ''}" 
               placeholder="example@mail.com" required>

        <label>Phone Number</label>
        <input type="text" name="customerPhone" 
               value="${not empty sessionScope.phone ? sessionScope.phone : ''}" 
               placeholder="e.g. +60123456789" required>

        <div style="display: flex; gap: 20px;">
            <div style="flex: 1;">
                <label>Check-in Date</label>
                <input type="date" name="checkin" value="<%= (sessCheckin != null) ? sessCheckin : "" %>" required>
            </div>
            <div style="flex: 1;">
                <label>Check-out Date</label>
                <input type="date" name="checkout" value="<%= (sessCheckout != null) ? sessCheckout : "" %>" required>
            </div>
        </div>

						 <div class="button-group">
		    <button class="book-btn" type="submit" name="confirm">Confirm Booking</button>
		
		    <a href="Homepage.jsp" class="return-btn">Return to Homepage</a>
		</div>
    </form>
</section>
</body>
</html>