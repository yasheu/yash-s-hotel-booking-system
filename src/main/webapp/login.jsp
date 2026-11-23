<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Hotelora</title>
    <link rel="stylesheet" href="style.css">
    
    <style>
        .error-message {
            color: #d9534f; /* Softer red */
            background-color: #f2dede;
            border: 1px solid #ebccd1;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <div class="auth-links">
        <a href="Homepage.jsp" class="auth-link">Home</a>
    </div>

    <header>
        <div class="hotel-name">
            <span class="letter1">H</span>
            <span class="letter2">O</span>
            <span class="letter3">T</span>
            <span class="letter4">E</span>
            <span class="letter5">L</span>
            <span class="letter6">O</span>
            <span class="letter7">R</span>
            <span class="letter8">A</span>
        </div>
        <p>Experience Comfort & Luxury</p>
    </header>

    <div class="search-box"> 
        <h2 style="text-align: center; color: #333;">Welcome Back</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <form action="LoginController" method="post">
            
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>

            <button class="book-btn" type="submit">Login</button>
            
            <input type="reset" value="Reset" style="background-color: #6c757d; margin-top: 10px; color:white; border:none; padding:10px; width:100%; border-radius:5px; cursor:pointer;">
        </form>

        <p style="text-align: center; margin-top: 15px;">
            Don't have an account? <a href="register.html" style="color: #0073e6; font-weight: bold;">Register here</a>
        </p>
    </div>

    

</body>
</html>