<%-- 
    Document   : viewcoursestudents
    Created on : 5 Apr 2021, 00:02:54
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>View Course Students | GCU_Skills</title>
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
                <h2>Enrolled Students</h2>    
                <small>Please note - prior to course start lessons cannot be set to On-Going or Completed.</small>
                ${requestScope.message}
                <c:import url="../CourseStudentsServlet" />
                <button onclick="window.location.href = 'enrollstudent.jsp';">Enroll New</button><br>
                <h3><a href="coursemanagement.jsp">&#x21a9; Back to Course Management</a></h3>
            </div>
        </section>
    </body>
</html>
