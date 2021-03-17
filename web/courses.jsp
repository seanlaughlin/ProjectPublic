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
                    <div class="course-box"><div style="width: 20%"><h3>Guide to Windows 10</h3><p><b>Start Date:</b> 15/04/2021 15:00</p><p> <b>Tutor:</b> Bill Gates</p><p><b>No. of lessons:</b> 3</p></div>
                        <div><p><b>Description: </b><br><p>Learn the fundamentals of the Windows 10 operating system, including set up and file management.</p><p> Course is comprised of three 1-hour lessons at 15:00 on Thursdays from 15/04/2021 for 2 weeks.</p>
                            <small><a href="#">Register for course</a></small></div></div>
                    <div class="course-box"><div style="width: 20%"><h3>Computer Hardware: Basics</h3><p><b>Start Date:</b> 07/05/2021 12:00</p><p> <b>Tutor:</b> Elon Musk</p><p><b>No. of lessons:</b> 1</p></div>
                        <div><p><b>Description: </b><br><p>Learn the basics of computer hardware components, as taught by industry leader Elon Musk.</p><p> Course is comprised of one 2-hour lesson at 12:00 on Friday 07/05/2021.</p>
                            <small><a href="#">Register for course</a></small></div></div>
                    <div class="course-box"><div style="width: 20%"><h3>Printers and Scanners</h3><p><b>Start Date:</b> 12/05/2021 13:30</p><p> <b>Tutor:</b> Dave Lister</p><p><b>No. of lessons:</b> 2</p></div>
                        <div><p><b>Description: </b><br><p>Learn how to install and use printers and scanners, including how to import and print your old photos.</p><p> Course is comprised of two 1-hour lessons at 13:30 on Wednesdays from 12/05/2021.</p>
                            <small><a href="#">Register for course</a></small></div></div>
                    <div class="course-box"><div style="width: 20%"><h3>Intro to Microsoft Office</h3><p><b>Start Date:</b> 20/05/2021 10:30</p><p> <b>Tutor:</b> Tony Soprano</p><p><b>No. of lessons:</b> 3</p></div>
                        <div><p><b>Description: </b><br><p>Learn to use the most popular office software package including PowerPoint, Word and Excel</p><p> Course is comprised of three 1-hour lessons at 10:30 on Thursdays from 20/05/2021.</p>
                            <small><a href="#">Register for course</a></small></div></div>
                </div>
            </section>
        </div>
    </body>
</html>
