<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, login.ConnectionManager" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hotel Booking System - Hotelora</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <header>
        <div class="auth-links">
            <%
                // 1. Check if the user is logged in
                String fullName = (String) session.getAttribute("fullname"); 
                
                if (fullName != null) {
            %>
                <span class="auth-link" style="border:none; background:none;">Hi, <%= fullName %>!</span>
                <a href="LogoutController" class="auth-link">Logout</a>
            <%
                } else {
            %>
                <a href="login.jsp" class="auth-link">Login</a>
                <a href="register.html" class="auth-link">Register</a>
            <%
                }
            %>
        </div>
		 
        <h1 class="hotel-name">
		  <span class="letter1">H</span><span class="letter2">O</span><span class="letter3">T</span><span class="letter4">E</span><span class="letter5">L</span><span class="letter6">O</span><span class="letter7">R</span><span class="letter8">A</span>
		</h1>
    </header>

    <ul class="nav-bar" style="display: flex; align-items: center;">
    <li><a href="Homepage.jsp" class="active">Home</a></li>
    <% if (fullName != null) { %>
        <li><a href="ViewMyBookingsServlet">My Bookings</a></li>
    <% } %>
    
   <li class="about-item"><a href="about.jsp">About</a></li>
 </ul>
  
    <section class="search-box">
        <h2 class="text-white">Search for a Hotel</h2>
        <form action="suggestedrooms.jsp" method="get">
            
            <label>Location</label>
            <select name="location" required>
                <option value="">-- Select Location --</option>
                <%
                    Connection locCon = null;
                    Statement locStmt = null;
                    ResultSet locRs = null;

                    try {
                        locCon = ConnectionManager.getConnection();
                        locStmt = locCon.createStatement();
                        locRs = locStmt.executeQuery("SELECT DISTINCT location FROM hotel ORDER BY location ASC");

                        while(locRs.next()) {
                            String city = locRs.getString("location");
                %>
                            <option value="<%= city %>"><%= city %></option>
                <%
                        }
                    } catch (Exception e) {
                        out.println("<option value=''>Error loading locations</option>");
                    } finally {
                        if (locRs != null) try { locRs.close(); } catch(Exception e) {}
                        if (locStmt != null) try { locStmt.close(); } catch(Exception e) {}
                        if (locCon != null) try { locCon.close(); } catch(Exception e) {}
                    }
                %>
            </select>

		      <div style="display: flex; gap: 15px; margin-bottom: 20px;">
		    <div style="flex: 1;">
		        <label>Check-in Date</label>
		        <input type="date" name="checkin" id="checkin" required>
		    </div>
		    <div style="flex: 1;">
		        <label>Check-out Date</label>
		        <input type="date" name="checkout" id="checkout" required>
		    </div>

			 <div style="flex: 1;">
            <label>Guests</label>
            <select name="guests" required>
                <option value="1">1 Adult</option>
                <option value="2">2 Adults</option>
                <option value="3">3 Adults</option>
                <option value="4">4 Adults</option>
            </select>
			</div>
            <div style="margin-top: 20px;">
                <button class="book-btn" type="submit" name="submit">Search Rooms</button>
            </div>
        </form>
    </section>

  

    <script>
        
        const today = new Date().toISOString().split('T')[0];
        const checkinInput = document.getElementsByName("checkin")[0];
        const checkoutInput = document.getElementsByName("checkout")[0];

        if (checkinInput && checkoutInput) {
            checkinInput.setAttribute('min', today);
            checkoutInput.setAttribute('min', today);

            
            checkinInput.addEventListener('change', function() {
                if (this.value) {
                    checkoutInput.setAttribute('min', this.value);
                    
                   
                    if (checkoutInput.value && checkoutInput.value < this.value) {
                        checkoutInput.value = "";
                    }
                }
            });
        }
    </script>
</body>
</html>