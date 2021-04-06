/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets.admin;

import bean.Course;
import bean.CourseManager;
import bean.Lesson;
import bean.Student;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author seanl
 */
public class AdminCourseManagement extends HttpServlet {

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

        int courseId;
        RequestDispatcher rd = null;
        HttpSession session = request.getSession();

        //Check if request was for a simple list of Courses (for reference) or not
        boolean isList = Boolean.parseBoolean(request.getParameter("isList"));

        CourseManager cm = new CourseManager();
        ArrayList<Course> allCourses = cm.loadAllCourses();

        //Get name of action requested from webpage, default to display if no action parameter
        String action = request.getParameter("action");
        if (action == null) {
            action = "display";
        }

        //Direct user to relevant page/perform requested action depending on action parameter
        switch (action) {

            //Stores Course object in session to be used by CourseDetailsServlet and directs to editcourse page (Which uses CourseDetailsServlet)
            case ("edit"):
                courseId = Integer.parseInt(request.getParameter("courseId"));
                Course selected = null;
                for (Course course : allCourses) {
                    if (courseId == course.getCourseId()) {
                        selected = course;
                    }
                }
                rd = request.getRequestDispatcher("editcourse.jsp");
                session.setAttribute("selectedCourse", selected);
                rd.forward(request, response);
                break;

            //Prints table of all Tutors 
            case ("display"):
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    if (allCourses == null) {
                        out.println("There are no courses to display.");
                    } else {
                        out.println("<table class=\"lessonstable\">");
                        out.println("<tr>");
                        out.println("<th>Course ID</th>");
                        out.println("<th>Course Name</th>");
                        out.println("<th>Course Status</th>");
                        out.println("<th>Tutor</th>");
                        out.println("<th>Students</th>");
                        out.println("<th>Lessons</th>");
                        out.println("<th>Starts</th>");
                        out.println("<th>Ends</th>");
                        if (!isList) {
                            out.println("<th></th>");
                            out.println("<th></th>");
                            out.println("<th></th>");
                            out.println("<th></th>");
                        }
                        out.println("</tr>");
                        
                        //For each Course object in array of all Courses, output table row
                        for (Course course : allCourses) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            ArrayList<Student> courseStudents = cm.loadCourseStudents(course.getCourseId());

                            out.println("<tr>");
                            out.format("<td>%s</td>", course.getCourseId());
                            out.format("<td>%s</td>", course.getCourseName());
                            out.format("<td>%s</td>", course.getCourseStatus());
                            out.format("<td>%1$s %2$s</td>", course.getCourseTutor().getFirstName(), course.getCourseTutor().getLastName());
                            out.format("<td>%s</td>", courseStudents.size());
                            out.format("<td>%s</td>", course.getLessons().size());
                            if (!(course.getLessons().isEmpty())) {

                                //Store all lesson times in array and sort to find first and last lesson date
                                ArrayList<Lesson> lessons = course.getLessons();
                                ArrayList<Timestamp> ts = new ArrayList();
                                for (Lesson lesson : lessons) {
                                    Timestamp timestamp = lesson.getTimeSlot();
                                    ts.add(timestamp);
                                }
                                Collections.sort(ts);
                                String startDate = sdf.format(ts.get(0));
                                String endDate = sdf.format(ts.get(ts.size() - 1));
                                out.format("<td>%s</td>", startDate);
                                out.format("<td>%s</td>", endDate);
                            } else {
                                out.format("<td></td>");
                                out.format("<td></td>");
                            }
                            
                            //If the request is not for a simple list of details only and interaction with data is required, display links
                            if (!isList) {
                                out.format("<td><small><a href=\"viewcoursestudents.jsp?courseId=%s\">View Students</small></td>", course.getCourseId());
                                out.println("<td><small><a href=\"addlesson.jsp\">Add Lesson</small></td>");
                                out.format("<td><small><a href=\"coursemanagement?courseId=%s&action=edit\">Edit Course</small></td>", course.getCourseId());
                                out.format("<td><small><a href=\"deletecourse?courseId=%s\" style=\"color:red\">Delete</small></td>", course.getCourseId());
                            }

                        }
                        out.println("</table>");
                    }
                }
                break;
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException,
            IOException {
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException,
            IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Admin functions relating to Courses. Viewing all registered Courses, editing details.";
    }// </editor-fold>

}
