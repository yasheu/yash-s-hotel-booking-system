<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotelora - Business Report</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .report-header { text-align: center; padding: 20px; border-bottom: 3px solid #004085; background: white; }
        .main-content { max-width: 1100px; margin: 30px auto; padding: 0 20px; }

        /* Top Banner Style (Like Top Point Collector) */
        .summary-banner {
            background: #004085;
            color: white;
            padding: 40px;
            border-radius: 12px;
            text-align: center;
            margin-bottom: 40px;
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .summary-banner h2 { font-size: 20px; opacity: 0.9; text-transform: uppercase; letter-spacing: 1px; }
        .total-amount { font-size: 56px; font-weight: 800; margin: 15px 0; }

        /* Grid for side-by-side tables */
        .report-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }
        .report-section { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); }
        
        /* Matching the Red Vertical Line from reference image */
        .section-title {
            border-left: 5px solid #dc3545; 
            padding-left: 15px;
            font-weight: bold;
            font-size: 18px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        table { width: 100%; border-collapse: collapse; }
        th { text-align: left; background: #dc3545; color: white; padding: 12px; font-size: 14px; }
        td { padding: 12px; border-bottom: 1px solid #eee; font-size: 14px; color: #444; }
        tr:hover { background-color: #fcfcfc; }
        
        .footer-info { text-align: center; margin-top: 50px; color: #888; padding-bottom: 20px; }
    </style>
</head>
<body>

    <div class="report-header">
        <h1>Hotelora Business Analytics</h1>
    </div>

    <div class="main-content">
        <div class="summary-banner">
            <h2><i class="fa-solid fa-money-check-dollar"></i> Overall Revenue (This Year)</h2>
            <div class="total-amount">
                RM <fmt:formatNumber value="${totalRevenue}" minFractionDigits="2" />
            </div>
            <p>Total Completed Transactions: ${totalCompletedBookings}</p>
        </div>

        <div class="report-grid">
            <div class="report-section">
                <div class="section-title">
                    <i class="fa-solid fa-bed" style="color: #dc3545;"></i> Best Selling Room Types
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Room Type</th>
                            <th>Total Revenue</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="room" items="${roomTypeRevenue}">
                            <tr>
                                <td>${room.roomTypeName}</td>
                                <td>RM <fmt:formatNumber value="${room.revenue}" minFractionDigits="2" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="report-section">
                <div class="section-title">
                    <i class="fa-solid fa-crown" style="color: #dc3545;"></i> Top Regular Customers
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Customer Name</th>
                            <th>Total Bookings</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="customer" items="${topCustomers}">
                            <tr>
                                <td>${customer.name}</td>
                                <td>${customer.bookingCount} stays</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="button-group">
		    
		
		    <a href="admin_homepage.html" class="book-btn">Return to Homepage</a>
		</div>
    </div>

    

</body>
</html>