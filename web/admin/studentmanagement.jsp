<%-- 
    Document   : studentmanagement
    Created on : 4 Apr 2021, 18:05:00
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Management | GCU_Skills</title>
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
                <h2>Registered Students</h2>   
                <small>To manage Courses and Lessons visit <a href="coursemanagement.jsp">Course Management</a></small>
                <c:import url="studentmanagement" />
                <button onclick="window.location.href = '${pageContext.request.contextPath}/register.jsp';">Add new Student</button>
                <h3><a href="account.jsp">&#x21a9; Back to account home</a></h3>
            </div>
        </section>
    </body>
</html>
