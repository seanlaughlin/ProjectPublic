<%-- 
    Document   : editcourse
    Created on : 5 Apr 2021, 15:36:17
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon16.png" sizes="16x16"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/toggle.js"></script>
        <title>Edit Student Details | GCU_Skills</title>
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
            <section class="image-section" id="accountdetails">
                <div class="image-box-content" id="detailsformbox">
                    <h2>Edit Course Details</h2>
                    <small>Click <small><a href="JavaScript:newPopup('tutormanagement?isList=true');">here</a></small> for all Tutor ID reference. (Opens in new window)</small>
                    <form class="detailsform" id="detailsform">
                        <table id="detailstable">
                            <tr>
                                <th>Course ID:</th>
                                <td id="studentid">${selectedCourse.courseId}</td>
                                <td id="studentidedit"></td>
                            </tr>
                            <tr>
                                <th>Course Name:</th>
                                <td><input type="text" class="input" name="coursename" id="coursenamefield" value="${selectedCourse.courseName}" readonly /></td>
                                <td><button class="detailsbutton" id="coursenameedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Description:</th>
                                <td><input type="text" class="input" name="description" id="descriptionfield" value="${selectedCourse.description}" readonly /></td>
                                <td><button class="detailsbutton" id="descriptionedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Tutor (ID):</th>
                                <td><input type="text" class="input" name="tutorid" id="tutoridfield" value="${selectedCourse.courseTutor.tutorId}" readonly /></td>
                                <td><button class="detailsbutton" id="tutoridedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                        </table>
                        <input type="hidden" id="servletpath" name="servletPath" value="coursedetails">
                    </form>
                    <h3><a href="coursemanagement.jsp">&#x21a9; Back to Course Management</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>

