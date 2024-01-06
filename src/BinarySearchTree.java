/* BinarySearchTree class
 * @description: Binary search tree implementation.
 * @author: Amun Ahmad
 * @version: 5/16/2023
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class BinarySearchTree<T extends Event> {
    private DayAgenda<T> root;

    BinarySearchTree() {
        this.root = null;
    }

    /* METHODS */

    public DayAgenda<T> addNode(DayAgenda<T> node, T event) {
        if (node != null) {
            LocalDate nodeDate = node.getAgenda().peek().getDate();
            LocalDate eventDate = event.getDate();

            if (eventDate.compareTo(nodeDate) == 0) {
                node.addEvent(event);
            } else if (eventDate.compareTo(nodeDate) < 0) {
                if (node.getLeft() != null) {
                    node.setLeft(addNode(node.getLeft(), event));
                } else {
                    node.setLeft(new DayAgenda<T>(new PriorityQueue<T>(), event));
                }
            } else if (eventDate.compareTo(nodeDate) > 0) {
                if (node.getRight() != null) {
                    node.setRight(addNode(node.getRight(), event));
                } else {
                    node.setRight(new DayAgenda<T>(new PriorityQueue<T>(), event));
                }
            }
        } else {
            return new DayAgenda<T>(new PriorityQueue<T>(), event);
        }

        return node;
    }

    public DayAgenda<T> searchNodes(DayAgenda<T> node, LocalDate eventDate) {
        if (node != null) {
            LocalDate nodeDate = node.getAgenda().peek().getDate();

            if (eventDate.compareTo(nodeDate) == 0) {
                return node;
            } else if (eventDate.compareTo(nodeDate) < 0) {
                return searchNodes(node.getLeft(), eventDate);
            } else if (eventDate.compareTo(nodeDate) > 0) {
                return searchNodes(node.getRight(), eventDate);
            }
        }

        return null;
    }

    public void grabNodeChildren(DayAgenda<T> node, ArrayList<T> nodes) {
        if (node == null) {
            return;
        }

        grabNodeChildren(node.getLeft(), nodes);
        for (T event: node.getAgenda()) {
            nodes.add(event);
        }
        grabNodeChildren(node.getRight(), nodes);
    }

    /* GETTERS */

    public DayAgenda<T> getRoot() {
        return this.root;
    }

    /* SETTERS */

    public void setRoot(DayAgenda<T> node) {
        this.root = node;
    }
}
