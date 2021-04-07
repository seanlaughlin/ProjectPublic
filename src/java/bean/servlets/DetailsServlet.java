/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import bean.Student;
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
import java.text.ParseException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class DetailsServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException, ParseException {
        try (out) {

            UserManager userManager = new UserManager();
            HttpSession session = request.getSession();
            Enumeration e1 = request.getParameterNames();
            String parameterName;
            Enumeration e2 = session.getAttributeNames();
            String userType = "";
            int userId = 0;
            Student student = null;
            String message = "";
            boolean adminRequest = session.getAttribute("admin") != null;

            parameterName = (String) e1.nextElement();
            String parameter = request.getParameter(parameterName);
            while (e2.hasMoreElements()) {
                String sessionAttribute = (String) e2.nextElement();
                switch (sessionAttribute) {

                    case "student":
                        student = (Student) session.getAttribute(sessionAttribute);
                        student = userManager.updateAttribute(parameterName, parameter, student);
                        response.getWriter().write("Success");
                        break;

                    case "tutor":
                        Tutor tutor = (Tutor) session.getAttribute(sessionAttribute);
                        tutor = userManager.updateAttribute(parameterName, parameter, tutor);
                        response.getWriter().write("Success");
                        break;

                    case "selectedStudent":

                        if (adminRequest) {
                            student = (Student) session.getAttribute(sessionAttribute);
                            student = userManager.updateAttribute(parameterName, parameter, student);
                            response.getWriter().write("Success");
                        }
                        break;

                    case "selectedTutor":
                        if (adminRequest) {
                            tutor = (Tutor) session.getAttribute(sessionAttribute);
                            tutor = userManager.updateAttribute(parameterName, parameter, tutor);
                            response.getWriter().write("Success");
                        }
                        break;

                    default:
                        break;
                }
            }
            //Output message to confirm operation has been completed (read by toggle.js)

        } catch (Exception e) {
            response.getWriter().write("Error");
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
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(DetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(DetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
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