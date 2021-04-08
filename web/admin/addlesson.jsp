<%-- 
    Document   : addlessons
    Created on : 5 Apr 2021, 14:52:03
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Lesson | GCU_Skills</title>
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
                    <h2>Add Lesson to Course</h2>
                    <small>Click <small><a href="JavaScript:newPopup('coursemanagement?isList=true');">here</a></small> for all Course ID reference. (Opens in new window)</small>
                    <form action="lessonregister" method="POST">  
                        <label><small style="color:red">${requestScope.error}</small></label>
                        <label><small style="color:green">${requestScope.message}</small></label>
                        <label>     
                            <input type="text" class="input" name="courseid" placeholder="Course ID" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>     
                            <input type="date" class="input" name="lessondate" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <label>  
                            at time (24h):
                            <input type="time" name="lessontime" required/>                   

                        </label>
                        <button type="submit">Add Lesson</button>
                        <label>
                        </label>
                    </form> 
                    <a href="coursemanagement.jsp">&#x21a9; Back to Course Management</a>
                </div>
            </section>
        </div>
    </body>
</html>