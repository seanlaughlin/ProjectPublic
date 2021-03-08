<%---
    Document   : logout
    Created on : 22 Feb 2021, 12:49:37
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="outnavbar.jsp" %> 
        <div id="section-container">
           <section class="image-section" id="register">
                <div class="image-box-content">
                          <% 
               if(session.getAttribute("loggedIn")!=null){ 
                                session.invalidate();
                out.println("<h2>Logged out successfully.</h2>");
            }
            else{
                   out.println("You are not logged in.");
            } 
        %>  
                </div>
            </section>
        </div>
    </body>
</html>



