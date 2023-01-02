package Structures.Linked;

import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.StackADT;

public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int count;

    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }

    @Override
    public void push(T element) {

        LinearNode<T> newNode = new LinearNode(element);

        if (this.top == null) {

            this.top = newNode;
            this.count++;
        } else {

            newNode.setNext(this.top);
            this.top = newNode;
            this.count++;
        }
    }

    @Override
    public T pop() throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Stack");
        }

        LinearNode<T> removeNode = this.top;
        this.top = removeNode.getNext();
        this.count--;
        return removeNode.getElement(); 
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty stack");
        }
        return this.top.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        String result = "";
        try {
            result = "A stack tem " + this.size() + " elementos sendo o primeiro: " + this.peek() + ".";
        } catch (EmptyCollectionException ex) {
            System.out.println(ex);
        }
        return result;
    }

}
