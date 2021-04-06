<%-- 
    Document   : enrollstudent
    Created on : 5 Apr 2021, 11:19:42
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Enroll Student | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon16.png" sizes="16x16">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            document.addEventListener("keyup", addHyphon);
            function addHyphon(event) {
                var keyID = event.keyCode;
                var dob = document.getElementById("dob");
                if (keyID === 8 && dob.is(":focus")) {
                    dob.value = "";
                } else {
                    if (dob.value.length === 2 || dob.value.length === 5) {
                        dob.value += "-";
                    }
                }
            }

            function newPopup(url) {
                popupWindow = window.open(
                        url, 'popUpWindow', 'height=400,width=700,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
            }
        </script>
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="register">
                <div class="form-box-content">
                    <h2>Enroll Student</h2>
                    <small>Use links at bottom for ID reference. (Opens in new window)</small>
                    <form action="../CourseEnrollmentServlet" method="POST">  
                        <label><small style="color:red">${requestScope.error}</small></label>
                        <label>     
                            <input type="text" class="input" name="courseId" placeholder="Course ID" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>

                        </label> 

                        <label>     
                            <input type="text" class="input" name="studentId" placeholder="Student ID" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>  

                        </label>

                        <button type="submit">Enroll</button>
                        <label>
                            <small><a href="JavaScript:newPopup('studentmanagement?isList=true');">View all Students</a></small>
                            <small><a href="JavaScript:newPopup('coursemanagement?isList=true');">View all Courses</a></small>
                        </label>
                    </form> 
                        <a href="coursemanagement.jsp">Back to Course Management</a>
                </div>
            </section>
        </div>
    </body>
</html>



