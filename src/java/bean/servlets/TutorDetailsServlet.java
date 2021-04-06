/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import bean.Tutor;
import bean.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class TutorDetailsServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try (out) {
            
            Tutor tutor;
            UserManager userManager = new UserManager();
            HttpSession session = request.getSession();
            
            //Check if Admin or Tutor is logged in and get Tutor object from session
            if(session.getAttribute("admin")!= null){
                tutor = (Tutor) session.getAttribute("selectedTutor");
            }
            else{
            tutor = (Tutor) session.getAttribute("tutor");
            }
            
            //Get the name of the parameter passed from web form
            Enumeration enumeration = request.getParameterNames();
            String parameterName;
            
            //Convert parameterName into String and get the parameter value from the web form request
            parameterName = (String) enumeration.nextElement();
            String parameter = request.getParameter(parameterName);
            
            //Send variables to updateAttribute method to update database and overwrite Tutor object with new values
            tutor = userManager.updateAttribute(parameterName, parameter, tutor);
            
            //Output message to confirm operation has been completed (read by toggle.js)
            response.getWriter().write("Success");
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
           Logger.getLogger(TutorDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(TutorDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used by Admin and Tutor to edit Student details from web form (data sent by AJAX)";
    }// </editor-fold>

}
