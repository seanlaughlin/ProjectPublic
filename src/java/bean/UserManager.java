package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jackb
 */
public class UserManager {

    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\seanl\\Documents\\NetBeansProjects\\GCU_Skills\\data\\GCU_SkillsDB.accdb";
    PassHash passHash = new PassHash();

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

    public Student logInStudent(String emailAddress, String password) throws SQLException, ClassNotFoundException {

        int studentId = 0;
        String realPassword = "";
        String firstName = "";
        String lastName = "";
        Date dob = new Date();
        String phoneNumber = "";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        //Selects every matching entry in the customers table in the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM Students WHERE EmailAddress= '" + emailAddress + "'");

        //Executes for every entry ing the results set
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
        boolean isPassValid = passHash.checkPassword(emailAddress, password);
        if (isPassValid) {
            Student student = new Student(emailAddress, password, firstName, lastName, dob, phoneNumber);
            student.setStudentId(studentId);
            return student;
        } else {
            return null;
        }
    }

    public Student updateAttribute(String parameterName, String parameter, Student student) throws SQLException, ClassNotFoundException {
        
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
    
}

//    public HashMap<Integer, Student> loadAllStudents(){
//
//    //String as key is the Customers Username
//    HashMap<Integer, Student> studentsHashMap = new HashMap();
//
//    try
//    {       
//
//       Class.forName(driver);
//       Connection conn = DriverManager.getConnection(connectionString);
//       Statement stmt = conn.createStatement();
//
//       //Selects every entry in the customers table in the database
//       ResultSet rs= stmt.executeQuery("SELECT * FROM Students;");
//
//       //Executes for every entry ing the results set
//       while(rs.next())
//       {
//
//           //Gets the values from the result set entry and creates a customer object using them
//           int studentId = rs.getInt("id");
//           String email = rs.getString("EmailAddress");
//           String password = rs.getString("Password");
//           String firstName = rs.getString("FirstName");
//           String lastName = rs.getString("LastName");
//           String dob = rs.getString("DateOfBirth");
//           String phoneNumber = rs.getString("PhoneNumber");
////           int courseId = rs.getInt(1);
////
////           Course studentCourse = new Course(courseId);
//Student students = new Student(email, password, firstName, lastName, dob, phoneNumber);
////          Student students = new Student(email, password, firstName, lastName, dob, studentId, phoneNumber, studentCourse);
//
//           //Puts the student object into the hashmap
//           studentsHashMap.put(studentId, students);          
//
//       }
//
//       conn.close();
//
//    }
//
//    catch(ClassNotFoundException | SQLException ex)
//    {
//
//        String message = ex.getMessage();
//
//    }
//
//    finally
//    {
//
//        //Loads the orders and orderlines into the customerHashMap and returns it
//        CourseManager cm = new CourseManager();
//        studentsHashMap = cm.loadStudentCourse(studentsHashMap);
//        studentsHashMap = cm.loadCourseLessons(studentsHashMap);
//        return studentsHashMap;
//
//    }    
//}
//    
//}
