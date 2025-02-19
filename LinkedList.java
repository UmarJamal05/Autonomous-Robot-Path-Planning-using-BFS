package RobotPathPlanning;

public class LinkedList {
    public class Node {
        int[] data; // data stored in the node
        Node previous; // ref to the previous node
        Node next; // ref to the next node

        // Constructor to initialize node with data
        Node(int[] data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }

    private Node head; // ref to first node in list
    private Node tail; // ref to last node in list

    // Constructor to initialize an empty linked list
    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Method to add a new node at the end of the list
    public void add(int[] data) {
        Node newNode = new Node(data); // Create new node with given data
        if (head == null) { // If list is empty
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
    }

    // Method to get size of the linked list
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Method to get the data at a specified index
    public int[] get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
}
