package Structures.Double;

import Structures.Interfaces.OrderedListADT;

public class DoubleLinkedOrderedList<T> extends DoubleList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    public DoubleLinkedOrderedList(T element) {
        super(element);
    }

    @Override
    public void add(T element) {

        if (!(element instanceof Comparable)) {
            throw new ClassCastException("Element not comparble");
        }
        
        Comparable temp = (Comparable) element;
        
        DoubleNode<T> newNode = new DoubleNode(element);
        DoubleNode<T> current = this.head;

        if (this.isEmpty()) {

            this.head = newNode;
            this.tail = newNode;
        } else if (temp.compareTo(this.head.getElement()) <= 0) {

            this.head.setPrevious(newNode);
            newNode.setNext(this.head);
            this.head = newNode;
        } else if (temp.compareTo(this.tail.getElement()) >= 0) {

            this.tail.setNext(newNode);
            newNode.setPrevious(this.tail);
            this.tail = newNode;
        } else {

            while (temp.compareTo(current.getElement()) > 0) {
                current = current.getNext();
            }
            newNode.setNext(current);
            newNode.setPrevious(current.getPrevious());
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
        }
        this.modCount++;
        this.count++;
    }
}
