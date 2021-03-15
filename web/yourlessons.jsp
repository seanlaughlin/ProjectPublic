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
                <table class="lessonstable">
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Course</th>
                        <th>Lesson</th>
                        <th>Tutor</th>
                        <th>Room</th>
                        <th>Lesson Status</th>
                        <th>Course Status</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td><b>18/03/2021</b></td>
                        <td>15:00</td>
                        <td>Introduction to Microsoft Office</td>
                        <td>Microsoft Excel</td>
                        <td>Tony Soprano</td>
                        <td>B12</td>
                        <td><i>Upcoming</i></td>
                        <td><i>Ongoing</i></td>
                        <td><small><a href="courseinfo.jsp">View</a></small></td>
                    </tr>
                    <tr>
                        <td><b>27/03/2021</b></td>
                        <td>15:00</td>
                        <td>Introduction to Microsoft Office</td>
                        <td>Microsoft PowerPoint</td>
                        <td>Tony Soprano</td>
                        <td>B12</td>
                        <td><i>Upcoming</i></td>
                        <td><i>Ongoing</i></td>
                        <td><small><a href="courseinfo.jsp">View</a></small></td>
                    </tr>
                </table>
                <h2>Past Lessons</h2>    
                <table class="lessonstable">
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Course</th>
                        <th>Lesson</th>
                        <th>Tutor</th>
                        <th>Room</th>
                        <th>Lesson Status</th>
                        <th>Course Status</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td><b>11/03/2021</b></td>
                        <td>15:00</td>
                        <td>Introduction to Microsoft Office</td>
                        <td>Microsoft Word</td>
                        <td>Tony Soprano</td>
                        <td>B12</td>
                        <td><i>Completed</i></td>
                        <td><i>Ongoing</i></td>
                        <td><small><a href="courseinfo.jsp">View</a></small></td>
                    </tr>
                </table>
                <h2>Your Courses</h2>    
                <table class="lessonstable">
                    <tr>
                        <th>Course</th>
                        <th>No. Lessons</th>
                        <th>Tutor</th>
                        <th>Status</th>
                    </tr>
                    <tr>
                        <td>Introduction to Microsoft Office</td>
                        <td>3</td>
                        <td>Tony Soprano</td>
                        <td><i>Ongoing</i></td>
                    </tr>
                </table>
                <small>* - if applicable</small>
                <h3><a href="account.jsp">&#x21a9; Back to account home</a></h3>
            </div>
        </section>
    </body>
</html>
