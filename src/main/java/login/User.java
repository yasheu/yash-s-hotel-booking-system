package login;

public class User {

    private int userId;
    private String email;
    private String password;
    
    
    private String fullname;
    private String phone;
    
    private boolean loggedIn = false;
    
    public User() {
        super();
    }

    public User(int userId, String email, String password, boolean loggedIn) {
        super();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.loggedIn = loggedIn;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // 2. ADDED GETTERS AND SETTERS FOR FULLNAME
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // 3. ADDED GETTERS AND SETTERS FOR PHONE
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}