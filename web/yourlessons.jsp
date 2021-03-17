<%-- 
    Document   : yourlessons
    Created on : 11 Mar 2021, 10:50:37
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Your Lessons | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">      
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <% if(session.getAttribute("student")!=null){
        }
            else{
            response.sendRedirect("login.jsp");}%>        
    </head>
    <body class="scrollable">
        <%@ include file="navbar.jsp" %> 
        <section class="image-section" id="lessons">
            <div class="image-box-content" id="yourlessons">
                <h2>Your Lessons</h2>    
                    <jsp:include page="/StudentLessonsServlet" />
                <h3><a href="account.jsp">&#x21a9; Back to account home</a></h3>
            </div>
        </section>
    </body>
</html>
