package login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAO {

    
    private static final String INSERT_USERS = "INSERT INTO users (email, password) VALUES (?, ?)";
    private static final String INSERT_CUSTOMER = "INSERT INTO customer (Name, CustomerPhoneNum, userid) VALUES (?, ?, ?)";
    
    private static final String SELECT_USER_LOGIN = 
        "SELECT u.userid, u.email, u.password, c.Name, c.CustomerPhoneNum " + 
        "FROM users u " + 
        "INNER JOIN customer c ON u.userid = c.userid " + 
        "WHERE u.email = ? AND u.password = ?";

    
    public static User login(User user) throws SQLException, NoSuchAlgorithmException {
        // .trim() is added to handle accidental spaces which change the MD5 hash
        String hashedPassword = hashPassword(user.getPassword().trim());
        
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_LOGIN)) {
            
            ps.setString(1, user.getEmail().trim());
            ps.setString(2, hashedPassword);
            
            System.out.println("Attempting Login for: " + user.getEmail());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("userid"));
                    user.setEmail(rs.getString("email"));
                    user.setFullname(rs.getString("Name")); 
                    user.setPhone(rs.getString("CustomerPhoneNum"));
                    user.setLoggedIn(true);
                } else {
                    user.setLoggedIn(false);
                    System.out.println("Login failed: Hash mismatch or user does not exist.");
                }
            }
        }
        return user;
    }

    public static int register(User user) throws SQLException, NoSuchAlgorithmException {
        int status = 0;
        String hashedPassword = hashPassword(user.getPassword().trim());

        try (Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false); // Start Transaction

            try (PreparedStatement psUser = con.prepareStatement(INSERT_USERS, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, user.getEmail().trim());
                psUser.setString(2, hashedPassword);
                
                if (psUser.executeUpdate() > 0) {
                    try (ResultSet rsGenerated = psUser.getGeneratedKeys()) {
                        if (rsGenerated.next()) {
                            int newUserId = rsGenerated.getInt(1);
                            
                            try (PreparedStatement psCust = con.prepareStatement(INSERT_CUSTOMER)) {
                                psCust.setString(1, user.getFullname());
                                // Removes non-numeric characters from phone
                                String cleanPhone = user.getPhone().replaceAll("[^0-9]", "");
                                psCust.setString(2, cleanPhone); 
                                psCust.setInt(3, newUserId);
                                
                                psCust.executeUpdate();
                                con.commit(); // Save both tables
                                status = 1;
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                con.rollback(); // Undo if either table fails
                throw e;
            }
        }
        return status;
    }
    
    
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
       
        md.update(password.getBytes(java.nio.charset.StandardCharsets.UTF_8)); 
        byte[] byteData = md.digest();
        
        StringBuilder sb = new StringBuilder();
        for (byte b : byteData) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}