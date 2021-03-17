package bean;

import java.util.Date;

/**
 * @author jackb
 */
public class Tutor extends User
{

    private int tutorId;
    private String role;
    private String department;
    private int payGrade;

    //Zero-Parameter Constructor
    public Tutor()
    {
        super();
        tutorId = 0;
        role = "";
        department = "";
        payGrade = 0;

    }

    //Overloaded Constructor
    public Tutor(String usernameIn, String firstNameIn, String lastNameIn, Date dobIn, String roleIn, String departmentIn, int tutorIdIn, int payGradeIn) {

        super(usernameIn, firstNameIn, lastNameIn, dobIn);
        tutorId = tutorIdIn;
        role = roleIn;
        department = departmentIn;
        payGrade = payGradeIn;

    }

    //Overloaded Constructor
    public Tutor(String usernameIn, String passwordIn, String firstNameIn, String lastNameIn, Date dobIn, String roleIn, String departmentIn, int tutorIdIn, int payGradeIn) {

        super(usernameIn, passwordIn, firstNameIn, lastNameIn, dobIn);
        tutorId = tutorIdIn;
        role = roleIn;
        department = departmentIn;
        payGrade = payGradeIn;

    }

    //Getters
    public int getTutorId() {return tutorId;}

    public String getRole() {return role;}

    public String getDepartment() {return department;}

    public int getPayGrade() {return payGrade;}

    //Setters
    public void setTutorId(int tutorIdIn) {tutorId = tutorIdIn;}

    public void setRole(String roleIn) {role = roleIn;}

    public void setDepartment(String departmentIn) {department = departmentIn;}

    public void setPayGrade(int payGradeIn) {payGrade = payGradeIn;}

}