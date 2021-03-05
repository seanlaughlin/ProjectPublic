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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jackb
 */
public class CourseManager {
    
    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\jackb\\IdeaProjects\\GCU_Skills\\data\\GCU_SkillsDB.accdb";
    
    public HashMap<Integer, Student> loadStudentCourse(HashMap<Integer, Student> students){
        
        try
        {
            
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            //Selects all entries in the orders table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Courses");
            
            //Executes while the results set has a next value
            while(rs.next())
            {
                
                //Loads all values for an entry into an order object
                int courseId = rs.getInt("CourseId");
                String courseName = rs.getString("CourseName");
                String courseStatus = rs.getString("CourseStatus");
                int tutorId = rs.getInt("TutorId");
                
                Course loadedCourse = new Course(courseId, courseName, courseStatus);

                //Executes if the customers hash map contains an entry with a matching username
                if(students.containsKey(courseId))
                {
                    
                    //Adds the order to the  orders for that customer in the orders hash map
                    Student studentWithCourse = students.get(courseId);
                    studentWithCourse.setCourse(loadedCourse);
                    
                }
                
            }
            
            conn.close();
            
        }
        
        catch(Exception ex)
        {
            
            String message = ex.getMessage();
            
        }
        
        finally
        {
            
            return students;
            
        }
        
    }
    
    public HashMap<Integer, Lesson> loadAllLessons(){
        
        HashMap<Integer, Lesson> lessonsHashMap = new HashMap();
        
        try
        {
        
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            
            //Selects all entries in the OrderLines table in the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lessons");
            
            while(rs.next())
            {
                
                //Loads all values for an entry into an order line object
                int lessonId = rs.getInt("LessonId");
                Date timeSlot = rs.getDate("TimeSlot");
                int courseId = rs.getInt("CourseId");
                
                Lesson lessons = new Lesson(lessonId, timeSlot);
                
                lessonsHashMap.put(lessonId, lessons);
                
            }
            
        }
        
        catch(Exception ex)
        {
            
            String message = ex.getMessage();
            
        }
        
        finally
        {
        
            return lessonsHashMap;
            
        }
        
    }
    
    public HashMap<Integer, Student> loadCourseLessons(HashMap<Integer, Student> students){
        
        try
        {
            
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            //Selects all entries in the orders table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lessons");
            
            //Executes while the results set has a next value
            while(rs.next())
            {
                
                //Loads all values for an entry into an order object
                int lessonId = rs.getInt("LessonId");
                Date timeSlot = rs.getDate("TimeSlot");
                int courseId = rs.getInt("CourseId");
                
                Lesson loadedLesson = new Lesson(lessonId, timeSlot);

                for(Map.Entry<Integer, Student> studentEntry : students.entrySet())
                {
                    
                    Student actualStudent = studentEntry.getValue();
                    
                    if(actualStudent.getCourse().getCourseId() == courseId)
                    {
                        
                        Course courseWithLesson = actualStudent.getCourse();
                        courseWithLesson.getLessons().put(lessonId, loadedLesson);
                        
                    }
                    
                }
                
            }
            
            conn.close();
            
        }
        
        catch(Exception ex)
        {
            
            String message = ex.getMessage();
            
        }
        
        finally
        {
            
            return students;
            
        }
        
    }
    
}
