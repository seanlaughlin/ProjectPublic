<%-- 
    Document   : navbar
    Created on : 5 Mar 2021, 16:51:30
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <nav id="navbar">
            <img src="images/GCU_SkillsLogoWordsSmall.png" alt="GCU Skills"/>
            <ul>
              <li><a href="index.jsp">Home</a></li>
              <li><a href="index.jsp#about">About</a></li>
              <li><a href="index.jsp#contact" id="contactlink">Contact</a></li>
              <li><a href="courses.jsp#start">Courses</a></li>
               <% if(session.getAttribute("loggedIn")!=null){ 
                   out.println("<li><a href=\"account.jsp\">Account</a></li>"
                           + "<li><a href=\"logout.jsp\">Logout</a></li>");
               }
                    else{
                        out.println("<li><a href=\"register.jsp\">Register</a></li>"
                                     +"<li><a href=\"login.jsp\">Log in</a></li>");
                            }%>
            </ul>
        </nav>
