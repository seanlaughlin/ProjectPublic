<%-- 
    Document   : registercourse
    Created on : 5 Apr 2021, 10:37:24
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register Course | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon16.png" sizes="16x16">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            function newPopup(url) {
                popupWindow = window.open(
                        url, 'popUpWindow', 'height=400,width=900,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
            }
        </script>
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="register">
                <div class="form-box-content">
                    <h2>Register a new Course</h2>
                    <small>Click <small><a href="JavaScript:newPopup('tutormanagement?isList=true');">here</a></small> for all Tutor ID reference. (Opens in new window)</small>
                    <form action="courseregister" method="POST">  
                        <label><small style="color:red">${requestScope.error}</small></label>
                        <label>     
                            <input type="text" class="input" name="coursename" placeholder="Course Name" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>     
                            <input type="number" class="input" name="tutorid" placeholder="ID of Assigned Tutor" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <label>     
                            <textarea class="input" name="description" placeholder="Course Description" rows='5' required></textarea>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <button type="submit">Add Course</button>
                        <label>
                        </label>
                    </form> 
                    <a href="coursemanagement.jsp">&#x21a9; Back to Course Management</a>
                </div>
            </section>
        </div>
    </body>
</html>
