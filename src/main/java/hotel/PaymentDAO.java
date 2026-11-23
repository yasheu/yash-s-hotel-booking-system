package hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import login.ConnectionManager; // Import the connection manager
import java.sql.Date;

public class PaymentDAO {
    // Removed hardcoded credentials

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO payment (Amount, PaymentDate, PaymentMethod, BookingID) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payment;";
    private static final String DELETE_PAYMENT_SQL = "DELETE FROM payment WHERE PaymentID = ?;";
    private static final String UPDATE_PAYMENT_SQL = "UPDATE payment SET Amount = ?, PaymentDate = ?, PaymentMethod = ?, BookingID = ? WHERE PaymentID = ?;";
    private static final String SELECT_PAYMENT_SUMMARY = 
    	    "SELECT p.*, c.Name, b.CheckInDate, b.CheckOutDate " +
    	    "FROM payment p " +
    	    "JOIN booking b ON p.BookingID = b.BookingID " +
    	    "JOIN customer c ON b.CustomerID = c.CustomerID " +
    	    "WHERE p.PaymentID = ?;";
    
    
    
    protected Connection getConnection() {
        // Updated to use your existing ConnectionManager
        return ConnectionManager.getConnection();
    }

    // Create Payment
    public void insertPayment(Payment payment) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAYMENT_SQL)) {
            preparedStatement.setDouble(1, payment.getAmount());
            preparedStatement.setDate(2, payment.getPaymentDate());
            preparedStatement.setString(3, payment.getPaymentMethod());
            preparedStatement.setInt(4, payment.getBookingID());
            preparedStatement.executeUpdate();
        }
    }

    public void printPaymentSummary(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYMENT_SUMMARY)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Data from Payment table
                double amount = rs.getDouble("Amount");
                int bookingID = rs.getInt("BookingID");
                
                // Data from Joined tables
                String customerName = rs.getString("Name");
                Date checkIn = rs.getDate("CheckInDate");
                Date checkOut = rs.getDate("CheckOutDate");
                
                // Logic for Total Stay (Manual calculation or SQL DATEDIFF)
                long diff = checkOut.getTime() - checkIn.getTime();
                long days = diff / (1000 * 60 * 60 * 24);

                // Now you can pass these to your JSP or UI
                System.out.println("Customer: " + customerName);
                System.out.println("Booking ID: " + bookingID);
                System.out.println("Total Amount: RM " + amount);
                System.out.println("Stay Duration: " + days + " Night(s)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read All Payments
    public List<Payment> selectAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PAYMENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("PaymentID");
                double amount = rs.getDouble("Amount");
                Date date = rs.getDate("PaymentDate");
                String method = rs.getString("PaymentMethod");
                int bookingID = rs.getInt("BookingID");
                
                // Pass the variables into the constructor!
                payments.add(new Payment(id, amount, date, method, bookingID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
    
    
    

    // Update Payment
    public boolean updatePayment(Payment payment) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PAYMENT_SQL)) {
            statement.setDouble(1, payment.getAmount());
            statement.setDate(2, payment.getPaymentDate());
            statement.setString(3, payment.getPaymentMethod());
            statement.setInt(4, payment.getBookingID());
            statement.setInt(5, payment.getPaymentID());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Delete Payment
    public boolean deletePayment(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PAYMENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}