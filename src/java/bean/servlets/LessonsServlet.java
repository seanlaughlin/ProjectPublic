package bean.servlets;

import bean.Admin;
import bean.Course;
import bean.CourseManager;
import bean.Lesson;
import bean.Student;
import bean.Tutor;
import bean.User;
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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seanl
 */
public class LessonsServlet extends HttpServlet {

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
        ArrayList<Course> courses = new ArrayList();
        Student student = null;
        Admin admin = null;
        Tutor tutor = null;
        boolean courseEdit = false;
        String userType = "";
        CourseManager cm = new CourseManager();
        
        //Checks which user type is logged in by iterating through names of attributes stored in session object to determine how request should be processed
        Enumeration e1 = session.getAttributeNames();
        while (e1.hasMoreElements()) {
            String sessionAttribute = (String) e1.nextElement();
            
            switch (sessionAttribute) {

                case "student":
                    userType = "student";
                    student = (Student) session.getAttribute(userType);
                    courses = student.getCourse();
                    break;

                case "tutor":
                    userType = "tutor";
                    tutor = (Tutor) session.getAttribute(userType);
                    courses = cm.loadTutorCourses(tutor.getTutorId());
                    break;

                case "admin":
                    userType = "admin";
                    admin = (Admin) session.getAttribute(userType);
                    Enumeration e2 = request.getParameterNames();
                    int id = 0;
                    
                    //Gets data passed from admin request and loads course depending on data type
                    while (e2.hasMoreElements() && id == 0) {
                        String requestParam = (String) e2.nextElement();
                        switch (requestParam) {

                            case "tutorId":
                                id = Integer.parseInt(request.getParameter(requestParam));
                                courses = cm.loadTutorCourses(id);
                                break;

                            case "studentId":
                                id = Integer.parseInt(request.getParameter(requestParam));
                                courses = cm.loadStudentCourses(id);
                                break;

                            case "courseId":
                                id = Integer.parseInt(request.getParameter(requestParam));
                                Course course = cm.loadCourse(id);
                                courses.add(course);
                                courseEdit = true;
                                break;
                        }
                    }
                    break;
            }
        }

        try {

            //Calendar object with todays date
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                //Display message if no Courses
                if (courses.isEmpty()) {
                    out.println("<p>There are no Courses to display.</p>");
                }

                //Generate table with row for each Course in the array
                for (Course course : courses) {

                    ArrayList<Lesson> lessons = course.getLessons();
                    String courseStatus = course.getCourseStatus();
                    String courseName = course.getCourseName();
                    int courseId = course.getCourseId();
                    int numberOfLessons = course.getLessons().size();
                    
                    
                    out.println("<table class=\"lessonstable\">");
                    out.println("<tr>");

                    //If Course is being displayed by admin in Course Management, there will be extra column in table so header is 1 more column wide
                    if (courseEdit) {
                        out.format("<th colspan=\"5\">%s</th>", courseName);
                    } else {
                        out.format("<th colspan=\"4\">%s</th>", courseName);
                    }
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<th>Date</th>");
                    out.println("<th>Time</th>");
                    out.println("<th>Lesson no.</th>");
                    out.println("<th>Tutor</th>");

                    //If course is being displayed by Admin in Course Management, create new header cell for delete button column
                    if (courseEdit) {
                        out.println("<th></th>");
                    }
                    out.println("</tr>");

                    //Generate rows and populate with each Lessons info from Courses Lesson ArrayList
                    for (Lesson lesson : lessons) {

                        //Get Lesson date and time and format as Strings
                        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                        String time = sdf1.format(lesson.getTimeSlot().getTime());
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                        String lessonDate = sdf2.format(lesson.getTimeSlot());

                        int lessonNumber = lessons.indexOf(lesson) + 1;
                        int lessonId = lesson.getLessonId();
                        String tutorFullName = course.getCourseTutor().getFirstName() + " " + course.getCourseTutor().getLastName();

                        out.println("<tr>");
                        out.println("<td>" + lessonDate + "</td>");
                        out.println("<td>" + time + "</td>");
                        out.format("<td>%d</td>", lessonNumber);
                        out.format("<td>%s</td>", tutorFullName);

                        //If course is being displayed by Admin in Course Management, add cell with delete option
                        if (courseEdit) {
                            out.format("<td><small><a href=\"delete?lessonId=%s\" style=\"color:red\">Delete</a></small></td>", lessonId);
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");

                    //Check if Student logged in
                    if (userType.equals("student")) {

                        int studentId = student.getStudentId();
                        
                        //Display Course Status with different font colours depending on status
                        String statusColour = course.getStatusColour();

                        out.println("Course status: ");
                        out.format("<span style=\"color: %1$s\">%2$s</span>", statusColour, courseStatus);

                        //Display option to end Lessons if Course has yet to begin or is currently underway. Link calls the EndLessonsServlet and sends the servlet the course ID.
                        if (course.getCourseStatus().equals("On-Going") || course.getCourseStatus().equals("Beginner")) {
                            out.format("<a href=\"%1$s/EndLessonsServlet?courseId=%2$d&studentId=%3$d\" class=\"bottomlink\" style=\"color: red\">End Lessons</a>", request.getContextPath(), courseId, studentId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            response.sendRedirect("admin/account.jsp");
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
            Logger.getLogger(LessonsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LessonsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Used by Student, Tutor and Admin to display Lessons for a Student/Tutor";
    }// </editor-fold>

}
