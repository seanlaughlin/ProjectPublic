package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author jackb
 */
public class CourseManager {

    private final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private final String connectionString = "jdbc:ucanaccess://C:\\Users\\seanl\\Documents\\NetBeansProjects\\GCU_1\\data\\GCU_SkillsDB.accdb";

    //Adds course and returns int equal to courseId
    public int addCourse(String courseName, String courseStatus, int tutorId, String description) throws SQLException, ClassNotFoundException {

        int courseId = 0;

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO Courses(CourseName, CourseStatus, TutorId, Description) " + "VALUES('" + courseName + "', '" + courseStatus + "', '" + tutorId + "', '" + description + "')");

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                courseId = rs.getInt(1);

            }
            conn.close();

            return courseId;

        } catch (ClassNotFoundException | SQLException ex) {

            String message = ex.getMessage();
            return 0;

        }

    }

    public boolean addLessons(ArrayList<Lesson> lessons) throws ClassNotFoundException, SQLException {

        int updated = 0;
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();
        for (Lesson lesson : lessons) {
            updated = stmt.executeUpdate("INSERT INTO Lessons(LessonId, Timeslot, CourseId) VALUES(" + lesson.getLessonId() + "', '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lesson.getTimeSlot()) + "', '" + lesson.getCourseId());
        }
        conn.close();
        if (updated != 0) {
            return true;
        } else {
            return false;
        }
    }

    //Update attribute of Course in DB
    public boolean updateCourseAttribute(String parameterName, String parameter, int courseId) throws SQLException, ClassNotFoundException {

        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        int updated = stmt.executeUpdate("UPDATE Courses SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE CourseId= " + courseId);
        conn.close();

        //true for success, false for failure
        if (updated != 0) {
            return true;
        } else {
            return false;
        }

    }

    //Returns all courses starting in the future, for display to prospective students
    public ArrayList<Course> loadFutureCourses() throws SQLException, ClassNotFoundException {

        ArrayList<Course> allCourses = new ArrayList();

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        //Selects all entries in the Courses table of the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM Courses");

        while (rs.next()) {

            //Loads all values for an entry into an course object
            int courseId = rs.getInt("CourseId");
            String courseName = rs.getString("CourseName");
            String courseStatus = rs.getString("CourseStatus");
            String description = rs.getString("Description");

            Tutor courseTutor;
            courseTutor = loadCourseTutor(courseId);

            //Creates a course object with the data from the database and calls the loadCourseLessons method to populate that course with its associated lessons
            Course loadedCourse = new Course(courseId, courseName, courseStatus, loadCourseLessons(courseId), courseTutor, description);
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);

            //Stops courses which haven't had lessons added yet from being processed
            if (loadedCourse.getLessons().isEmpty()) {

            } //Check if course date is later than today and add course to array
            else if (loadedCourse.getLessons().get(0).getTimeSlot().after(today.getTime())) {
                allCourses.add(loadedCourse);
            }
        }

        conn.close();

        return allCourses;
    }

    //Create new entry in Enrollments table in DB for student&course
    public boolean enrollStudent(int studentId, int courseId) throws SQLException, ClassNotFoundException {
        try {
            //Connect to database and insert studentid and courseid to table to record enrollment
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Enrollments(StudentId, CourseId, CourseStatus) " + "VALUES('" + studentId + "', '" + courseId + "', 'Beginner')");

            //If key was generated from statement, return true to confirm enrollment success, otherwise return false
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    //Remove entry in Enrollments table by student and course id
    public boolean unenrollStudent(int studentId, int courseId) throws SQLException, ClassNotFoundException {

        boolean deleted = false;

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();
        int delete = stmt.executeUpdate("DELETE FROM Enrollments WHERE studentId=" + studentId + " AND courseId=" + courseId);
        if (delete != 0) {
            deleted = true;
        }

        return deleted;
    }

    //Delete entry in Courses table by Course ID
    public boolean deleteCourse(int courseId) throws SQLException, ClassNotFoundException {

        boolean deleted = false;

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();
        int delete = stmt.executeUpdate("DELETE FROM Courses WHERE courseId=" + courseId);
        if (delete != 0) {
            deleted = true;
        }

        return deleted;
    }
    
        //Delete entry in Lessons table by Lesson ID
        public boolean deleteLesson(int lessonId) throws SQLException, ClassNotFoundException {

        boolean deleted = false;

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();
        int delete = stmt.executeUpdate("DELETE FROM Lessons WHERE lessonId=" + lessonId);
        if (delete != 0) {
            deleted = true;
        }

        return deleted;
    }

    //Load all Courses for Student by Student ID
    public ArrayList<Course> loadStudentCourses(int studentIdIn) {

        ArrayList<Course> studentCourses = new ArrayList();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT Enrollments.StudentId, Enrollments.CourseId, Enrollments.CourseStatus, Courses.CourseId, Courses.CourseName, Courses.TutorId, Courses.Description FROM Courses INNER JOIN Enrollments ON Courses.CourseId = Enrollments.CourseId WHERE Enrollments.CourseId = Courses.CourseId AND Enrollments.StudentId = '" + studentIdIn + "'");

            //Executes while the results set has a next value
            while (rs.next()) {

                //Loads all values for an entry into an course object
                int courseId = rs.getInt("CourseId");
                String courseName = rs.getString("CourseName");
                String courseStatus = rs.getString("CourseStatus");
                int tutorId = rs.getInt("TutorId");
                String description = rs.getString("Description");

                new Tutor();
                Tutor courseTutor;
                courseTutor = loadCourseTutor(courseId);

                //Creates a course object with the data from the database and calls the loadCourseLessons method to populate that course with its associated lessons
                Course loadedCourse = new Course(courseId, courseName, courseStatus, loadCourseLessons(courseId), courseTutor, description);

                studentCourses.add(loadedCourse);

            }

            conn.close();

        } catch (Exception ex) {

            String message = ex.getMessage();

        } finally {

            //returns the student object populated with a course
            return studentCourses;

        }

    }

    //Load all Courses for Tutor by Tutor ID
    public Tutor loadCourseTutor(int courseId) {

        Tutor courseTutor = new Tutor();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Tutors INNER JOIN Courses ON Tutors.TutorId = Courses.TutorId WHERE CourseId = '" + courseId + "'");

            while (rs.next()) {

                int tutorId = rs.getInt("TutorId");
                String emailAddress = rs.getString("EmailAddress");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                String role = rs.getString("Role");
                String department = rs.getString("Department");
                int payGrade = rs.getInt("PayGrade");

                courseTutor = new Tutor(emailAddress, firstName, lastName, dob, tutorId, role, department, payGrade);

            }

        } catch (ClassNotFoundException | SQLException ex) {
            String message = ex.getMessage();
        } finally {
            return courseTutor;
        }

    }

    //Load all Lessons in DB
    public ArrayList<Lesson> loadAllLessons() {

        //Initialises an ArrayList object
        ArrayList<Lesson> lessonsArrayList = new ArrayList<>();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Lessons table in the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lessons");

            //Populates the lessonsArrayList with all lessons in the database
            while (rs.next()) {

                //Loads all values for an entry into an lesson object
                int lessonId = rs.getInt("LessonId");
                Timestamp timeSlot = rs.getTimestamp("TimeSlot");
                int courseId = rs.getInt("CourseId");

                Lesson lesson = new Lesson(lessonId, timeSlot, courseId);

                //Adds the lesson object to the lessonsArrayList
                lessonsArrayList.add(lesson);

            }

        } catch (ClassNotFoundException | SQLException ex) {

            String message = ex.getMessage();

        } finally {

            //Returns an ArrayList of all objects in the database
            return lessonsArrayList;

        }

    }
    
    //Add Lesson to Lessons table in DB by Course ID
    public boolean addCourseLesson(int courseId, Timestamp timeSlot) throws ClassNotFoundException{
         try {
            //Connect to database and insert courseId and timeSlot to Lessons table
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Lessons(Timeslot, CourseId) " + "VALUES('" + timeSlot + "', '" + courseId+"')");

            //If key was generated from statement, return true to confirm success, otherwise return false
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    //Load all Courses in DB
    public ArrayList<Course> loadAllCourses() {

        //Initialises an ArrayList object
        ArrayList<Course> allCourses = new ArrayList<>();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table in the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Courses");

            //Populates the allCourses list with all courses in the database
            while (rs.next()) {

                //Loads all values for an entry into a course object
                int courseId = rs.getInt("CourseId");
                String courseName = rs.getString("CourseName");
                int tutorId = rs.getInt("TutorId");
                String description = rs.getString("Description");
                String courseStatus = rs.getString("CourseStatus");

                Tutor courseTutor = loadCourseTutor(courseId);
                ArrayList<Lesson> courseLessons = loadCourseLessons(courseId);
                Course course = new Course(courseId, courseName, courseStatus, courseLessons, courseTutor, description);

                //Adds the lesson object to the array list
                allCourses.add(course);

            }

        } catch (ClassNotFoundException | SQLException ex) {

            String message = ex.getMessage();

        } finally {

            //Returns an ArrayList of all objects in the database
            return allCourses;

        }

    }

    //Load all Lessons for Course by Course ID
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

    //Update Lesson Status in Enrollments table in DB by Student ID
    public Course updateLessonsStatus(String parameterName, String parameter, Course course, int studentId) throws SQLException, ClassNotFoundException {

        //Uupdate course object courseStatus
        course.setCourseStatus(parameter);

        //Connect to database and update parameter
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(connectionString);
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE Enrollments" + " SET " + parameterName + " = " + "\"" + parameter + "\" " + "WHERE CourseId= " + course.getCourseId() + " AND StudentId= " + studentId);
        conn.close();

        //Return updated course object
        return course;
    }
    
        public Course loadCourse(int courseId) throws SQLException, ClassNotFoundException {

        Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            Course loadedCourse = null;
            
            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Courses WHERE CourseId= " + courseId);

            //Executes while the results set has a next value
            while (rs.next()) {

                //Loads all values for an entry into an course object
                String courseName = rs.getString("CourseName");
                String courseStatus = rs.getString("CourseStatus");
                String description = rs.getString("Description");
                
                Tutor courseTutor = loadCourseTutor(courseId);

                //Creates a course object with the data from the database and calls the loadCourseLessons method to populate that course with its associated lessons
                loadedCourse = new Course(courseId, courseName, courseStatus, loadCourseLessons(courseId), courseTutor, description);
            }
            conn.close();
            return loadedCourse;     
    }

    //Load all Courses for a Tutor by Tutor ID
    public ArrayList<Course> loadTutorCourses(int tutorId) {

        ArrayList<Course> tutorCourses = new ArrayList();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT * FROM Courses WHERE TutorId = '" + tutorId + "'");

            //Executes while the results set has a next value
            while (rs.next()) {

                //Loads all values for an entry into an course object
                int courseId = rs.getInt("CourseId");
                String courseName = rs.getString("CourseName");
                String courseStatus = rs.getString("CourseStatus");
                String description = rs.getString("Description");

                new Tutor();
                Tutor courseTutor;
                courseTutor = loadCourseTutor(courseId);

                //Creates a course object with the data from the database and calls the loadCourseLessons method to populate that course with its associated lessons
                Course loadedCourse = new Course(courseId, courseName, courseStatus, loadCourseLessons(courseId), courseTutor, description);

                //Get Calendar object of todays date
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);

                //Check if last lesson date is later than today and add course to array (prevents old courses from loading)
                if (loadedCourse.getLessons().get(loadedCourse.getLessons().size() - 1).getTimeSlot().after(today.getTime())) {
                    tutorCourses.add(loadedCourse);
                }
            }

            conn.close();

        } catch (Exception ex) {

            String message = ex.getMessage();

        } finally {

            //returns the student object populated with a course
            return tutorCourses;

        }

    }

    //Load all Students on a particular Course by Course ID
    public ArrayList<Student> loadCourseStudents(int courseId) {

        ArrayList<Student> studentsOnCourse = new ArrayList<>();

        try {

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();

            //Selects all entries in the Courses table of the database
            ResultSet rs = stmt.executeQuery("SELECT Students.StudentId, Students.EmailAddress, Students.FirstName, Students.LastName, Students.DateOfBirth, Students.PhoneNumber, Enrollments.CourseId FROM Enrollments INNER JOIN Students ON Enrollments.StudentId = Students.StudentId WHERE Enrollments.CourseId =  '" + courseId + "'");

            while (rs.next()) {

                int studentId = rs.getInt("StudentId");
                String emailAddress = rs.getString("EmailAddress");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                String phoneNumber = rs.getString("PhoneNumber");

                ArrayList<Course> studentCourse = loadStudentCourses(studentId);

                Student loadedStudent = new Student(emailAddress, firstName, lastName, dob, studentId, phoneNumber, studentCourse);

                studentsOnCourse.add(loadedStudent);

            }

        } catch (Exception ex) {

            String message = ex.getMessage();

        } finally {

            return studentsOnCourse;

        }

    }

}
