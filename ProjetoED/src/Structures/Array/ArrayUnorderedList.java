package Structures.Array;

import Structures.Interfaces.UnorderedListADT;
import Structures.Exceptions.ElementNotFoundException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    @Override
    public void addFront(T element) {
        if (size() == this.list.length) {
            this.expandCapacity();
        }

        for (int i = this.rear; i > 0; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[0] = element;
        this.modCount++;
        this.rear++;
    }

    @Override
    public void addRear(T element) {
        if (this.size() == this.list.length) {
            this.expandCapacity();
        }

        this.list[this.rear] = element;
        this.modCount++;
        this.rear++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        if (this.size() == this.list.length) {
            this.expandCapacity();
        }

        int pos = 0;
        while (pos < this.rear && !element.equals(this.list[pos])) {
            pos++;
        }
        
        if (pos == this.rear) {
            throw new ElementNotFoundException("Element not found");
        }
        
        pos++;
        for (int i = this.rear; i > pos; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[pos] = element;
        this.modCount++;
        this.rear++;
    }

}
