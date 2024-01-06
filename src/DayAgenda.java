/* DayAgenda class
 * @description: Node class for the binary search tree.
 * @author: Amun Ahmad
 * @version: 5/16/2023
 */

import java.util.PriorityQueue;

public class DayAgenda<T> {
    private PriorityQueue<T> dayAgenda;
    private DayAgenda<T> left;
    private DayAgenda<T> right;

    DayAgenda(PriorityQueue<T> dayAgenda, T event) {
        this.dayAgenda = dayAgenda;
        (this.dayAgenda).add(event);
        this.left = null;
        this.right = null;
    }

    /* METHODS */

    public void addEvent(T event) {
        this.dayAgenda.add(event);
    }

    /* GETTERS */

    public PriorityQueue<T> getAgenda() {
        return this.dayAgenda;
    }

    public DayAgenda<T> getLeft() {
        return this.left;
    }

    public DayAgenda<T> getRight() {
        return this.right;
    }

    /* SETTERS */

    public void setLeft(DayAgenda<T> dayAgenda) {
        this.left = dayAgenda;
    }

    public void setRight(DayAgenda<T> dayAgenda) {
        this.right = dayAgenda;
    }
}
