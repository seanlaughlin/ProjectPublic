<%-- 
    Document   : enroll
    Created on : 19 Mar 2021, 13:06:04
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Enroll | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
        <div id="section-container">
            <section class="image-section" id="register">
                <div class="form-box-content">
                    <% if(session.getAttribute("student")!=null){
                        }
                        else{
                        response.sendRedirect("login.jsp");}
                    if(request.getParameter("enrollment").equals("true")){
                        out.println("<h2>Enrollment successful. <h3><a href=\"account.jsp\">&#x21a9; Back to account home</a></h3></h2>");
                    }
                    else if(request.getParameter("enrollment").equals("false")){
                        out.println("<h2>Enrollment error. Please <a href=\"index.jsp#contact\">contact support</a> for assistance. <h3><a href=\"account.jsp\">&#x21a9; Back to account home</a></h3></h2>");
                            }
                    else if(request.getParameter("enrollment").equals("registered")) {
                            out.println("<h2>You are already enrolled on this course. <h3><a href=\"account.jsp\">&#x21a9; Back to account home</a></h3></h2>");}%>
                </div>
            </section>
        </div>
    </body>
</html>
