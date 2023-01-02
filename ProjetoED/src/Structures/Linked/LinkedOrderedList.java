package Structures.Linked;

import Structures.Interfaces.OrderedListADT;

public class LinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    public LinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) {

        if (!(element instanceof Comparable)) {
            throw new ClassCastException("Element not comparble");
        }

        Comparable temp = (Comparable) element;

        LinearNode<T> newNode = new LinearNode(element);
        LinearNode<T> current = this.head;
        LinearNode<T> previous = null;

        if (this.isEmpty()) {

            this.head = newNode;
            this.tail = newNode;
        } else if (temp.compareTo(this.head.getElement()) <= 0) {

            newNode.setNext(this.head);
            this.head = newNode;
        } else if (temp.compareTo(this.tail.getElement()) >= 0) {

            this.tail.setNext(newNode);
            this.tail = newNode;
        } else {

            while (temp.compareTo(current.getElement()) >= 0) {
                previous = current;
                current = current.getNext();
            }
            newNode.setNext(current);
            previous.setNext(newNode);
        }
        this.count++;
    }
}
