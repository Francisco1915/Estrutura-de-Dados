package Structures.Heap;

import Structures.tree.LinkedBinaryTree;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.HeapADT;

/**
 * Heap implements a heap.
 *
 * @param <T>
 */
public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {

    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
    }

    /**
     * Adds the specified element to this heap in the appropriate position
     * according to its key value Note that equal elements are added to the
     * right.
     *
     * @param obj the element to be added to this head
     */
    @Override
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<T>(obj);
        if (this.root == null) {
            this.root = node;
        } else {
            HeapNode<T> next_parent = getNextParentAdd();
            if (next_parent.getLeft() == null) {
                next_parent.setLeft(node);
            } else {
                next_parent.setRight(node);
            }

            node.parent = next_parent;
        }
        this.lastNode = node;
        this.count++;
        if (this.count > 1) {
            heapifyAdd();
        }
    }

    /**
     * Returns the node that will be the parent of the new node
     *
     * @return the node that will be a parent of the new node
     */
    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = this.lastNode;
        while ((result != this.root)
                && (result.parent.getLeft() != result)) {
            result = result.parent;
        }
        if (result != this.root) {
            if (result.parent.getRight() == null) {
                result = result.parent;
            } else {
                result = (HeapNode<T>) result.parent.getRight();
                while (result.getLeft() != null) {
                    result = (HeapNode<T>) result.getLeft();
                }
            }
        } else {
            while (result.getLeft() != null) {
                result = (HeapNode<T>) result.getLeft();
            }
        }

        return result;
    }

    /**
     * Reorders this heap after adding a node.
     */
    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = this.lastNode;

        temp = next.getElement();

        while ((next != this.root) && (((Comparable) temp).compareTo(next.parent.getElement()) < 0)) {
            next.setElement(next.parent.getElement());
            next = next.parent;
        }
        next.setElement(temp);
    }

    /**
     * Remove the element with the lowest value in this heap and returns a
     * reference to it. Throws an EmptyCollectionException if the heap is empty.
     *
     * @return the element with the lowest value in this heap
     * @throws EmptyCollectionException if an empty collection exception occurs
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }
        T minElement = this.root.getElement();
        if (this.count == 1) {
            this.root = null;
            this.lastNode = null;
        } else {
            HeapNode<T> next_last = getNewLastNode();
            if (this.lastNode.parent.getLeft() == this.lastNode) {
                this.lastNode.parent.setLeft(null);
            } else {
                this.lastNode.parent.setRight(null);
            }
            this.root.setElement(this.lastNode.getElement());
            this.lastNode = next_last;
            heapifyRemove();
        }
        this.count--;

        return minElement;
    }

    /**
     * Returns the node that will be the new last node after a remove.
     *
     * @return the node that willbe the new last node after a remove
     */
    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = this.lastNode;
        while ((result != this.root) && (result.parent.getLeft() == result)) {
            result = result.parent;
        }

        if (result != this.root) {
            result = (HeapNode<T>) result.parent.getLeft();
        }
        while (result.getRight() != null) {
            result = (HeapNode<T>) result.getRight();
        }
        return result;
    }

    /**
     * Reorders this heap after removing the root element.
     */
    private void heapifyRemove() {
        T temp;
        HeapNode<T> node = (HeapNode<T>) this.root;
        HeapNode<T> left = (HeapNode<T>) node.getLeft();
        HeapNode<T> right = (HeapNode<T>) node.getRight();
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } else if (left == null) {
            next = right;
        } else if (right == null) {
            next = left;
        } else if (((Comparable) left.getElement()).compareTo(right.getElement()) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = node.getElement();
        while ((next != null) && (((Comparable) next.getElement()).compareTo(temp) < 0)) {
            node.setElement(next.getElement());
            node = next;
            left = (HeapNode<T>) node.getLeft();
            right = (HeapNode<T>) node.getRight();

            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (((Comparable) left.getElement()).compareTo(right.getElement()) < 0) {
                next = left;
            } else {
                next = right;
            }
        }
        node.setElement(temp);
    }

    @Override
    public T findMin() {
        return this.root.getElement();
    }
}
