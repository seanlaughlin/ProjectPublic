/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jackb
 */
public class UserManager {
    
    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\jackb\\IdeaProjects\\GCU_Skills\\data\\GCU_SkillsDB.accdb";
    
    public int registerStudent(Student student){
        
        int studentId = 0;
        
        try
        {
            
           Class.forName(driver);
           Connection conn = DriverManager.getConnection(connectionString);
           Statement stmt = conn.createStatement();
           stmt.executeUpdate("INSERT INTO Students(EmailAddress, Password, FirstName, LastName, DateOfBirth, PhoneNumber) " + "VALUES('" + student.getEmail() + "', '" + student.getPassword() + "', '" + student.getFirstName() + "', '" + student.getLastName() + "', '" + student.getDob() + "', '" + student.getPhoneNumber() + "')" );
        
           ResultSet rs = stmt.getGeneratedKeys();
            
            if (rs.next())
            {
                
                studentId = rs.getInt(1);
                
            } 
           
           conn.close();
           return studentId;
           
        }
        catch(Exception ex)
        {
            
            String message = ex.getMessage();
            return 0;
            
        }
        
    }
    
    public Student logInStudent(String emailAddress, String password){
        
        //String as key is the Customers Username
        HashMap<Integer, Student> studentsHashMap = loadAllStudents();
        
        for(Map.Entry<Integer, Student> studentEntry : studentsHashMap.entrySet())
            {

                Student actualStudent = studentEntry.getValue();
                
                if(actualStudent.getEmail().equalsIgnoreCase(emailAddress))
                {

                    int foundStudentId = actualStudent.getStudentId();
                    Student foundStudent = studentsHashMap.get(foundStudentId);
                    
                    if(foundStudent.getPassword().equals(password))
                    {
                        return foundStudent;
                    }
                    
                    else{return null;}
                    
                }
                
                else{return null;}

            }
        
        return null;
        
    }
    
    public HashMap<Integer, Student> loadAllStudents(){

    //String as key is the Customers Username
    HashMap<Integer, Student> studentsHashMap = new HashMap();

    try
    {       

       Class.forName(driver);
       Connection conn = DriverManager.getConnection(connectionString);
       Statement stmt = conn.createStatement();

       //Selects every entry in the customers table in the database
       ResultSet rs= stmt.executeQuery("SELECT * FROM Students");

       //Executes for every entry ing the results set
       while(rs.next())
       {

           //Gets the values from the result set entry and creates a customer object using them
           int studentId = rs.getInt("StudentId");
           String email = rs.getString("EmailAddress");
           String password = rs.getString("Password");
           String firstName = rs.getString("FirstName");
           String lastName = rs.getString("LastName");
           Date dob = rs.getDate("DateOfBirth");
           String phoneNumber = rs.getString("PhoneNumber");
           int courseId = rs.getInt("CourseId");

           Course studentCourse = new Course(courseId);

           Student students = new Student(email, password, firstName, lastName, dob, studentId, phoneNumber, studentCourse);

           //Puts the student object into the hashmap
           studentsHashMap.put(studentId, students);          

       }

       conn.close();

    }

    catch(Exception ex)
    {

        String message = ex.getMessage();

    }

    finally
    {

        //Loads the orders and orderlines into the customerHashMap and returns it
        CourseManager cm = new CourseManager();
        studentsHashMap = cm.loadStudentCourse(studentsHashMap);
        studentsHashMap = cm.loadCourseLessons(studentsHashMap);
        return studentsHashMap;

    }    
}
    
}
