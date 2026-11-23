package hotel;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 1. IMPORT YOUR LOGIN CLASSES
import login.User;
import login.UserDAO;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        
        if (!password.equals(confirmPassword)) {
            out.println("<script>alert('Passwords do not match!'); window.location.href='register.html';</script>");
            return;
        }

        // 4. PREPARE USER OBJECT
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        try {
           
            int result = UserDAO.register(user); //

            if (result > 0) {
                // Success: Alert and redirect to login
                out.println("<script>");
                out.println("alert('Registration Successful! Please Login.');");
                out.println("window.location.href='login.jsp';");
                out.println("</script>");
            
            } else if (result == -1) {
                
                out.println("<script>");
                out.println("alert('This email is already registered!');");
                out.println("window.location.href='register.html';");
                out.println("</script>");
            
            } else {
                
                out.println("<script>");
                out.println("alert('Database error occurred. Please try again.');");
                out.println("window.location.href='register.html';");
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('System Error: " + e.getMessage() + "'); window.location.href='register.html';</script>");
        }
    }
}