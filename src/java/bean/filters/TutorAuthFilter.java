package bean.filters;

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
@WebFilter("/tutor/*")
public class TutorAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //Get request and session objects
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        //Check if logged in
        boolean isLoggedIn = (session != null && session.getAttribute("tutor") != null);
        
        //Check other user types not logged in
        boolean loggedInStudentAdmin = ((session.getAttribute("student") != null) || (session.getAttribute("admin") != null));
        String loginURI = httpRequest.getContextPath() + "/tutorlogin";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("tutorlogin.jsp");

        // Forwards to tutor home if already logged in
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/tutor/account.jsp");
            dispatcher.forward(request, response);

            // Send to requested page as logged in user if logged in
        } else if (isLoggedIn || isLoginRequest) {
            chain.doFilter(request, response);

            // Forward to login page if not logged in
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("../tutorlogin.jsp");
            if(loggedInStudentAdmin){
                request.setAttribute("error", "You are already logged in.");
            }
            request.setAttribute("error", "Please log in to continue");
            dispatcher.forward(request, response);
        }

    }
}
