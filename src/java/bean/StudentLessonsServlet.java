package bean;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author seanl
 */
public class StudentLessonsServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        if(session.getAttribute("student")!=null){
        Student student = (Student) session.getAttribute("student");
        ArrayList<Course> courses = student.getCourse();
        try (PrintWriter out = response.getWriter()) {
            for (Course course : courses){
                ArrayList<Lesson> lessons = course.getLessons();
                out.println("<table class=\"lessonstable\">");
                out.println("<tr>");
                out.println("<th>Date</th>");
                out.println("<th>Time</th>");
                out.println("<th>Course</th>");
                out.println("<th>Lesson no.</th>");
                out.println("<th>Tutor</th>");
                out.println("<th>Course Status</th>");
                out.println("</tr>");
            for (Lesson lesson : lessons) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                String time = sdf1.format(lesson.getTimeSlot().getTime());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                String lessonDate = sdf2.format(lesson.getTimeSlot());
                out.println("<tr>");
                out.println("<td>"+lessonDate+"</td>");
                out.println("<td>"+time+"</td>");
                out.println("<td>"+course.getCourseName()+"</td>");
                out.println("<td>"+lesson.getLessonId()+"</td>");
                out.println("<td>"+course.getCourseTutor().getFirstName()+" "+ course.getCourseTutor().getLastName()+"</td>");
                out.println("<td>"+course.getCourseStatus()+"</td>");
                out.println("</tr>");
            }
                out.println("</table>");
            }
            out.println("<h3><a href=\"account.jsp\">&#x21a9; Back to account home</a></h3>");
            out.println("</div>");
            out.println("</section>");
            out.println("</body>");
            out.println("</html>"); 
        }
        }
        else{
            response.sendRedirect("login.jsp");
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
        return "Short description";
    }// </editor-fold>

}
