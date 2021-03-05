package bean;

import java.util.Date;

/**
 *
 * @author jackb
 */
public class User {
    
    //Private Variables
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dob;

    //Zero-Parameter Constructor
    public User()
    {

        email = "";
        password = "";
        firstName = "";
        lastName = "";
        dob = new Date();

    }

    //Overloaded Constructor
    public User(String emailIn, String passwordIn, String firstNameIn, String lastNameIn, Date dobIn)
    {

        email = emailIn;
        password = passwordIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        dob = dobIn;

    }

    //Getters
    public String getEmail() {return email;}
    
    public String getPassword() {return password;}
    
    public String getFirstName() {return firstName;}
    
    public String getLastName() {return lastName;}

    public Date getDob() {return dob;}

    //Setters
    public void setEmail(String emailIn) {email = emailIn;}
    
    public void setPassword(String passwordIn) {password = passwordIn;}
    
    public void setFirstName(String firstNameIn) {firstName = firstNameIn;}
    
    public void setLastName(String lastNameIn) {lastName = lastNameIn;}

    public void setDob(Date dobIn) {dob=dobIn;}
}
