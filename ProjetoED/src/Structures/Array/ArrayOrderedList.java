package Structures.Array;

import Structures.Interfaces.OrderedListADT;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList(int inicialCapacity) {
        super(inicialCapacity);
    }

    @Override
    public void add(T element) {
        
        if (!(element instanceof Comparable)) {
            throw new ClassCastException("Element not comparble");
        }
        
        Comparable temp = (Comparable) element;
        
        if (this.size() == this.list.length) {
            expandCapacity();
        }

        int pos = 0;
        
        while (pos < this.rear && temp.compareTo(this.list[pos]) > 0) {
            pos++;
        }

        for (int i = this.rear; i > pos; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[pos] = element;
        this.modCount++;
        this.rear++;
    }

}
