<%-- 
    Document   : edittutor
    Created on : 4 Apr 2021, 19:57:26
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
        <title>Edit Tutor Details | GCU_Skills</title>
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="accountdetails">
                <div class="image-box-content" id="detailsformbox">
                    <h2>Edit Tutor Details</h2>
                    <form class="detailsform" id="detailsform">
                        <table id="detailstable">
                            <tr>
                                <th>Tutor ID:</th>
                                <td id="tutorid">${selectedTutor.tutorId}</td>
                                <td id="tutoridedit"></td>
                            </tr>
                            <tr>
                                <th>First Name:</th>
                                <td><input type="text" class="input" name="firstname" id="firstnamefield" value="${selectedTutor.firstName}" readonly /></td>
                                <td><button class="detailsbutton" id="firstnameedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Surname:</th>
                                <td><input type="text" class="input" name="lastname" id="lastnamefield" value="${selectedTutor.lastName}" readonly /></td>
                                <td><button class="detailsbutton" id="lastnameedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Date of birth:</th>
                                <td><input type="text" class="input" name="dateofbirth" id="dobfield" value="${selectedTutor.dobString}" readonly /></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th>Role:</th>
                                <td><input type="text" class="input" name="role" id="rolefield" value="${selectedTutor.role}" readonly /></td>
                                <td><button class="detailsbutton" id="roleedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Department:</th>
                                <td><input type="text" class="input" name="department" id="departmentfield" value="${selectedTutor.department}" readonly /></td>
                                <td><button class="detailsbutton" id="departmentedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Paygrade:</th>
                                <td><input type="text" class="input" name="paygrade" id="paygradefield" value="${selectedTutor.payGrade}" readonly /></td>
                                <td><button class="detailsbutton" id="paygradeedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td><input type="text" class="input" name="emailaddress" id="emailfield" value="${selectedTutor.email}" readonly /></td>
                                <td><button class="detailsbutton" id="emailedit">Edit</button><small class="editconfirm">&check; updated.</small></td>
                            </tr>                          
                        </table>  
                        <input type="hidden" id="servletpath" name="servletPath" value="${pageContext.request.contextPath}/tutordetails">
                    </form>
                    <h3><a href="tutormanagement.jsp">&#x21a9; Back to Tutor Management</a></h3>
                </div>
            </section>
        </div>
    </body>
</html>

