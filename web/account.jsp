<%-- 
    Document   : account
    Created on : 5 Mar 2021, 16:39:17
    Author     : seanl
--%>
 <%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%@ page import="bean.Student"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Your account | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">
        <% Student student = (Student) session.getAttribute("student"); %>
    </head>
    <body>
    <%@ include file="navbar.jsp" %>  
        <div id="section-container">
           <section class="image-section" id="register">
                <div class="image-box-content">
                     <% if(session.getAttribute("student")!=null){
                         out.println("<h2>Welcome, "+student.getFirstName()+" "+student.getLastName()+"</h2>");
                        }
                        else {
                             response.sendRedirect("login.jsp");
                            } 
                    %>        
                    
                    <h3><a href="accountdetails.jsp" class="dashlink">View your account details</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>

