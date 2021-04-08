package bean;

import java.sql.Timestamp;

/**
 * @author jackb
 */
public class Lesson {

    private int lessonId;
    private Timestamp timeSlot;
    private int courseId;

    //Zero-Parameter Constructor
    public Lesson()
    {

        lessonId = 0;
        timeSlot = null;
        courseId = 0;

    }

    //Overloaded Constructor
    public Lesson(int lessonIdIn, Timestamp timeSlotIn, int courseIdIn)
    {

        lessonId = lessonIdIn;
        timeSlot = timeSlotIn;
        courseId = courseIdIn;

    }

    //Getters
    public int getLessonId() {return lessonId;}

    public Timestamp getTimeSlot() {return timeSlot;}

    public int getCourseId() {return courseId;}

    //Setters
    public void setLessonId(int lessonIdIn) {lessonId = lessonIdIn;}

    public void setTimeSlot(Timestamp timeSlotIn) {timeSlot = timeSlotIn;}

    public void setCourseId(int courseIdIn) {courseId = courseIdIn;}

}