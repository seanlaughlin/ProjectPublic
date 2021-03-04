package bean;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author jackb
 */
public class Course {

    private int courseId;
    private String courseName;
    private String courseStatus;
    private HashMap<Integer, Lesson> lessons;

    //Zero-Parameter Constructor
    public Course()
    {

        courseId = 0;
        courseName = "";
        courseStatus = "";
        lessons = new HashMap<Integer, Lesson>();

    }

    public Course(int courseIdIn)
    {

        courseId = courseIdIn;
        courseName = "";
        courseStatus = "";
        lessons = new HashMap<Integer, Lesson>();

    }
    
    //Overloaded Constructor
    public Course(int courseIdIn, String courseNameIn, String courseStatusIn)
    {

        courseId = courseIdIn;
        courseName = courseNameIn;
        courseStatus = courseStatusIn;
        lessons = new HashMap();

    }
    
    //Overloaded Constructor
    public Course(int courseIdIn, String courseNameIn, String courseStatusIn, HashMap<Integer, Lesson> lessonsIn)
    {

        courseId = courseIdIn;
        courseName = courseNameIn;
        courseStatus = courseStatusIn;
        lessons = lessonsIn;

    }

    //Getters
    public int getCourseId() {return courseId;}

    public String getCourseName() {return courseName;}

    public String getCourseStatus() {return courseStatus;}

    public HashMap<Integer, Lesson> getLessons() {return lessons;}

    //Setters
    public void setCourseId(int courseIdIn) {courseId = courseIdIn;}

    public void setCourseName(String courseNameIn) {courseName = courseNameIn;}

    public void setCourseStatus(String courseStatusIn) {courseStatus= courseStatusIn;}

    public void setLessons(HashMap<Integer, Lesson> lessonsIn) {lessons = lessonsIn;}

}
