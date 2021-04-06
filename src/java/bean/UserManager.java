package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jackb
 */
public class UserManager {

    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\seanl\\Documents\\NetBeansProjects\\GCU_1\\data\\GCU_SkillsDB.accdb";
    PassHash passHash = new PassHash();

    //Register a new Student in Students table in db, returns Student ID number
    public int registerStudent(Student student) {

        int studentId = 0;

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Students(EmailAddress, Password, FirstName, LastName, DateOfBirth, PhoneNumber) " + "VALUES('" + student.getEmail() + "', '" + student.getPassword() + "', '" + student.getFirstName() + "', '" + student.getLastName() + "', '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(student.getDob()) + "', '" + student.getPhoneNumber() + "')");

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                studentId = rs.getInt(1);

            }

            conn.close();
            return studentId;

        } catch (ClassNotFoundException | SQLException ex) {

            String message = ex.getMessage();
            return 0;

        }

    }

    //Check login and password against email and hashed password in Student table in DB and return Student object if valid
    public Student logInStudent(String emailAddress, String password) throws SQLException, ClassNotFoundException {

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        int studentId = 0;
        String realPassword = "";
        String firstName = "";
        String lastName = "";
        Date dob = new Date();
        String phoneNumber = "";

        //Selects every matching entry in the customers table in the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM Students WHERE EmailAddress= '" + emailAddress + "'");

        //Executes for every entry in the results set
        while (rs.next()) {

            //Gets the values from the result set entry 
            studentId = rs.getInt("studentId");
            realPassword = rs.getString("Password");
            firstName = rs.getString("FirstName");
            lastName = rs.getString("LastName");
            dob = rs.getDate("DateOfBirth");
            phoneNumber = rs.getString("PhoneNumber");

        }

        conn.close();

        //Compares encrypted password in database to plaintext password for equality using bCrypt and returns student if valid
        boolean isPassValid = passHash.checkPassword(emailAddress, password, "Students");

        if (isPassValid) {
            CourseManager cm = new CourseManager();
            ArrayList<Course> studentCourses = cm.loadStudentCourses(studentId);
            Student student = new Student(emailAddress, password, firstName, lastName, dob, studentId, phoneNumber, studentCourses);

            //Loads the students course and the course's lessons
            //Returns the completed student object
            student.setStudentId(studentId);
            return student;

        } else {
            return null;
        }
    }

    //Check login and password against email and hashed password in Admin table in DB and return Admin object if valid
    public Admin logInAdmin(String emailAddress, String password) throws SQLException, ClassNotFoundException {

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        int adminId = 0;
        String realPassword = "";
        String firstName = "";
        String lastName = "";
        Date dob = new Date();
        boolean superUser;
        Admin admin = null;

        //Selects every matching entry in the customers table in the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM Administrators WHERE EmailAddress= '" + emailAddress + "'");

        //Executes for every entry in the results set
        while (rs.next()) {

            //Gets the values from the result set entry 
            adminId = rs.getInt("adminId");
            realPassword = rs.getString("Password");
            firstName = rs.getString("FirstName");
            lastName = rs.getString("LastName");
            dob = rs.getDate("DateOfBirth");
            superUser = rs.getBoolean("superuser");
            admin = new Admin(emailAddress, password, firstName, lastName, dob, adminId, superUser);
        }

        conn.close();

        //Compares encrypted password in database to plaintext password for equality using bCrypt and returns admin object if valid
        boolean isPassValid = passHash.checkPassword(emailAddress, password, "Administrators");

        if (isPassValid) {
            return admin;

        } else {
            return null;
        }
    }

    //Update Attribute of Student in Students table in DB and return updated Student object
    public Student updateAttribute(String parameterName, String parameter, Student student) throws SQLException, ClassNotFoundException, ParseException {

        //Check name of parameter submitted from web form (through servlet) and update student object
        switch (parameterName) {
            case "emailaddress":
                student.setEmail(parameter);
                break;

            case "phonenumber":
                student.setPhoneNumber(parameter);
                break;

            case "password":
                student.setPassword(parameter);
                parameter = passHash.hashPassword(parameter);
                break;
        }

        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE Students SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE StudentId= " + student.getStudentId());
        conn.close();

        //Return updated student object
        return student;
    }

    //Check login and password against email and hashed password in Tutors table in DB and return Tutor object if valid
    public Tutor logInTutor(String emailAddress, String password) throws SQLException, ClassNotFoundException {

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        int tutorId = 0;
        String realPassword = "";
        String firstName = "";
        String lastName = "";
        Date dob = new Date();
        String role = "";
        String department = "";
        int payGrade = 0;

        //Selects every matching entry in the customers table in the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM Tutors WHERE EmailAddress= '" + emailAddress + "'");

        //Executes for every entry in the results set
        while (rs.next()) {

            //Gets the values from the result set entry 
            tutorId = rs.getInt("TutorId");
            realPassword = rs.getString("Password");
            firstName = rs.getString("FirstName");
            lastName = rs.getString("LastName");
            dob = rs.getDate("DateOfBirth");
            role = rs.getString("Role");
            department = rs.getString("Department");
            payGrade = rs.getInt("PayGrade");

        }

        conn.close();

        //Compares encrypted password in database to plaintext password for equality using bCrypt and returns tutor if valid
        boolean isPassValid = passHash.checkPassword(emailAddress, password, "Tutors");

        if (isPassValid) {
            CourseManager cm = new CourseManager();

            Tutor tutor = new Tutor(emailAddress, password, firstName, lastName, dob, tutorId, role, department, payGrade, cm.loadTutorCourses(tutorId));

            return tutor;

        } else {
            return null;
        }
    }
    
//Update Attribute of Tutor in Tutors table in DB and return updated Tutor object
    public Tutor updateAttribute(String parameterName, String parameter, Tutor tutor) throws SQLException, ClassNotFoundException {

        //Check name of parameter submitted from web form (through servlet) and update tutor object
        switch (parameterName) {
            case "emailaddress":
                tutor.setEmail(parameter);
                break;

            case "password":
                tutor.setPassword(parameter);
                parameter = passHash.hashPassword(parameter);
                break;
        }

        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE Tutors SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE TutorId= " + tutor.getTutorId());
        conn.close();

        //Return updated tutor object
        return tutor;
    }

    //Update Attribute of Admin in Admin table in DB and return updated Admin object
    public Admin updateAttribute(String parameterName, String parameter, Admin admin) throws SQLException, ClassNotFoundException {

        //Check name of parameter submitted from web form (through servlet) and update Admin object
        switch (parameterName) {
            case "emailaddress":
                admin.setEmail(parameter);
                break;

            case "password":
                admin.setPassword(parameter);
                parameter = passHash.hashPassword(parameter);
                break;

            case "firstname":
                admin.setFirstName(parameter);
                break;

            case "lastname":
                admin.setLastName(parameter);
                break;
        }

        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE Administrators SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE AdminId= " + admin.getAdminId());
        conn.close();

        //Return updated Admin object
        return admin;
    }

    //Loads all Students from Students table in DB
    public ArrayList<Student> loadAllStudents() throws SQLException, ClassNotFoundException {

        ArrayList<Student> allStudents = new ArrayList<>();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT StudentId, EmailAddress, FirstName, LastName, DateOfBirth, PhoneNumber FROM Students");

            while (rs.next()) {

                int studentId = rs.getInt("StudentId");
                String emailAddress = rs.getString("EmailAddress");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                String phoneNumber = rs.getString("PhoneNumber");

                CourseManager cm = new CourseManager();

                ArrayList<Course> studentCourse = cm.loadStudentCourses(studentId);

                Student loadedStudent = new Student(emailAddress, firstName, lastName, dob, studentId, phoneNumber, studentCourse);

                allStudents.add(loadedStudent);

            }

        } catch (Exception ex) {

            String message = ex.getMessage();

        } finally {

            return allStudents;

        }

    }

    //Loads all Tutors from Tutors table in DB
    public ArrayList<Tutor> loadAllTutors() throws SQLException, ClassNotFoundException {

        ArrayList<Tutor> allTutors = new ArrayList<>();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT TutorId, EmailAddress, FirstName, LastName, DateOfBirth, Role, Department, PayGrade FROM Tutors");

            while (rs.next()) {

                int tutorId = rs.getInt("TutorId");
                String emailAddress = rs.getString("EmailAddress");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                String role = rs.getString("Role");
                String department = rs.getString("Department");
                int payGrade = rs.getInt("PayGrade");
                CourseManager cm = new CourseManager();

                ArrayList<Course> tutorCourses = cm.loadTutorCourses(tutorId);

                Tutor loadedTutor = new Tutor(emailAddress, firstName, lastName, dob, tutorId, role, department, payGrade, tutorCourses);

                allTutors.add(loadedTutor);

            }

        } catch (Exception ex) {

            String message = ex.getMessage();

        } finally {

            return allTutors;

        }

    }

    //Register new Tutor account and return Tutor ID
    public int registerTutor(Tutor tutor) {

        int tutorId = 0;

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Tutors(EmailAddress, Password, FirstName, LastName, DateOfBirth, Role, Department, PayGrade) " + "VALUES('" + tutor.getEmail() + "', '" + tutor.getPassword() + "', '" + tutor.getFirstName() + "', '" + tutor.getLastName() + "', '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tutor.getDob()) + "', '" + tutor.getRole() + "', '" + tutor.getDepartment() + "', '" + tutor.getPayGrade() + "')");

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                tutorId = rs.getInt(1);

            }

            conn.close();
            return tutorId;

        } catch (ClassNotFoundException | SQLException ex) {

            String message = ex.getMessage();
            return 0;

        }

    }

    //Delete user account from any user table as specified
    public boolean deleteUser(String userType, int userId) throws SQLException, ClassNotFoundException {

        boolean deleted = false;

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        int delete = stmt.executeUpdate("DELETE FROM " + userType + "s WHERE " + userType + "Id=" + userId);

        if (delete != 0) {
            deleted = true;
        }

        return deleted;
    }
}
