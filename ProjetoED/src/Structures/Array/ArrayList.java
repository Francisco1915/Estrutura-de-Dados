package Structures.Array;

import Structures.Interfaces.ListADT;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ArrayList<T> implements ListADT<T> {

    private final int INICIAL_CAPACITY = 5;
    protected T[] list;
    protected int rear, modCount;

    public ArrayList() {
        this.list = (T[]) (new Object[this.INICIAL_CAPACITY]);
        this.rear = 0;
    }

    public ArrayList(int inicialCapacity) {
        this.list = (T[]) (new Object[inicialCapacity]);
        this.rear = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        T removeElement = this.list[0];
        this.list[0] = null;
        for (int i = 0; i < this.rear - 1; i++) {

            this.list[i] = this.list[i + 1];
        }
        this.modCount++;
        this.rear--;
        return removeElement;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }
        T removeElement = this.list[this.rear];
        this.list[this.rear] = null;
        this.modCount++;
        this.rear--;
        return removeElement;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        if (!this.contains(element)) {
            throw new ElementNotFoundException("Element not found");
        }

        int pos = this.find(element);
        this.list[pos] = null;

        for (int j = pos; pos < this.rear - 1; j++) {
            this.list[j] = this.list[j + 1];
        }

        this.modCount++;
        this.rear--;
        return element;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        return this.list[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        return this.list[this.rear - 1];
    }

    @Override
    public boolean contains(T target) {
        return this.find(target) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.rear == 0;
    }

    @Override
    public int size() {
        return this.rear;
    }

    @Override
    public Iterator<T> iterator() {
        ArrayIterator<T> iterator = new ArrayIterator();
        return iterator;
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < this.size(); i++) {
            result = result + (this.list[i].toString() + "\n");
        }

        return result;
    }

    public void expandCapacity() {
        T[] temp = (T[]) (new Object[size() * 2]);

        for (int i = 0; i < this.size(); i++) {

            temp[i] = this.list[i];
        }

        this.list = temp;
    }

    public int find(T element) {
        if (!this.isEmpty()) {
            for (int i = 0; i < this.size(); i++) {

                if (this.list[i].equals(element)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public class ArrayIterator<E> implements Iterator<E> {

        private int pos = 0, expectModCount = ArrayList.this.modCount;
        private boolean okToRemove = false;
        private E previous;

        public ArrayIterator() {
        }

        @Override
        public boolean hasNext() {
            if (this.expectModCount != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            return (this.pos < ArrayList.this.rear);
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No Such Element");
            }
            if (this.expectModCount != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            this.previous = (E) ArrayList.this.list[this.pos];
            this.pos++;
            this.okToRemove = true;
            return previous;
        }

        @Override
        public void remove() {
            if (this.expectModCount != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            if (this.okToRemove == true) {
                try {
                    ArrayList.this.remove((T) this.previous);
                    this.okToRemove = false;
                    this.expectModCount++;
                    this.pos--;
                } catch (EmptyCollectionException | ElementNotFoundException ex) {
                    System.out.println(ex);
                }
            } else {
                throw new IllegalStateException("Method next() not been called (okToRemove false)");
            }
        }
    }
}
