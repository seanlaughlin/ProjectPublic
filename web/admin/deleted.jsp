<%-- 
    Document   : deleted
    Created on : 6 Apr 2021, 21:01:59
    Author     : seanl
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Delete ${requestScope.attribute} | GCU_Skills</title>
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
                <div class="image-box-content">
                    <h2>${message}</h2>
                    <h3><a href="${requestScope.returnLinkKey}management.jsp">&#x21a9; Back to ${requestScope.returnLinkKey} management</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>

