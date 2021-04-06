/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import bean.Course;
import bean.CourseManager;
import bean.Student;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author seanl
 */
public class CourseStudentsServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        //Get courseId from request and load students on course into ArrayList
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        CourseManager cm = new CourseManager();
        ArrayList<Student> courseStudents = cm.loadCourseStudents(courseId);

        //Get calendar object with todays date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        boolean hasCourseStarted;

        Course studentCourse = null;
        try (PrintWriter out = response.getWriter()) {
            out.println("<table class=\"lessonstable\">");
            out.println("<tr>");
            out.println("<th>Student ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Date of Birth</th>");
            out.println("<th>Email Address</th>");
            out.println("<th>Lessons Status</th>");
            out.println("<th></th>");
            out.println("<th></th>");
            out.println("</tr>");

            //Print new row for each student in ArrayList with student details
            for (Student student : courseStudents) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String studentDob = sdf.format(student.getDob());
                for (Course course : student.getCourse()) {
                    if (course.getCourseId() == courseId) {
                        studentCourse = course;
                    }
                }
                String courseStatus = studentCourse.getCourseStatus();
                out.println("<tr>");
                out.format("<td>%s</td>", student.getStudentId());
                out.format("<td>%1$s %2$s</td>", student.getFirstName(), student.getLastName());
                out.format("<td>%s</td>", studentDob);
                out.format("<td>%s</td>", student.getEmail());

                //Check if Course has started by comparing first Lesson date to object with todays date to see if before
                try {
                    hasCourseStarted = studentCourse.getLessons().get(0).getTimeSlot().before(today.getTime());
                } //Set to false if course has no Lessons (causes exception)
                catch (IndexOutOfBoundsException e) {
                    hasCourseStarted = false;
                }

                //Display different options to change course status and text colors depending on current Course status
                switch (courseStatus) {
                    case "Not-complete":
                        out.format("<td><span style=\"color: red\">%s</span></td>", courseStatus);
                        out.println("<td><small style=\"color: red\">Lessons Ended</small></td>");
                        break;

                    case "Completed":
                        out.format("<td><span style=\"color: green\">%s</span></td>", courseStatus);
                        out.println("<td><small style=\"color: green\">Course Completed</small></td>");
                        break;

                    case "On-Going":
                        out.format("<td><span style=\"color: orange\">%s</span></td>", courseStatus);
                        out.format("<td><small><ul style=\"list-style: none\"><li><a href=\"../EndLessonsServlet?courseId=%1$s&studentId=%2$s&lessonStatus=Completed\">Mark as Completed</a></small></li>", studentCourse.getCourseId(), student.getStudentId());
                        out.format("<small><li><a href=\"../EndLessonsServlet?courseId=%1$s&studentId=%2$s&lessonStatus=Not-complete\">End Lessons</a></small></li></ul></td>", studentCourse.getCourseId(), student.getStudentId());
                        break;

                    case "Beginner":
                        out.format("<td><span style=\"color: #0065BF\">%s</span></td>", courseStatus);
                        if (hasCourseStarted) {
                            out.format("<td><ul style=\"list-style: none\"><li><small><a href=\"../EndLessonsServlet?courseId=%1$s&studentId=%2$s&lessonStatus=On-Going\">Mark as On-Going</a></small></li>", studentCourse.getCourseId(), student.getStudentId());
                            out.format("<small><li><a href=\"../EndLessonsServlet?courseId=%1$s&studentId=%2$s&lessonStatus=Completed\">Mark as Completed</a></small></li>", studentCourse.getCourseId(), student.getStudentId());
                        } else {
                            out.println("<td><ul style=\"list-style:none\">");
                        }
                        out.format("<small><li><a href=\"../EndLessonsServlet?courseId=%1$s&studentId=%2$s&lessonStatus=Not-complete\">End Lessons</a></small></li></ul></td>", studentCourse.getCourseId(), student.getStudentId());
                        break;
                }
                out.format("<td><small><a href=\"" + request.getContextPath() + "/CourseEnrollmentServlet?studentId=%1$s&courseId=%2$s&unEnroll=true\" style=\"color:red\">Unenroll</a></small></td>", student.getStudentId(), studentCourse.getCourseId());
                out.println("</tr>");
            }
            out.println("</table>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used by Tutor and Admin to list Students on a specific Course.";
    }// </editor-fold>

}
