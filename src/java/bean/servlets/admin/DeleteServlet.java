/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets.admin;

import bean.CourseManager;
import bean.UserManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class DeleteServlet extends HttpServlet {

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

        Enumeration enumeration = request.getParameterNames();
        String parameterName = "";
        CourseManager cm = new CourseManager();
        UserManager um = new UserManager();
        String deletedType = "";
        String returnLinkKey = "";
        String message = "";
        boolean isDeleted = false;
        RequestDispatcher rd;
        int id = 0;

        while (enumeration.hasMoreElements()) {
            parameterName = (String) enumeration.nextElement();
        }

        id = Integer.parseInt(request.getParameter(parameterName));

        switch (parameterName) {

            case "courseId":
                deletedType = "Course";
                isDeleted = cm.deleteCourse(id);
                returnLinkKey = deletedType.toLowerCase();
                break;

            case "lessonId":
                deletedType = "Lesson";
                isDeleted = cm.deleteLesson(id);
                returnLinkKey = "course";
                break;

            case "studentId":
                deletedType = "Student";
                isDeleted = um.deleteUser(deletedType, id);
                returnLinkKey = deletedType.toLowerCase();
                break;

            case "tutorId":
                deletedType = "Tutor";
                isDeleted = um.deleteUser(deletedType, id);
                returnLinkKey = deletedType.toLowerCase();
                break;

            default:
                deletedType = "object";
                isDeleted = false;
                returnLinkKey = "student";
        }
        if (isDeleted) {
            message = "The " + deletedType + " has been deleted successfully.";
        } else {
            message = "Unable to delete." + deletedType + " may not exist or may have already been deleted.";
        }

        //Check if deleted and set message
        //Put message in request and direct to page displaying message indicating success or failure
        rd = request.getRequestDispatcher("deleted.jsp");
        request.setAttribute("attribute", deletedType);
        request.setAttribute("returnLinkKey", returnLinkKey);
        request.setAttribute("message", message);
        rd.forward(request, response);
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
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used by Admin to delete Lessons.";
    }// </editor-fold>

}
