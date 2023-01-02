package Structures.tree;

import Structures.Array.ArrayUnorderedList;
import Structures.Linked.LinkedQueue;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.BinaryTreeADT;
import Structures.Interfaces.QueueADT;
import java.util.Iterator;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 *
 * @param <T>
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary
     * tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    @Override
    public T getRoot() {
        return this.root.getElement();
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
    public boolean contains(T targetElement) {

        T temp;
        boolean found = false;

        try {
            temp = this.find(targetElement);
            found = true;
        } catch (ElementNotFoundException ex) {
        }

        return found;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement,
            BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder(BinaryTreeNode<T> node,
            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void preorder(BinaryTreeNode<T> node,
            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);
        }
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postorder(BinaryTreeNode<T> node,
            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addRear(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();

        try {
            levelorder(this.root, templist);
        } catch (EmptyCollectionException ex) {
        }

        return templist.iterator();
    }

    protected void levelorder(BinaryTreeNode<T> node,
            ArrayUnorderedList<T> tempList) throws EmptyCollectionException {

        QueueADT<BinaryTreeNode> queue = new LinkedQueue<>();
        BinaryTreeNode<T> tempNode;

        if (node != null) {

            queue.enqueue(node);

            while (!queue.isEmpty()) {

                tempNode = queue.dequeue();

                if (tempNode.getElement() != null) {
                    tempList.addRear(tempNode.getElement());
                }

                if (tempNode.getLeft() != null) {
                    queue.enqueue(tempNode.getLeft());
                }

                if (tempNode.getRight() != null) {
                    queue.enqueue(tempNode.getRight());
                }
            }
        }
    }

    @Override
    public String toString() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preorder(root, templist);
        return templist.toString();
    }
}
