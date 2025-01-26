

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.UserService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sozcu
 */



@WebFilter("*.xhtml")
public class AuthFilter implements Filter {
     @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login.xhtml";
        String adminURI = request.getContextPath() + "/admin.xhtml";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean isAdminPage = request.getRequestURI().equals(adminURI);

        if (loggedIn) {
            User user = (User) session.getAttribute("user");
            if (isAdminPage && !user.getRoles().contains("ADMIN")) {
                // If the user is not an admin, redirect to index.xhtml
                response.sendRedirect(request.getContextPath() + "/index.xhtml");
                return;
            }
            chain.doFilter(request, response); // Proceed to the requested page
        } else if (loginRequest) {
            chain.doFilter(request, response); // Allow access to login.xhtml
        } else {
            response.sendRedirect(loginURI); // Redirect to login page
        }
    }
}




