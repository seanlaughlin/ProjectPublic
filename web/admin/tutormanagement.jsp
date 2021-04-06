<%-- 
    Document   : tutormanagement
    Created on : 4 Apr 2021, 17:24:55
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Tutor Management | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon16.png" sizes="16x16">      
        <meta name="viewport" content="width=device-width, initial-scale=1.0">     
    </head>
    <body class="scrollable">
        <c:import url="../Navbar" />
        <section class="image-section" id="lessons">
            <div class="image-box-content" id="yourlessons">
                <h2>Registered Tutors</h2>
                <small>To change registered courses visit <a href="coursemanagement.jsp">Course Management</a></small>
                <c:import url="tutormanagement" />
                <button onclick="window.location.href = 'registertutor.jsp';">Add new Tutor</button>
                <h3><a href="account.jsp">&#x21a9; Back to account home</a></h3>
            </div>
        </section>
    </body>
</html>