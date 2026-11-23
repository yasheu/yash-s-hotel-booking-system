<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Customer Account List</h2>
        <table border="1" class="booking-table">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cust" items="${customerList}">
                    <tr>
                        <td>${cust.userId}</td>
                        <td>${cust.name}</td>
                        <td>${cust.email}</td>
                        <td>${cust.phone}</td>
                        <td>
                            <form action="DeleteCustomerServlet" method="POST" onsubmit="return confirm('Are you sure?');">
							 <input type="hidden" name="userId" value="${cust.userId}">
							 <button type="submit" class="book-btn">Delete</button>
							</form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <a href="admin_homepage.html">Back to Dashboard</a>
    </div>
</body>
</html>