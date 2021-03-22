/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class EndLessonsServlet extends HttpServlet {

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
        
        //Get session object so session attributes such as student object can be read
        HttpSession session = request.getSession();
        CourseManager cm = new CourseManager();
        
        //Get student object from session
        Student student = (Student) session.getAttribute("student");
        Course studentCourse = new Course();
        
        //Get ArrayList of student courses
        ArrayList<Course> courses = student.getCourse();
        
        //Find course that has the course ID sent from the webpage request and store in studentCourse, update database and studentCourse object with "Not-complete" status
        for (Course course : courses) {
            if (course.getCourseId() == Integer.parseInt(request.getParameter("courseId"))) {
                studentCourse = course;
            }
            studentCourse = cm.updateAttribute("coursestatus", "Not-complete", studentCourse);
        }
        
        //Create ArrayList of student's current courses from DB, now that update has been completed
        ArrayList<Course> studentCourses = cm.loadStudentCourses(student.getStudentId());
        
        //Update student object with new courses list and update object in session attribute
        student.setCourse(studentCourses);
        session.setAttribute("student", student);
        
        //Send back to page (same as refreshing to reflect changes)
        response.sendRedirect("student/yourlessons.jsp");
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
            Logger.getLogger(EndLessonsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EndLessonsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
