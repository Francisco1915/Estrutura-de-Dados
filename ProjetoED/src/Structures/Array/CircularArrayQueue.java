package Structures.Array;

import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;

public class CircularArrayQueue<T> implements QueueADT<T> {

    private final int DEFAULT_CAPACITY = 10;

    private T[] queue;

    private int front, rear, count;

    public CircularArrayQueue() {
        this.front = this.rear = this.count = 0;
        this.queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public CircularArrayQueue(int InicialCapacity) {
        this.front = this.rear = this.count = 0;
        this.queue = (T[]) (new Object[InicialCapacity]);
    }

    @Override
    public void enqueue(T element) {
        if (this.size() == this.queue.length) {
            this.expandCapacity();
        }

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        this.count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        this.count--;
        T result = this.queue[this.front];
        this.queue[this.front] = null;
        this.front = (this.front + 1) % this.queue.length;
        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Queue is empty");
        }

        return this.queue[this.front];
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
            result = "A Queue tem " + this.size() + " elementos sendo o primeiro: " + this.first() + ".";
        } catch (EmptyCollectionException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public void expandCapacity() {
        T[] temp = (T[]) (new Object[size() * 2]);

        for (int i = 0; i < this.size(); i++) {

            temp[i] = this.queue[this.front];
            this.front = (this.front + 1) % this.queue.length;
        }

        this.front = 0;
        this.rear = this.count;
        this.queue = temp;
    }

}
