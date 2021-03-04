package bean;

import java.util.Date;

/**
 * @author jackb
 */
public class Student extends User {

    int studentId;
    private String phoneNumber;
    private Course course;

    //Zero-Parameter Constructor
    public Student()
    {
        
        super();
        studentId = 0;
        phoneNumber = "";
        course = new Course();

    }

    //Overloaded Constructor
    public Student(String emailIn, String passwordIn, String firstNameIn, String lastNameIn, Date dobIn, String phoneNumberIn)
    {

        super(emailIn, passwordIn, firstNameIn, lastNameIn, dobIn);
        studentId = 0;
        phoneNumber = phoneNumberIn;
        course = new Course();

    }

    //Overloaded Constructor
    public Student(String emailIn, String passwordIn, String firstNameIn, String lastNameIn, Date dobIn, int studentIdIn, String phoneNumberIn, Course courseIn)
    {
        
        super(emailIn, passwordIn, firstNameIn, lastNameIn, dobIn);
        studentId = studentIdIn;
        phoneNumber = phoneNumberIn;
        course = courseIn;

    }
    
    //Getters
    public int getStudentId() {return studentId;}

    public String getPhoneNumber() {return phoneNumber;}
    
    public Course getCourse() {return course;}
    
    //Setters
    public void setStudentId(int studentIdIn) {studentId = studentIdIn;}

    public void setPhoneNumber(String phoneNumberIn) {phoneNumber = phoneNumberIn;}

    public void setCourse(Course courseIn) {course = courseIn;}
    
}
