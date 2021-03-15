<%-- 
    Document   : login
    Created on : 4 Mar 2021, 06:48:10
    Author     : jackb
--%>

<%@page contentType = "text/html" pageEncoding = "UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Login | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="images/favicon32.png" sizes="32x32"/>
        <link rel="icon" type="image/png" href="images/favicon16.png" sizes="16x16">      
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
        <section class="image-section" id="login">
            <div class="form-box-content">
                <h2>Log in to your Student Account</h2>
                <form action="<%= request.getContextPath() %>/login" method="POST">    
                    <label>     
                        <input type="text" class="input" name="emailaddress" placeholder="Email"/>                   
                        <div class="line-box">          
                            <div class="line"></div>        
                        </div>    
                    </label>
                    <label>     
                        <input type="password" class="input" name="password" placeholder="Password"/>        
                        <div class="line-box">          
                            <div class="line"></div>        
                        </div>    
                    </label>         
                    <button type="submit">Log in</button>
                    <% if(request.getParameter("error")==null){}
                        else{out.println("<label><small style=\"color:red\">Invalid login details</small></label>");}%>
                    <label>
                        <small>Don't have an account? <a href="register.jsp">Register</a></small>
                    </label>
                </form> 
            </div>
        </section>
    </body>
</html>

