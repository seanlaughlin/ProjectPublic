/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author seanl
 */
@WebFilter("/student/*")
public class StudentAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //Get request and session objects
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        //Check if logged in
        boolean isLoggedIn = (session != null && session.getAttribute("student") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        // Forwards to student home if already logged in
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/student/account.jsp");
            dispatcher.forward(request, response);

            // Send to page as logged in user if logged in
        } else if (isLoggedIn || isLoginRequest) {
            chain.doFilter(request, response);

            // Forward to login page if not logged in
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("../login.jsp");
            request.setAttribute("error", "Please log in to continue");
            dispatcher.forward(request, response);
        }

    }
}
