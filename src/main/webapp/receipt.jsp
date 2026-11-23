<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt | Hotelora</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; text-align: center; color: #333; }
        h2 { color: #4CAF50; margin-bottom: 20px; }
        table { margin: 0 auto; border-collapse: collapse; width: 60%; max-width: 500px; }
        td { padding: 10px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        td:nth-child(1) { font-weight: bold; }
        tr.total-row td { background-color: #f0f0f0; font-size: 1.2em; color: #008009; }
        @media print { body { margin: 0; } }
    </style>
</head>
<body onload="window.print()">

<h2>Hotelora Payment Receipt</h2>

<table>
    <tr>
        <td>Customer Name</td>
        <td>${payment.customerName}</td>
    </tr>
    <tr>
        <td>Booking ID</td>
        <td>${payment.bookingID}</td>
    </tr>
    <tr>
        <td>Check-in Date</td>
        <td><fmt:formatDate value="${payment.checkInDate}" pattern="d MMMM yyyy"/></td>
    </tr>
    <tr>
        <td>Check-out Date</td>
        <td><fmt:formatDate value="${payment.checkOutDate}" pattern="d MMMM yyyy"/></td>
    </tr>
    <tr>
        <td>Total Stay</td>
        <td>${payment.totalStay} Night(s)</td>
    </tr>
    <tr class="total-row">
        <td>Total Amount</td>
        <td>RM <fmt:formatNumber value="${payment.amount}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
    </tr>
</table>

<p style="margin-top: 30px; font-size: 0.9em; color: #555;">
    &copy; 2026 Hotelora - Thank you for your payment!
</p>

</body>
</html>
