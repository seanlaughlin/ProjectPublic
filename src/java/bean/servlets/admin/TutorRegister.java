/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets.admin;

import bean.PassHash;
import bean.Tutor;
import bean.UserManager;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author seanl
 */
public class TutorRegister extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserManager userManager = new UserManager();
        PassHash passHash = new PassHash();
        RequestDispatcher rd;

        try {

            //Read variables in from web form request and trim any whitespace
            String email = request.getParameter("email").trim();
            String password = passHash.hashPassword(request.getParameter("password"));
            String firstName = request.getParameter("fname").trim();
            String lastName = request.getParameter("surname").trim();
            String role = request.getParameter("role").trim();
            String sDob = request.getParameter("dob");
            String department = request.getParameter("department");
            int payGrade = Integer.parseInt(request.getParameter("paygrade"));

            //Convert sDob string from request to Date object
            Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(sDob);

            //Create new student object with details read in
            Tutor tutor = new Tutor(email, password, firstName, lastName, dob, role, department, payGrade);

            //Register student in database, if successful will return studentId which is > 0
            int i = userManager.registerTutor(tutor);

            //Send error message if registration was unsuccessful
            if (i == 0) {
                rd = request.getRequestDispatcher("registertutor.jsp");
                request.setAttribute("error", "Registration error.");
                rd.forward(request, response);
            } //Return tutorId in request and redirect to page confirming registration
            else {
                
                //Variables for dynamic confirmation page
                String message = "Tutor registered successfully.";
                String returnURL = "tutormanagement.jsp";
                String pageTitle = "Register Tutor";
                
                //Set variables in request and forward to confirmation page
                rd = request.getRequestDispatcher("message.jsp");
                request.setAttribute("returnURL", returnURL);
                request.setAttribute("pageTitle", pageTitle);
                request.setAttribute("message", message);
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            rd = request.getRequestDispatcher("registertutor.jsp");
            request.setAttribute("error", "Registration error.");
            rd.forward(request, response);
        }

        //Send error message if exception occurs
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
