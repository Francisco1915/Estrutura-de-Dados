package Structures.Double;

import Structures.Interfaces.ListADT;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class DoubleList<T> implements ListADT<T> {

    protected DoubleNode<T> head, tail;
    protected int modCount, count;

    private class DoubleListIterator<E> implements Iterator<E> {

        private DoubleNode<E> current = (DoubleNode<E>) DoubleList.this.head;
        private DoubleNode<E> previous = null;
        private int expectModCount = DoubleList.this.modCount;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            if (this.expectModCount != DoubleList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            return (this.current != null);
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No Such Element");
            }
            if (this.expectModCount != DoubleList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            E result = this.current.getElement();
            this.previous = this.current;
            this.current = this.current.getNext();
            this.okToRemove = true;
            return result;
        }

        @Override
        public void remove() {
            if (this.expectModCount != DoubleList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            if (this.okToRemove == true) {
                try {
                    DoubleList.this.remove((T) this.previous.getElement());
                    this.okToRemove = false;
                    this.expectModCount++;
                } catch (EmptyCollectionException | ElementNotFoundException ex) {
                    System.out.println(ex);
                }
            } else {
                throw new IllegalStateException("Method next() not been called (okToRemove false)");
            }            
        }  
    }

    public DoubleList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public DoubleList(T element) {
        this.head = new DoubleNode(element);
        this.tail = new DoubleNode(element);
        this.count = 1;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        DoubleNode<T> removeNode = this.head;
        this.head = this.head.getNext();

        if (this.head == null) {
            this.tail = null;
        } else {
            this.head.setPrevious(null);
            removeNode.setNext(null);
        }

        this.count--;
        this.modCount++;
        return removeNode.getElement();
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        DoubleNode<T> removeNode = this.tail;
        this.tail = this.tail.getPrevious();

        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
            removeNode.setPrevious(null);
        }

        this.count--;
        this.modCount++;
        return removeNode.getElement();

    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        if (!this.contains(element)) {
            throw new ElementNotFoundException("Element not found");
        }

        DoubleNode<T> removeNode = this.find(element);
        DoubleNode<T> previous = removeNode.getPrevious();

        if (this.head == removeNode) {
            this.removeFirst();
        } else if (this.tail == removeNode) {
            this.removeLast();
        } else {
            previous.setNext(removeNode.getNext());
            removeNode.getNext().setPrevious(previous);
            removeNode.setNext(null);
            removeNode.setPrevious(null);
            this.count--;
            this.modCount++;
        }
        return removeNode.getElement();
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }
        return this.head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }
        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return this.find(target) != null;
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
    public Iterator<T> iterator() {
        DoubleListIterator<T> iterator = new DoubleListIterator();
        return iterator;
    }

    @Override
    public String toString() {
        String result = "";

        DoubleNode<T> current = this.head;

        while (current != null) {
            result = result + (current.getElement()).toString() + "\n";
            current = current.getNext();
        }

        return result;
    }

    public DoubleNode<T> find(T element) {
        if (!this.isEmpty()) {
            DoubleNode<T> current = this.head;
            while (current != null) {

                if (current.getElement().equals(element)) {
                    return current;
                }

                current = current.getNext();
            }
        }
        return null;
    }
}
