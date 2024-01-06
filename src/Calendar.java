/* Calendar class
 * @description: Calendar interface for programmer.
 * @author: Amun Ahmad
 * @version: 5/16/2023
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Calendar {
    private String calendarName;
    private BinarySearchTree<Event> days;
    private ArrayList<Event> events;

    Calendar(String calendarName) {
        this.calendarName = calendarName;
        this.days = new BinarySearchTree<Event>();
        this.events = new ArrayList<Event>();
    }

    /* METHODS */

    public void addEvent(Event event) {
        if (this.days.getRoot() == null) {
            this.days.setRoot(this.days.addNode(this.days.getRoot(), event));
        } else {
            this.days.addNode(this.days.getRoot(), event);
        }
    }

    public ArrayList<Event> getAllEvents() {
        this.events.clear();
        this.days.grabNodeChildren(this.days.getRoot(), this.events);

        return this.events;
    }

    public ArrayList<Event> getDayAgenda(LocalDate date) {
        DayAgenda<Event> node = this.days.searchNodes(this.days.getRoot(), date);

        if (node != null) {
            PriorityQueue<Event> dayAgenda = node.getAgenda();
            ArrayList<Event> sortedAgenda = new ArrayList<Event>();

            for (int i = 0; i <= dayAgenda.size(); i++) {
                sortedAgenda.add(dayAgenda.peek());
                dayAgenda.remove();
            }

            for (int i = sortedAgenda.size() - 1; i >= 0; i--) {
                dayAgenda.add(sortedAgenda.get(i));
            }

            return sortedAgenda;
        }

        return null;
    }

    public void deleteEvent(Event event) {
        LocalDate eventDate = event.getDate();
        DayAgenda<Event> node = this.days.searchNodes(this.days.getRoot(), eventDate);

        if (node != null) {
            PriorityQueue<Event> dayAgenda = node.getAgenda();
            ArrayList<Event> agendaWorkbench = new ArrayList<Event>();

            for (int i = 0; i <= dayAgenda.size(); i++) {
                agendaWorkbench.add(dayAgenda.peek());
                dayAgenda.remove();
            }

            for (int i = 0; i < agendaWorkbench.size(); i++) {
                if (agendaWorkbench.get(i) == event) {
                    agendaWorkbench.remove(i);
                }
            }

            for (int i = agendaWorkbench.size() - 1; i >= 0; i--) {
                dayAgenda.add(agendaWorkbench.get(i));
            }
        }
    }

    public void resetCalendar() {
        this.days.setRoot(null);
    }

    /* GETTERS */

    public String getCalendarName() {
        return this.calendarName;
    }

    /* SETTERS */

    public void setCalendarName(String newName) {
        this.calendarName = newName;
    }
}
