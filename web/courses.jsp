<%-- 
    Document   : lessons
    Created on : 5 Mar 2021, 14:17:48
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lessons | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">       
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
            <div class="section-container" id="start">
                <section class="image-section" id="courses">
                    <div class="image-box-content">
                        <h1>Courses at GCU_Skills</h1> 
                        <h2>Click below for available courses</h2>
                    </div>
                    <div class="scroll-down"><a href="#courses-list"><img src='images/downArrowWhiteSmall.png' alt='scroll down'/></a></div>
                </section>
        </div>
            <div class="section-container">
                <section class="image-section" id="courses-list">
                    <div class="courses-content">
                        <h2>Courses currently available</h2> 
                        <div class="course-box"></div>
                        <div class="course-box"></div>
                        <div class="course-box"></div>
                        <div class="course-box"></div>
                    </div>
                </section>
        </div>
    </body>
</html>
