package Structures.tree;

import Structures.Array.ArrayUnorderedList;
import Structures.Linked.LinkedQueue;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.BinaryTreeADT;
import Structures.Interfaces.QueueADT;
import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        count = 0;
        tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[CAPACITY];
        tree[0] = element;
    }

    public void expandCapacity() {
        T[] temp = (T[]) (new Object[size() * 2]);

        for (int i = 0; i < this.size(); i++) {

            temp[i] = this.tree[i];
        }

        this.tree = temp;
    }

    @Override
    public T getRoot() {
        return this.tree[0];
    }

    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {

        boolean found = false;

        for (int i = 0; i < this.size(); i++) {
            if (this.tree[i].equals(targetElement)) {
                found = true;
            }
        }

        return found;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in the tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void inorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                inorder(node * 2 + 1, templist);
                templist.addRear(tree[node]);
                inorder((node + 1) * 2, templist);
            }
        }
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void preorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                templist.addRear(tree[node]);
                preorder(node * 2 + 1, templist);
                preorder((node + 1) * 2, templist);
            }
        }
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void postorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length) {
            if (tree[node] != null) {
                postorder(node * 2 + 1, templist);
                postorder((node + 1) * 2, templist);
                templist.addRear(tree[node]);
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();

        levelorder(0, templist);

        return templist.iterator();
    }

    protected void levelorder(int node,
            ArrayUnorderedList<T> tempList) {

        QueueADT<Integer> queue = new LinkedQueue<>();
        int tempNode;

        queue.enqueue(node);

        while (!queue.isEmpty()) {
            try {

                tempNode = queue.dequeue();
                tempList.addRear(this.tree[tempNode]);

                if (this.tree[(tempNode * 2) + 1] != null) {
                    queue.enqueue((tempNode * 2) + 1);
                }

                if (this.tree[(tempNode + 1) * 2] != null) {
                    queue.enqueue((tempNode + 1) * 2);
                }

            } catch (EmptyCollectionException ex) {
                System.out.println(ex);
            }
        }
    }

    @Override
    public String toString() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.toString();
    }
}
