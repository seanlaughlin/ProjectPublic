<%-- 
    Document   : registertutor
    Created on : 4 Apr 2021, 22:16:00
    Author     : seanl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Register Tutor | GCU_Skills</title>
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
        </script>
    </head>
    <body>
        <c:import url="../Navbar" /> 
        <div id="section-container">
            <section class="image-section" id="register">
                <div class="form-box-content">
                    <h2>Register a new Tutor Account</h2>
                    <form action="tutorregister" method="POST">  
                        <label><small style="color:red">${requestScope.error}</small></label>
                        <label>     
                            <input type="text" class="input" name="fname" placeholder="First name" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>     
                            <input type="text" class="input" name="surname" placeholder="Surname" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <label>     
                            <input type="text" class="input" name="email" placeholder="Email" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <label>     
                            <input type="text" class="input" name="dob" onkeyup="addHyphon();" id="dob" placeholder="Date of birth (DDMMYYYY)" 
                                   pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title="Enter a date in this format DD-MM-YYYY" required />               
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>  
                        <label>
                            <input type="text" class="input" name="role" placeholder="Role" required />                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>                         
                        <label>     
                            <input type="text" class="input" name="department" placeholder="Department" required/>                   
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>         
                        <label>     
                            <input type="text" class="input" name="paygrade" placeholder="Paygrade" required/>        
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <label>     
                            <input type="password" class="input" name="password" placeholder="Password" required/>        
                            <div class="line-box">          
                                <div class="line"></div>        
                            </div>    
                        </label>
                        <button type="submit">Register</button>
                        <label>
                            <small><a href="tutormanagement.jsp">&#x21a9; Back to Tutor Management</a></small>
                        </label>
                    </form> 
                </div>
            </section>
        </div>
    </body>
</html>


