<%-- 
    Document   : accountdetails
    Created on : 5 Mar 2021, 18:20:51
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <title>Account details | GCU_Skills</title>
        <% if(session.getAttribute("student")!=null){
        }
            else{
            response.sendRedirect("login.jsp");}%>
    </head>
    <body>
     <%@ include file="navbar.jsp" %>  
        <div id="section-container">
           <section class="image-section" id="register">
                <div class="image-box-content">
                    <h2>Working on this</h2>
                </div>
            </section>
        </div>
    </body>
</html>
