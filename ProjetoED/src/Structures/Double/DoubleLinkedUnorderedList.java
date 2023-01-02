package Structures.Double;

import Structures.Interfaces.UnorderedListADT;

public class DoubleLinkedUnorderedList<T> extends DoubleList<T> implements UnorderedListADT<T> {

    @Override
    public void addFront(T element) {

        DoubleNode<T> newNode = new DoubleNode(element);

        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            this.head.setPrevious(newNode);
            newNode.setNext(this.head);
            this.head = newNode;
        }
        this.modCount++;
        this.count++;
    }

    @Override
    public void addRear(T element) {

        DoubleNode<T> newNode = new DoubleNode(element);

        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            this.tail.setNext(newNode);
            newNode.setPrevious(this.tail);
            this.tail = newNode;
        }
        this.modCount++;
        this.count++;
    }

    @Override
    public void addAfter(T element, T target) {

        DoubleNode<T> newNode = new DoubleNode(element);

        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            DoubleNode<T> afterNode = this.find(target);
            
            afterNode = afterNode.getNext();
            newNode.setNext(afterNode);
            newNode.setPrevious(afterNode.getPrevious());
            afterNode.getPrevious().setNext(newNode);
            afterNode.setPrevious(newNode);
        }
        this.modCount++;
        this.count++;
    }
}
