Hotel Booking System

A web-based hotel reservation system built with Java Servlets, JSP and MySQL using the MVC (Model–View–Controller) architecture. Developed as a group project for Enterprise Programming at Universiti Teknologi MARA (UiTM), CS230.

Customers can register, browse available rooms by location and date, make bookings and pay online. Administrators can manage rooms, bookings and customers, and view a revenue report.

Features

Customer
Register an account and log in / log out (session-based)
Search available rooms by location (Kuala Lumpur, Pulau Pinang) and check-in / check-out dates
View suggested rooms (Standard, Deluxe, Suite) with images, size and price
Book a room and choose the number of guests
Pay for a booking (Credit/Debit Card or Online Banking) and get a receipt
View and cancel your own bookings

Admin
Separate admin dashboard
Room management: add, update and delete rooms (with room image)
Booking management: view all bookings, delete one or all bookings
Customer management: view and delete customer records
Booking report: total revenue, number of payments, revenue by room type and top 5 customers




Tech Stack
Language	Java 21
Web	Jakarta Servlet 5.0, JSP, JSTL 3.0
Server	Apache Tomcat 10.1
Database	MySQL (via MySQL Connector/J 8.0.31)
Front end	HTML, CSS
IDE	Eclipse IDE for Enterprise Java (Dynamic Web Project)


Database

Database name: hotel_booking_system

Table	Key columns
users	userid (PK), email, password
customer	CustomerID (PK), Name, CustomerPhoneNum, userid (FK → users)
hotel	HotelID (PK), HotelName, Location
room_type	RoomTypeID (PK), HotelID (FK → hotel), RoomNumber, RoomTypeName, RoomSize, RoomPrice, RoomDescription, roomImage
booking	BookingID (PK), BookingDate, BookingStatus, NumOfGuest, CheckInDate, CheckOutDate, CustomerID (FK → customer), RoomTypeID (FK → room_type)
payment	PaymentID (PK), Amount, PaymentDate, PaymentMethod, BookingID (FK → booking)

Relationships: users 1─1 customer 1─N booking 1─1 payment, and hotel 1─N room_type 1─N booking.


Prerequisites
JDK 21
Apache Tomcat 10.1
MySQL Server (e.g. via XAMPP / WAMP / MySQL Workbench)
Eclipse IDE for Enterprise Java and Web Developers


Default admin login
Email	Password
admin@gmail.com	admin

Customers can create their own accounts from the Register page.



This is an academic project and is not intended for production use
