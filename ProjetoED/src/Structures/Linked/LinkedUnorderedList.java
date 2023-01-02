package Structures.Linked;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Interfaces.UnorderedListADT;


public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T>{

    @Override
    public void addFront(T element) {
        
        LinearNode<T> newNode = new LinearNode(element);

        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            newNode.setNext(this.head);
            this.head = newNode;
        }
        this.modCount++;
        this.count++;
    }

    @Override
    public void addRear(T element) {
        
        LinearNode<T> newNode = new LinearNode(element);
        
        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.modCount++;
        this.count++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
   
        LinearNode<T> newNode = new LinearNode(element);
        
        if (this.isEmpty()) {
            
            this.head = newNode;
            this.tail = newNode;
        } else {
            
            LinearNode<T> afterNode = this.find(target);
            
            newNode.setNext(afterNode.getNext());
            afterNode.setNext(newNode);
        }
        this.modCount++;
        this.count++;
    }
}
