package RobotPathPlanning;

public class Queue {
    // Custom queue implementation using linked list
    static class Node {
        int row; // row index
        int column; // column index
        Node next; // next node in queue

        // constructor
        Node(int row, int column) {
            this.row = row;
            this.column = column;
            this.next = null;
        }
    }

    private Node front; // ref to front of queue
    private Node rear; // ref to rear of queue

    // constructor to initialize empty queue
    Queue() {
        front = null;
        rear = null;
    }

    // Method to add an element to the rear of the queue
    void offer(int row, int column) {
        Node newNode = new Node(row, column);
        if (rear == null) {
            // If queue is empty, set front and rear to the new node
            front = newNode;
            rear = newNode;
        } else {
            // Otherwise, link the new node to the rear and update rear
            rear.next = newNode;
            rear = newNode;
        }
    }

    // Method to check if queue is empty
    boolean isEmpty() {
        return front == null;
    }

    // Method to remove & return element at front of queue
    Node poll() {
        if (isEmpty()) return null;
        Node temp = front;
        front = front.next;
        if (front == null) rear = null;
        return temp;
    }
}
