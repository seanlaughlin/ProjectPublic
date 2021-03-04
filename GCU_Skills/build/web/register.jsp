<%-- 
    Document   : register
    Created on : 2 Mar 2021, 11:59:14
    Author     : jackb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import = "java.util.Date, java.text.SimpleDateFormat, bean.Student, bean.UserManager"%>
<!DOCTYPE html>

<%
    
String email = request.getParameter("email");
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String password = request.getParameter("password");
String sDob = request.getParameter("dob");
Date dob = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDob);  
String phoneNumber = request.getParameter("phoneNumber");

Student student = new Student(email, password, firstName, lastName, dob, phoneNumber);

UserManager userManager = new UserManager();
int studentId = userManager.registerStudent(student); 

if(studentId>0){
out.print("Completed.");
}

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
