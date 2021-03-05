<%-- 
    Document   : register
    Created on : 2 Mar 2021, 11:59:14
    Author     : jackb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Register | GCU_Skills</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            function addHyphon(){
                var dob = document.getElementById("dob");
                var doblength = document.getElementById("dob").length;
                    if(dob.value.length === 2 || dob.value.length === 5) {
                    dob.value += "-";
                }
            }
        </script>
    </head>
    <body>
        <%@ include file="navbar.jsp" %> 
        <div id="section-container">
           <section class="image-section" id="register">
                <div class="form-box-content">
                    <h2>Register a new Student Account</h2>
                        <form action="<%= request.getContextPath() %>/register" method="POST">    
                        <label>     
                            <input type="text" class="input" name="fname" placeholder="First name" autocomplete="off"/>                   
                            <div class="line-box">          
                            <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>     
                            <input type="text" class="input" name="surname" placeholder="Surname"/>                   
                            <div class="line-box">          
                            <div class="line"></div>        
                            </div>    
                        </label>
                        <label>     
                            <input type="text" class="input" name="dob" onkeyup="addHyphon();" id="dob" placeholder="Date of birth (DDMMYYYY)" 
                            pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" required  title="Enter a date in this format DD-MM-YYYY"/>               
                            <div class="line-box">          
                            <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>     
                            <input type="text" class="input" name="address" placeholder="Address"/>                   
                            <div class="line-box">          
                            <div class="line"></div>        
                            </div>    
                        </label> 
                        <label>
                           <input type="text" class="input" name="contactno" placeholder="Phone number"/>                   
                            <div class="line-box">          
                            <div class="line"></div>        
                            </div>    
                        </label>                         
                        <label>     
                          <input type="text" class="input" name="email" placeholder="Email"/>                   
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
                        <button type="submit">Register</button>
                        <label>
                            <small>Already have an account? <a href="login.jsp">Log in</a></small>
                        </label>
                    </form> 
                </div>
            </section>
        </div>
    </body>
</html>


