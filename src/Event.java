/* Event class
 * @description: Events to be stored in the trees.
 * @author: Amun Ahmad
 * @version: 5/16/2023
 */

import java.time.LocalDate;

public class Event implements Comparable<Event> {
    private String eventName;
    private String eventNotes;
    private LocalDate date;
    private int startTime;
    private int endTime;

    Event(String eventName, String eventNotes, LocalDate date, int startTime, int endTime) {
        this.eventName = eventName;
        this.eventNotes = eventNotes;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /* METHODS */

    @Override
    public String toString() {
        return "\nEvent Name: " + this.eventName +
               "\nEvent Notes: " + this.eventNotes +
               "\nEvent Date: " + this.date.toString() +
               "\nEvent Start Time: " + this.startTime +
               "\nEvent End Time: " + this.endTime + "\n";
    }

    public String toEdit() {
        return "\n1. Event Name: " + this.eventName +
               "\n2. Event Notes: " + this.eventNotes +
               "\n3. Event Date: " + this.date.toString() +
               "\n4. Event Start Time: " + this.startTime +
               "\n5. Event End Time: " + this.endTime + "\n"; 
    }

    public String toFile() {
        return this.eventName + " !next " +
               this.eventNotes + " !next " +
               this.date.toString() + " !next " +
               this.startTime + " !next " +
               this.endTime + "\n";
    }

    public int compareTo(Event other) {
        if (this.startTime > other.getStartTime()) {
            return 1;
        } else if (this.startTime < other.getStartTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /* GETTERS */

    public String getName() {
        return this.eventName;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int getStartTime() {
        return this.startTime;
    }
}
