package bean.servlets.admin;

import bean.CourseManager;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class LessonRegister extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, ClassNotFoundException {
        
        CourseManager cm = new CourseManager();
        RequestDispatcher rd;
        String message = null;
        String error = null;
        
        //Get Lesson details from request
        int courseId = Integer.parseInt(request.getParameter("courseid"));
        String lessonDate = request.getParameter("lessondate");
        String lessonTime = request.getParameter("lessontime");
        
        //Convert into Timestamp via Date as SDF doesn't support Timestamp
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lessonDate+" "+ lessonTime);
        Timestamp ts = new Timestamp(date.getTime());
        
        //Call method to add Lesson to DB and store returned result to check success
        boolean isAdded = cm.addCourseLesson(courseId, ts);
        
        //Set message to be displayed depending on if added successfully
        if(isAdded){
            message = "Lesson added successfully for Course #" + courseId;
            request.setAttribute("message", message);
        }
        else{
            error = "Unable to add lesson. Please check details and try again.";
            request.setAttribute("error", error);
        }
        
        //Direct back to add Lesson (in case further lessons to be added) and message will be displayed
        rd = request.getRequestDispatcher("addlesson.jsp");
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
        } catch (ParseException | ClassNotFoundException ex) {
            Logger.getLogger(LessonRegister.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException | ClassNotFoundException ex) {
            Logger.getLogger(LessonRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Allows Admin to add Lessons to existing Courses.";
    }// </editor-fold>

}
