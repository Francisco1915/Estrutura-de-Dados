package Structures.tree;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.BinarySearchTreeADT;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T>
        implements BinarySearchTreeADT<T> {

    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new binary search
     * tree
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to the binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    @Override
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;
        if (isEmpty()) {
            this.root = temp;
        } else {
            BinaryTreeNode<T> current = this.root;
            boolean added = false;
            while (!added) {
                if (comparableElement.compareTo(current.getElement()) < 0) {
                    if (current.getLeft() == null) {
                        current.left = temp;
                        added = true;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) {
                        current.right = temp;
                        added = true;
                    } else {
                        current = current.getRight();
                    }
                }
            }
        }
        this.count++;

    }

    /**
     * Removes the first element that matches the specified target element from
     * the binary search tree and returns a reference to it. Throws a
     * ElementNotFoundException if the specified target element is not found in
     * the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;
        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(this.root.getElement())) {
                result = root.getElement();
                this.root = replacement(this.root);
                this.count--;
            } else {
                BinaryTreeNode<T> current, parent = this.root;
                boolean found = false;
                if (((Comparable) targetElement).compareTo(this.root.getElement()) < 0) {
                    current = this.root.getLeft();
                } else {
                    current = this.root.getRight();
                }
                while (current != null && !found) {
                    if (targetElement.equals(current.getElement())) {
                        found = true;
                        this.count--;
                        result = current.getElement();

                        if (current == parent.getLeft()) {
                            parent.left = replacement(current);
                        } else {
                            parent.right = replacement(current);
                        }
                    } else {
                        parent = current;

                        if (((Comparable) targetElement).compareTo(current.getElement()) < 0) {
                            current = current.getLeft();
                        } else {
                            current = current.getRight();
                        }
                    }
                } //while

                if (!found) {
                    throw new ElementNotFoundException("binary search tree");
                }
            }
        } // end outer if
        return result;
    }

    /**
     * Returns a reference to a node that will replace the one specified for
     * removal. In the case where the removed node has two children, the inorder
     * successor is used as its replacement.
     *
     * @param node the node to be removeed
     * @return a reference to the replacing node
     */
    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {

        BinaryTreeNode<T> result = null;

        if ((node.getLeft() == null) && (node.getRight() == null)) {
            result = null;
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            result = node.getLeft();
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            result = node.right;
        } else {

            BinaryTreeNode<T> current = node.getRight();
            BinaryTreeNode<T> parent = node;
            while (current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            if (node.getRight() == current) {
                current.left = node.getLeft();
            } else {

                parent.left = current.getRight();
                current.right = node.getRight();
                current.left = node.getLeft();
            }
            result = current;
        }
        return result;
    }

    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException {

        removeElement(targetElement);

        while (this.contains(targetElement)) {
            try {
                removeElement(targetElement);
            } catch (ElementNotFoundException ex) {
            }
        }
    }

    @Override
    public T removeMin() throws EmptyCollectionException {

        T result = null;

        if (!this.isEmpty()) {
            BinaryTreeNode<T> current = this.root;
            if (current.getLeft() == null) {

                result = current.getElement();
                this.root = current.getRight();
            } else {

                BinaryTreeNode<T> parent = current;
                current = current.getLeft();
                while (current.getLeft() != null) {
                    parent = current;
                    current = current.getLeft();
                }
                result = current.getElement();
                parent.setLeft(current.getRight());
            }
            this.count--;
        } else {
            throw new EmptyCollectionException("Tree is empty");
        }
        return result;
    }

    @Override
    public T removeMax() throws EmptyCollectionException {
        T result = null;

        if (!this.isEmpty()) {

            BinaryTreeNode<T> current = this.root;
            if (current.getRight() == null) {

                result = current.getElement();
                this.root = current.getLeft();
            } else {

                BinaryTreeNode<T> parent = current;
                current = current.getRight();
                while (current.getRight() != null) {
                    parent = current;
                    current = current.getRight();
                }

                result = current.getElement();
                parent.setRight(current.getLeft());
            }
            this.count--;
        } else {
            throw new EmptyCollectionException("Tree is empty");
        }
        return result;
    }

    @Override
    public T findMin() throws EmptyCollectionException {

        T result = null;

        if (!this.isEmpty()) {

            BinaryTreeNode<T> current = this.root;
            while (current.getLeft() != null) {
                current = current.getLeft();
            }

            result = current.getElement();
        } else {
            throw new EmptyCollectionException("Tree is empty");
        }
        return result;
    }

    @Override
    public T findMax() throws EmptyCollectionException {

        T result = null;

        if (!this.isEmpty()) {

            BinaryTreeNode<T> current = this.root;
            while (current.getRight() != null) {
                current = current.getRight();
            }

            result = current.getElement();
        } else {
            throw new EmptyCollectionException("Tree is empty");
        }
        return result;
    }
}
