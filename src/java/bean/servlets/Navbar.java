/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author seanl
 */
public class Navbar extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            
            //Get session object and context path
            HttpSession session = request.getSession();
            String path = request.getContextPath();
            
            String userType;
            
            //Output links that do not change dynamically first
            out.format("<nav id=\"navbar\">%n"
                    + "<img src=\"%1$s/images/GCU_SkillsLogoWordsSmall.png\" alt=\"GCU Skills\"/><ul>", path);
            out.format("<li><a href=\"%1$s/index.jsp\">Home</a></li> "
                        + "<li><a href=\"%1$s/index.jsp#about\">About</a></li>"
                        + "<li><a href=\"%1$s/index.jsp#contact\" id=\"contactlink\">Contact</a></li> "
                        + "<li><a href=\"%1$s/courses.jsp#start\">Courses</a></li>", path);
            
            if(session.getAttribute("admin")!=null){
                userType = "admin";
            }
            else if(session.getAttribute("tutor")!=null){
                userType = "tutor";
            }
            else{
                userType = "student";
            }
            
            //Check if user logged in and print different links if logged in
            if (session.getAttribute("loggedIn") != null) {
                        out.format("<li><a href=\""+request.getContextPath()+"/"+userType+"/"+"account.jsp\">Account</a></li>"
                        + "<li><a href=\"%1$s/logout\">Logout</a></li>", path);
            }
            
            else {
                out.format("<li><a href=\"%1$s/register.jsp\">Register</a></li>"
                        + "<li><a href=\"%1$s/login.jsp\">Log in</a></li>", path);
            }
            out.println("</ul>\n"
                    + "        </nav>");
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
        return "Generated Navbar which changes depending on if and which type of user is logged in.";
    }// </editor-fold>

}
