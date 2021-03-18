package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author jackb
 */
public class CourseManager {

    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\seanl\\Documents\\NetBeansProjects\\GCU\\data\\GCU_SkillsDB.accdb";

    public ArrayList<Course> loadStudentCourses(int studentIdIn) {

        ArrayList<Course> studentCourses = new ArrayList();

        try
        {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT Enrollments.StudentId, Enrollments.CourseId, Courses.CourseId, Courses.CourseName, Courses.CourseStatus, Courses.TutorId FROM Courses INNER JOIN Enrollments ON Courses.CourseId = Enrollments.CourseId WHERE Enrollments.CourseId = Courses.CourseId AND Enrollments.StudentId = '" +  studentIdIn + "'");



            //Executes while the results set has a next value
            while (rs.next())
            {

                //Loads all values for an entry into an course object
                int courseId = rs.getInt("CourseId");
                String courseName = rs.getString("CourseName");
                String courseStatus = rs.getString("CourseStatus");
                int tutorId = rs.getInt("TutorId");

                new Tutor();
                Tutor courseTutor;
                courseTutor = loadCourseTutor(courseId);

                //Creates a course object with the data from the database and calls the loadCourseLessons method to populate that course with its associated lessons
                Course loadedCourse = new Course(courseId, courseName, courseStatus, loadCourseLessons(courseId), courseTutor);

                studentCourses.add(loadedCourse);

            }

            conn.close();

        }

        catch (Exception ex)
        {

            String message = ex.getMessage();

        }

        finally
        {

            //returns the student object populated with a course
            return studentCourses;

        }

    }

    public Tutor loadCourseTutor(int courseId)
    {

        Tutor courseTutor = new Tutor();

        try
        {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Tutors INNER JOIN Courses ON Tutors.TutorId = Courses.TutorId WHERE CourseId = '" + courseId + "'");

            while (rs.next())
            {

                int tutorId = rs.getInt("TutorId");
                String emailAddress = rs.getString("EmailAddress");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                String role = rs.getString("Role");
                String department = rs.getString("Department");
                int payGrade = rs.getInt("PayGrade");

                courseTutor = new Tutor(emailAddress, firstName, lastName, dob, role, department, tutorId, payGrade);

            }

        }

        catch (ClassNotFoundException | SQLException ex)
        {
            String message = ex.getMessage();
        }

        finally
        {
            return courseTutor;
        }

    }

    public ArrayList<Lesson> loadAllLessons() {

        //Initialises an ArrayList object
        ArrayList<Lesson> lessonsArrayList = new ArrayList<>();

        try
        {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Lessons table in the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lessons");

            //Populates the lessonsArrayList with all lessons in the database
            while (rs.next()) {

                //Loads all values for an entry into an lesson object
                int lessonId = rs.getInt("LessonId");
                Date timeSlot = rs.getDate("TimeSlot");
                int courseId = rs.getInt("CourseId");

                Lesson lesson = new Lesson(lessonId, timeSlot, courseId);

                //Adds the lesson object to the lessonsArrayList
                lessonsArrayList.add(lesson);

            }

        }

        catch (ClassNotFoundException | SQLException ex)
        {

            String message = ex.getMessage();

        }

        finally
        {

            //Returns an ArrayList of all objects in the database
            return lessonsArrayList;

        }

    }

    public ArrayList<Lesson> loadCourseLessons(int courseId) {

        //Calls the loadAllLessons object to get an ArrayList of all lessons in the database
        ArrayList<Lesson> allLessons = loadAllLessons();

        //Creates a condition which will be used select only the lessons for an associated courseId
        Predicate<Lesson> byCourseId = lesson -> lesson.getCourseId() == courseId;

        //Filters all lessons by courseId and selects those that match the students course
        ArrayList<Lesson> result = (ArrayList<Lesson>) allLessons.stream().filter(byCourseId).collect(Collectors.toList());

        //returns the lessons for the students course in the form of an ArrayList
        return result;
    }
    
    public Course updateAttribute(String parameterName, String parameter, Course course) throws SQLException, ClassNotFoundException {
        
        //Check name of parameter submitted from web form (through servlet) and update course object
        switch (parameterName) {
            case "coursestatus":
                course.setCourseStatus(parameter);
                break;

            case "coursename":
                course.setCourseName(parameter);
                break;

        }
        
        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE Courses SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE CourseId= " + course.getCourseId());
        conn.close();
        
        //Return updated student object
        return course;
    }
}