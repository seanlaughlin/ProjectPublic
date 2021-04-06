<%-- 
    Document   : unenroll
    Created on : 5 Apr 2021, 12:28:54
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Student Unenroll | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon16.png" sizes="16x16">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="register">
                <div class="form-box-content">
                    ${requestScope.message}
                    <h3><a href="admin/coursemanagement.jsp">&#x21a9;Back to Course Management</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>
