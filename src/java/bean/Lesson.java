package bean;

import java.util.Date;

/**
 * @author jackb
 */
public class Lesson {

    private int lessonId;
    private Date timeSlot;

    //Zero-Parameter Constructor
    public Lesson()
    {

        lessonId = 0;
        timeSlot = new Date();

    }

    //Overloaded Constructor
    public Lesson(int lessonIdIn, Date timeSlotIn)
    {

        lessonId = lessonIdIn;
        timeSlot = timeSlotIn;

    }

    //Getters
    public int getLessonId() {return lessonId;}

    public Date getTimeSlot() {return timeSlot;}

    //Setters
    public void setLessonId(int lessonIdIn) {lessonId = lessonIdIn;}

    public void setTimeSlot(Date timeSlotIn) {timeSlot = timeSlotIn;}


}
