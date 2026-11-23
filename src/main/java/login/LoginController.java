package login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            
            if ("admin@gmail.com".equals(email) && "admin".equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("email", email);
                session.setAttribute("role", "admin"); 

              
                response.sendRedirect("admin_homepage.html"); 
                return;
            }
            // -----------------------------------------------------------

            
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            
            
            user = UserDAO.login(user);
            
            if (user.isLoggedIn()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("email", user.getEmail());
                
              
                session.setAttribute("fullname", user.getFullname()); 
                session.setAttribute("phone", user.getPhone());
                session.setAttribute("role", "user");

                
                response.sendRedirect("Homepage.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}