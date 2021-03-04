<%-- 
    Document   : login
    Created on : 4 Mar 2021, 06:48:10
    Author     : jackb
--%>

<%@page contentType = "text/html" pageEncoding = "UTF-8" import = "bean.UserManager, bean.Student"%>
<!DOCTYPE html>

<%
    
String email = request.getParameter("email");
String password = request.getParameter("password");

UserManager userManager = new UserManager();
Student loggedInStudent = userManager.logInStudent(email, password);

if(loggedInStudent != null)
{
    out.print("Success");
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
