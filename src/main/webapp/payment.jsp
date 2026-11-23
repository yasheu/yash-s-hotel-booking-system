<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment | Hotelora</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

<header>
    <div class="hotel-name">
       <span class="letter1">H</span><span class="letter2">O</span><span class="letter3">T</span><span class="letter4">E</span><span class="letter5">L</span><span class="letter6">O</span><span class="letter7">R</span><span class="letter8">A</span></div>
</header>

<div class="search-box" style="min-height: auto;">
    
    <div class="text-white">
        <h2 style="margin-top: 0;">HOTEL PAYMENT</h2>
        <hr style="border: 0.5px solid rgba(255,255,255,0.3); margin-bottom: 20px;">
    </div>

    <div class="booking-container">
        <table class="booking-table">
            <thead>
                <tr>
                    <th colspan="2" style="text-align: center;">Payment Summary</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><strong>Customer Name</strong></td>
                    <td>${payment.customerName}</td>
                </tr>
                <tr>
                    <td><strong>Booking ID</strong></td>
                    <td>${payment.bookingID}</td>
                </tr>
                <tr>
                    <td><strong>Check-in Date</strong></td>
                    <td><fmt:formatDate value="${payment.checkInDate}" pattern="d MMMM yyyy" /></td>
                </tr>
                <tr>
                    <td><strong>Check-out Date</strong></td>
                    <td><fmt:formatDate value="${payment.checkOutDate}" pattern="d MMMM yyyy" /></td>
                </tr>
                <tr>
                    <td><strong>Total Stay</strong></td>
                    <td>${payment.totalStay} Night(s)</td>
                </tr>
                <tr style="background-color: rgba(193, 154, 107, 0.1);">
                    <td><strong>Total Amount</strong></td>
                    <td style="font-size: 1.2em; color: #008009;">
                        <strong>RM <fmt:formatNumber value="${payment.amount}" type="number" minFractionDigits="2" maxFractionDigits="2"/></strong>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Form submits to servlet in a new tab for TXT receipt -->
    <form id="paymentForm" action="ProcessPaymentServlet" method="POST" target="_blank" style="margin-top: 25px; text-align: left;">
        <input type="hidden" name="bookingID" value="${payment.bookingID}">
        
        <label for="paymentType" style="color: white; font-weight: bold;">Choose Payment Type:</label>
        <select name="paymentType" id="paymentType" required>
            <option value="">-- Select Method --</option>
            <option value="credit_card">Credit/Debit Card</option>
            <option value="online_banking">Online Banking</option>
        </select>

        <label for="details" style="color: white; font-weight: bold;">Card / Account Details:</label>
        <input type="text" id="details" name="details" placeholder="Enter Card or Account Number" required>

        <div class="button-group">
            <button class="book-btn" type="submit">Pay Now</button>
            <a href="ViewMyBookingsServlet" class="return-btn">Cancel and go back</a>
        </div>
    </form>
</div>



</body>
</html>