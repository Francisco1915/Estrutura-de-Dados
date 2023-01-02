package Structures.Linked;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class LinkedList<T> implements ListADT<T> {

    protected LinearNode<T> head, tail;
    protected int count, modCount;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        LinearNode<T> removeNode = this.head;
        this.head = this.head.getNext();

        if (this.head == null) {
            this.tail = null;
        } else {
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

        LinearNode<T> previous = null;
        LinearNode<T> current = this.head;

        while (current.getNext() != null) {
            previous = current;
            current = current.getNext();
        }

        LinearNode<T> result = this.tail;
        this.tail = previous;
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
        }

        this.count--;
        this.modCount++;
        return result.getElement();
    }

    /**
     * Removes the first instance of the specified element from this list and
     * returns a reference to it.Throws an EmptyListException if the list is
     * empty. Throws a NoSuchElementException if the specified element is not
     * found in the list.
     *
     * @param targetElement
     */
    @Override
    public T remove(T targetElement) throws EmptyCollectionException, ElementNotFoundException {

        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        boolean found = false;

        LinearNode<T> previous = null;
        LinearNode<T> current = this.head;

        while (current != null && !found) {
            if (targetElement.equals(current.getElement())) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Element nor found");
        }

        if (size() == 1) {
            this.head = this.tail = null;
        } else if (current.equals(this.head)) {
            this.head = current.getNext();
        } else if (current.equals(this.tail)) {
            this.tail = previous;
            this.tail.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }

        this.count--;

        return current.getElement();
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }
        return this.head.getElement();
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty");
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
        LinkedListIterator<T> iterator = new LinkedListIterator();
        return iterator;
    }

    @Override
    public String toString() {
        String result = "";

        LinearNode<T> current = this.head;

        while (current != null) {
            result = result + (current.getElement()).toString() + "\n";
            current = current.getNext();
        }
        return result;
    }

    public LinearNode<T> find(T element) {
        if (!this.isEmpty()) {
            LinearNode<T> current = this.head;
            while (current != null) {

                if (current.getElement().equals(element)) {
                    return current;
                }

                current = current.getNext();
            }
        }
        return null;
    }

    public void replace(T existingElement, T newElement) {
        if (!this.isEmpty()) {
            LinearNode<T> temp = this.head;
            while (temp != null) {
                if (temp.getElement().equals(existingElement)) {
                    replace(temp.getElement(), newElement);
                }
                temp = temp.getNext();
            }
        }
    }

    private class LinkedListIterator<E> implements Iterator<E> {

        private LinearNode<E> current = (LinearNode<E>) LinkedList.this.head;
        private LinearNode<E> previous = null;
        private int expectModCount = LinkedList.this.modCount;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            if (this.expectModCount != LinkedList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            return (this.current != null);
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No Such Element");
            }
            if (this.expectModCount != LinkedList.this.modCount) {
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
            if (this.expectModCount != LinkedList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            if (this.okToRemove == true) {
                try {
                    LinkedList.this.remove((T) this.previous.getElement());
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
}
