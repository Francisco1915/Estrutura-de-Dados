package Structures.Heap;

import Structures.tree.ArrayBinaryTree;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.HeapADT;

/**
 * ArrayHeap provides an array implementation of a minheap.
 *
 * @param <T>
 */
public class ArrayHeap<T> extends ArrayBinaryTree<T>
        implements HeapADT<T> {

    public ArrayHeap() {
        super();
    }

    /**
     * Adds the specified element to this heap in the appropriate position
     * according to its key value. Note that equal elements are added to the
     * right.
     *
     * @param obj the element to be added to this heap
     */
    @Override
    public void addElement(T obj) {
        if (this.count == this.tree.length) {
            expandCapacity();
        }
        this.tree[this.count] = obj;
        this.count++;
        if (this.count > 1) {
            this.heapifyAdd();
        }
    }

    /**
     * Reorders this heap to maintain the ordering property after adding a node.
     */
    private void heapifyAdd() {
        T temp;
        int next = this.count - 1;

        temp = this.tree[next];

        while ((next != 0) && (((Comparable) temp).compareTo(this.tree[(next - 1) / 2]) < 0)) {
            this.tree[next] = this.tree[(next - 1) / 2];
            next = (next - 1) / 2;
        }
        this.tree[next] = temp;
    }

    /**
     * Remove the element with the lowest value in this heap and returns a
     * reference to it. Throws an EmptyCollectionException if the heap is empty.
     *
     * @return a reference to the element with the lowest value in this head
     * @throws EmptyCollectionException if an empty collection exception occurs
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }
        T minElement = this.tree[0];
        this.tree[0] = this.tree[this.count - 1];
        heapifyRemove();
        this.count--;

        return minElement;
    }

    /**
     * Reorders this heap to maintain the ordering property.
     */
    private void heapifyRemove() {
        T temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((this.tree[left] == null) && (this.tree[right] == null)) {
            next = this.count;
        } else if (this.tree[left] == null) {
            next = right;
        } else if (this.tree[right] == null) {
            next = left;
        } else if (((Comparable) this.tree[left]).compareTo(this.tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = this.tree[node];
        while ((next < this.count) && (((Comparable) this.tree[next]).compareTo(temp) < 0)) {
            this.tree[node] = this.tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((this.tree[left] == null) && (this.tree[right] == null)) {
                next = this.count;
            } else if (this.tree[left] == null) {
                next = right;
            } else if (this.tree[right] == null) {
                next = left;
            } else if (((Comparable) this.tree[left]).compareTo(this.tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }
        this.tree[node] = temp;
    }

    @Override
    public T findMin() {
        return this.tree[0];
    }
}
