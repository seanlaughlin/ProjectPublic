/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets.admin;

import bean.Course;
import bean.Tutor;
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
public class AdminTutorManagement extends HttpServlet {

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
        int tutorId;
        RequestDispatcher rd = null;

        UserManager um = new UserManager();
        ArrayList<Tutor> allTutors = um.loadAllTutors();
        
        //Check if request was for a simple list of Tutors (for reference) or not
        boolean isList = Boolean.parseBoolean(request.getParameter("isList"));

        //Get name of action requested from webpage, default to display if no action parameter
        String action = request.getParameter("action");
        if (action == null) {
            action = "display";
        }
        
        //Direct user to relevant page/perform requested action depending on action parameter
        switch (action) {
            
            //Stores Tutor object in session to be used by TutorDetailsServlet and directs to edittutor page (Which uses TutorDetailsServlet)
            case ("edit"):
                tutorId = Integer.parseInt(request.getParameter("tutorId"));
                Tutor selected = null;
                for (Tutor tutor : allTutors) {
                    if (tutorId == tutor.getTutorId()) {
                        selected = tutor;
                    }
                }
                rd = request.getRequestDispatcher("edittutor.jsp");
                
                //If Student or Course were previously being edited, removes object from session, adds Tutor object for use by DetailsServlet
                session.removeAttribute("selectedStudent");
                session.removeAttribute("selectedCourse");
                
                session.setAttribute("selectedTutor", selected);
                rd.forward(request, response);
                break;
            
            //Stores tutorId in request to be used by LessonsServlet and directs to tutorschedule (which includes LessonsServlet as import)
            case ("schedule"):
                tutorId = Integer.parseInt(request.getParameter("tutorId"));
                rd = request.getRequestDispatcher("tutorschedule.jsp");
                request.setAttribute("tutorId", tutorId);
                rd.forward(request, response);
                break;
            
            //Prints table of all Tutors 
            case ("display"):
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<table class=\"lessonstable\">");
                    out.println("<tr>");
                    out.println("<th>Tutor ID</th>");
                    out.println("<th>Name</th>");
                    out.println("<th>DOB</th>");
                    out.println("<th>Email Address</th>");
                    out.println("<th>Role</th>");
                    out.println("<th>Department</th>");
                    out.println("<th>Paygrade</th>");
                    out.println("<th>Registered Courses</th>");
                    out.println("<th></th>");
                    out.println("<th></th>");
                    out.println("<th></th>");
                    out.println("</tr>");
                    
                    //For each Tutor object in array of all Tutors, output table row
                    for (Tutor tutor : allTutors) {
                        
                        //Format date object 
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String tutorDob = sdf.format(tutor.getDob());
                        
                        ArrayList<Course> tutorCourses = tutor.getTutorCourse();

                        out.println("<tr>");
                        out.format("<td>%s</td>", tutor.getTutorId());
                        out.format("<td>%1$s %2$s</td>", tutor.getFirstName(), tutor.getLastName());
                        out.format("<td>%s</td>", tutorDob);
                        out.format("<td>%s</td>", tutor.getEmail());
                        out.format("<td>%s</td>", tutor.getRole());
                        out.format("<td>%s</td>", tutor.getDepartment());
                        out.format("<td>%s</td>", tutor.getPayGrade());
                        out.println("<td>");
                        
                        switch (tutorCourses.size()) {
                            case 0:
                                out.format("<i>No registered courses</i>");
                                break;

                            case 1:
                                out.format("%s", tutorCourses.get(0).getCourseName());
                                break;

                            default:
                                out.println("<ul style=\"list-style: none\">");
                                for (Course course : tutorCourses) {
                                    out.format("<li>%s</li>", course.getCourseName());
                                }
                                out.println("</ul>");
                                break;
                        }
                        out.println("</td>");
                        
                        //If the request is not for a simple list of details only and interaction with data is required, display links
                        if (!isList) {
                            out.format("<td><small><a href=\"tutormanagement?action=schedule&tutorId=%d\">View Schedule</a></small></td>", tutor.getTutorId());
                            out.format("<td><small><a href=\"tutormanagement?action=edit&tutorId=%d\">Edit</a></small></td>", tutor.getTutorId());
                            out.format("<td><small><a href=\"delete?tutorId=%d\" style=\"color: red\">Delete</a></small></td>", tutor.getTutorId());
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminTutorManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminTutorManagement.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminTutorManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminTutorManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Admin functions relating to Tutor accounts. Viewing all registered Tutors, Tutor schedule, editing details.";
    }// </editor-fold>

}
