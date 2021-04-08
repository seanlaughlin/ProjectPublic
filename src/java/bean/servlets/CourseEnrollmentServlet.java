/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import bean.Course;
import bean.CourseManager;
import bean.Student;
import jakarta.servlet.RequestDispatcher;
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

        HttpSession session = request.getSession();
        CourseManager cm = new CourseManager();

        //Initialise variables for dynamic confirmation page
        String message = null;
        String pageTitle = null;
        String returnURL = null;

        //Get student object from session
        Student student = (Student) session.getAttribute("student");

        RequestDispatcher rd;

        try {

            //Check if student logged in
            if (student != null) {

                //Get courseid from courses page request and studentid from student object
                int courseId = Integer.parseInt(request.getParameter("courseid"));
                int studentId = student.getStudentId();

                //Get array of student courses to check if already registered
                ArrayList<Course> courses = student.getCourse();
                boolean isRegistered = false;

                //Set isAlreadyRegistered to true if course that matches request courseid is found
                for (Course course : courses) {
                    if (course.getCourseId() == courseId) {
                        isRegistered = true;
                    }
                }

                //Redirect with message advising already registered
                if (isRegistered) {
                    rd = request.getRequestDispatcher("student/enroll.jsp");
                    request.setAttribute("enrollment", "You are already enrolled on this course.");
                    rd.forward(request, response);
                } else {

                    //Update enrollments table in database, returns true if successful
                    boolean enrolled = cm.enrollStudent(studentId, courseId);

                    //Update course array in student object and store in session, redirect to page confirming success
                    if (enrolled) {
                        ArrayList<Course> updatedCourses = cm.loadStudentCourses(studentId);
                        student.setCourse(updatedCourses);
                        session.setAttribute("student", student);
                        request.setAttribute("enrollment", "Enrolled successfully.");

                    } //Redirect to page confirming failure to enroll
                    else {
                        request.setAttribute("enrollment", "An error occurred. Please contact support.");
                    }
                    rd = request.getRequestDispatcher("student/enroll.jsp");
                    rd.forward(request, response);
                }
            } //Checks if admin is logged in and has requested unEnroll of student
            else if (session.getAttribute("admin") != null && Boolean.parseBoolean(request.getParameter("unEnroll"))) {

                //Get studentId and courseId from request
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                int courseId = Integer.parseInt(request.getParameter("courseId"));

                pageTitle = "Unenroll";
                returnURL = "admin/viewcoursestudents.jsp?courseId=" + courseId;

                //true if unenrolled successfully, set message depending on success and forward to page advising user
                boolean isDeleted = cm.unenrollStudent(studentId, courseId);

                if (isDeleted) {
                    message = "Student unenrolled successfully.";
                    request.setAttribute("courseId", courseId);
                } else {
                    message = "Unable to remove enrollment. It may have already been removed from the database.";
                }
                
                //Forward user to page confirming action with message
                rd = request.getRequestDispatcher("admin/message.jsp");
                request.setAttribute("pageTitle", pageTitle);
                request.setAttribute("returnURL", returnURL);
                request.setAttribute("message", message);
                rd.forward(request, response);
            } //Checks if admin logged in and enrolls student (if not unenroll request will be enroll request)
            else if (session.getAttribute("admin") != null) {
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                int courseId = Integer.parseInt(request.getParameter("courseId"));

                //True if enrolled successfully, set message depending on success and forward to page advising user
                boolean enrolled = cm.enrollStudent(studentId, courseId);
                returnURL = "admin/viewcoursestudents.jsp?courseId=" + courseId;
                pageTitle = "Enrolment";
                if (enrolled) { 
                    message = "Enrolled successfully.";
                } else {
                    message = "Enrollment failed. Please check the information and try again.";
                }
                
                //Forward user to page confirming action with message
                rd = request.getRequestDispatcher("admin/message.jsp");
                request.setAttribute("pageTitle", pageTitle);
                request.setAttribute("returnURL", returnURL);
                request.setAttribute("message", message);
                rd.forward(request, response);
            } //Send to login with message if not logged in
            else {
                rd = request.getRequestDispatcher("login.jsp");
                request.setAttribute("error", "Please log in to continue");
                rd.forward(request, response);
            }
        } catch (NumberFormatException e) {
            rd = request.getRequestDispatcher("login.jsp");
            request.setAttribute("error", "Please log in to continue");
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
        return "Used by Admin to enroll Students on Courses, and by Students to enroll themselves on Courses.";
    }// </editor-fold>

}
