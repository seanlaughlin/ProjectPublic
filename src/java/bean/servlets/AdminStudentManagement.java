/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets.admin;

import bean.Course;
import bean.Student;
import bean.UserManager;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class AdminStudentManagement extends HttpServlet {

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
        int studentId;
        RequestDispatcher rd = null;

        UserManager um = new UserManager();
        ArrayList<Student> allStudents = um.loadAllStudents();
        boolean isList = Boolean.parseBoolean(request.getParameter("isList"));

        String action = request.getParameter("action");
        if (action == null) {
            action = "display";
        }

        //Direct user to relevant page/perform requested action depending on action parameter
        switch (action) {

            //Stores Student object in session to be used by StudentDetailsServlet and directs to edittutor page (Which uses StudentDetailsServlet)
            case ("edit"):
                studentId = Integer.parseInt(request.getParameter("studentId"));
                Student selected = null;
                for (Student student : allStudents) {
                    if (studentId == student.getStudentId()) {
                        selected = student;
                    }
                }
                rd = request.getRequestDispatcher("editstudent.jsp");
                
                //If Tutor or Course were previously being edited, removes object from session, adds Student object for use by DetailsServlet
                session.removeAttribute("selectedTutor");
                session.removeAttribute("selectedCourse");
                
                session.setAttribute("selectedStudent", selected);
                rd.forward(request, response);
                break;

            //Stores studentId in request to be used by LessonsServlet and directs to schedule (which includes LessonsServlet as import)
            case ("schedule"):
                selected = null;
                studentId = Integer.parseInt(request.getParameter("studentId"));
                for (Student student : allStudents) {
                    if (studentId == student.getStudentId()) {
                        student = selected;
                    }
                }
                rd = request.getRequestDispatcher("schedule.jsp");
                request.setAttribute("pageTitle", "Student");
                request.setAttribute("returnURL", "studentmanagement.jsp");
                request.setAttribute("studentId", studentId);
                rd.forward(request, response);
                break;

            //Prints table of all Tutors 
            case ("display"):

                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<table class=\"lessonstable\">");
                    out.println("<tr>");
                    out.println("<th>Student ID</th>");
                    out.println("<th>Name</th>");
                    out.println("<th>DOB</th>");
                    out.println("<th>Email Address</th>");
                    out.println("<th>Phone No.</th>");
                    out.println("<th>Registered Courses</th>");
                    out.println("<th>Course Status</th>");
                    if (!isList) {
                        out.println("<th></th>");
                        out.println("<th></th>");
                        out.println("<th></th>");
                        out.println("</tr>");
                    }

                    //For each Student object in array of all Students output table row
                    for (Student student : allStudents) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String studentDob = sdf.format(student.getDob());
                        ArrayList<Course> studentCourses = student.getCourse();

                        out.println("<tr>");
                        out.format("<td>%s</td>", student.getStudentId());
                        out.format("<td>%1$s %2$s</td>", student.getFirstName(), student.getLastName());
                        out.format("<td>%s</td>", studentDob);
                        out.format("<td>%s</td>", student.getEmail());
                        out.format("<td>%s</td>", student.getPhoneNumber());
                        switch (studentCourses.size()) {
                            case 0:
                                out.format("<td><i>No registered courses</i></td>");
                                out.println("<td></td>");
                                break;

                            case 1:
                                out.format("<td>%s</td>", studentCourses.get(0).getCourseName());
                                out.format("<td>%s</td>", studentCourses.get(0).getCourseStatus());
                                break;

                            default:
                                out.println("<td>");
                                for (Course course : studentCourses) {
                                    out.format("%s<br>", course.getCourseName());
                                }
                                out.println("</td>");
                                out.println("<td>");
                                for (Course course : studentCourses) {
                                    out.format("%s<br>", course.getCourseStatus());
                                }
                                out.println("</td>");
                                break;
                        }

                        //If the request is not for a simple list of details only and interaction with data is required, display links
                        if (!isList) {
                            out.format("<td><small><a href=\"studentmanagement?action=schedule&studentId=%d\">View Schedule</a></small></td>", student.getStudentId());
                            out.format("<td><small><a href=\"studentmanagement?action=edit&studentId=%d\">Edit</a></small></td>", student.getStudentId());
                            out.format("<td><small><a href=\"delete?studentId=%d\" style=\"color: red\">Delete</a></small></td>", student.getStudentId());
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminStudentManagement.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdminStudentManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Admin functions relating to Student accounts. Viewing all registered Students, Student schedule, editing details.";
    }// </editor-fold>

}
