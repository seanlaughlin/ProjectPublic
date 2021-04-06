/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import bean.Admin;
import bean.Student;
import bean.Tutor;
import bean.UserManager;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class TutorLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {

        UserManager userManager = new UserManager();
        RequestDispatcher dispatcher;

        //Read in variables from web form request
        String email = request.getParameter("emailaddress");
        String password = request.getParameter("password");

        //Create Tutor object and attempt to retrieve details 
        Tutor tutor = userManager.logInTutor(email, password);
        
        //Get session and get Student and Admin objects in case user already logged in
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Admin admin = (Admin) session.getAttribute("admin");

        //Create Tutor session variable and send to account.jsp if login successful, otherwise send error message
        if (tutor != null) {
            session.setAttribute("tutor", tutor);
            session.setAttribute("loggedIn", "true");
            response.sendRedirect("tutor/account.jsp");
        } else if (student != null || admin != null) {
            dispatcher = request.getRequestDispatcher("tutorlogin.jsp");
            request.setAttribute("error", "You are already logged in.");
            dispatcher.forward(request, response);
        } else {
            dispatcher = request.getRequestDispatcher("tutorlogin.jsp");
            request.setAttribute("error", "Invalid login details.");
            dispatcher.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TutorLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TutorLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login servlet used by Tutor.";
    }// </editor-fold>

}
