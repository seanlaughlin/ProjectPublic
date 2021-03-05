/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class StudentRegisterServlet extends HttpServlet {

    private UserManager userManager = new UserManager();
    private PassHash passHash = new PassHash();
   

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
        int i = 0;
        try {
            String email = request.getParameter("email");
            String password = passHash.hashPassword(request.getParameter("password"));
            String firstName = request.getParameter("fname");
            String lastName = request.getParameter("surname");
            String dateOfBirth = request.getParameter("dob");
            Date dob = new SimpleDateFormat("ddMMyyyy").parse(dateOfBirth);
            String phone = request.getParameter("contactno");
            Student student = new Student(email, password, firstName, lastName, dob, phone);
            i = userManager.registerStudent(student);
        } catch (ParseException ex) {
            Logger.getLogger(StudentRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            if(i == 0){
                HttpSession session = request.getSession(false);
                response.sendRedirect("register.jsp?registerError=true");
            }
            else{
                HttpSession session = request.getSession(false);
                request.setAttribute("studentid", i);
                RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
                rd.forward(request, response);
//                response.sendRedirect("dashboard.jsp");
            }
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
