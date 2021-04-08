<%-- 
    Document   : editstudent
    Created on : 4 Apr 2021, 18:20:51
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
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="accountdetails">
                <div class="image-box-content" id="detailsformbox">
                    <h2>Edit Student Details</h2>
                    <form class="detailsform" id="detailsform">
                        <table id="detailstable">
                            <tr>
                                <th>Student ID:</th>
                                <td id="studentid">${selectedStudent.studentId}</td>
                                <td id="studentidedit"></td>
                            </tr>
                            <tr>
                                <th>First name:</th>
                                <td><input type="text" class="input" name="firstname" id="firstnamefield" value="${selectedStudent.firstName}" readonly /></td>
                                <td><button class="detailsbutton" id="firstnameedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Surname:</th>
                                <td><input type="text" class="input" name="lastname" id="lastnamefield" value="${selectedStudent.lastName}" readonly /></td>
                                <td><button class="detailsbutton" id="lastnameedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Date of birth:</th>
                                <td><input type="text" class="input" name="dateofbirth" id="dobfield" value="${selectedStudent.dobString}" readonly /></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th>Contact no:</th>
                                <td><input type="text" class="input" name="phonenumber" id="phonenofield" value="${selectedStudent.phoneNumber}" readonly /></td>
                                <td><button class="detailsbutton" id="phonenoedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td><input type="text" class="input" name="emailaddress" id="emailfield" value="${selectedStudent.email}" readonly /></td>
                                <td><button class="detailsbutton" id="emailedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>                          
                        </table>
                        <input type="hidden" id="servletpath" name="servletPath" value="${pageContext.request.contextPath}/details">
                    </form>
                    <h3><a href="studentmanagement.jsp">&#x21a9; Back to student management</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>
