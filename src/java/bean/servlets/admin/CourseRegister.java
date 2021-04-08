package bean.servlets.admin;

import bean.CourseManager;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class CourseRegister extends HttpServlet {

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
        CourseManager cm = new CourseManager();
        RequestDispatcher rd;

        //Read parameters from web form request and trim any whitespace
        String coursename = request.getParameter("coursename").trim();
        int tutorid = Integer.parseInt(request.getParameter("tutorid").trim());
        String description = request.getParameter("description").trim();
        String courseStatus = "Upcoming";

        //Attempt to add course and get courseId from method return
        int courseId = cm.addCourse(coursename, courseStatus, tutorid, description);

        //Send error message if registration was unsuccessful
        if (courseId == 0) {
            rd = request.getRequestDispatcher("registercourse.jsp");
            request.setAttribute("error", "Registration error.");
            rd.forward(request, response);

        } //Return courseId in request message and direct to page to add Course Lessons
        else {
            String message;
            message = "Course registered successfully. Course ID: " + courseId;
            rd = request.getRequestDispatcher("addlesson.jsp");
            request.setAttribute("message", message);
            rd.forward(request, response);
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
            Logger.getLogger(CourseRegister.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CourseRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used by Admin to register Course and directs to page to add Course Lessons";
    }// </editor-fold>

}
