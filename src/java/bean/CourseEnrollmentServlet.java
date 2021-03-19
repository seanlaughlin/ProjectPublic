/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.io.PrintWriter;
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
public class CourseEnrollmentServlet extends HttpServlet {

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

        //Check that user is logged in by checking student object exists, redirect to login if not logged in
        if (session.getAttribute("student") != null) {
            CourseManager cm = new CourseManager();

            //Get student object from session
            Student student = (Student) session.getAttribute("student");

            //Get courseid from courses page request and studentid from student object
            int courseId = Integer.parseInt(request.getParameter("courseid"));
            int studentId = student.getStudentId();

            //Get array of student courses to check if already registered
            ArrayList<Course> courses = student.getCourse();
            boolean isAlreadyRegistered = false;

            //Set isAlreadyRegistered to true if course that matches request courseid is found
            for (Course course : courses) {
                if (course.getCourseId() == courseId) {
                    isAlreadyRegistered = true;
                }
            }

            //Redirect with message advising already registered
            if (isAlreadyRegistered) {
                response.sendRedirect("enroll.jsp?enrollment=registered");
            } 
            else {

                //Update enrollments table in database, returns true if successful
                boolean enrolled = cm.enrollStudent(studentId, courseId);

                //Update course array in student object and store in session, redirect to page confirming success
                if (enrolled) {
                    ArrayList<Course> updatedCourses = cm.loadStudentCourses(studentId);
                    student.setCourse(updatedCourses);
                    session.setAttribute("student", student);
                    response.sendRedirect("enroll.jsp?enrollment=true");
                } //Redirect to page confirming failure to enroll
                else {
                    response.sendRedirect("enroll.jsp?enrollment=false");
                }
            }
        } else {
            response.sendRedirect("login.jsp?error=register");
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
            Logger.getLogger(CourseEnrollmentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CourseEnrollmentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
