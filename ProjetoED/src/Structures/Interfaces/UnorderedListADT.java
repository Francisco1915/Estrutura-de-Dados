package Structures.Interfaces;

import Structures.Exceptions.ElementNotFoundException;

public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the front
     *
     * @param element the element to be added to this list
     */
    public void addFront(T element);

    /**
     * Adds the specified element to this list at the rear
     *
     * @param element the element to be added to this list
     */
    public void addRear(T element);

    /**
     * Adds the specified element to this list after the element selected
     *
     * @param element the element to be added to this list
     * @param target the element before the new element
     * @throws ElementNotFoundException if element not found
     */
    public void addAfter(T element, T target) throws ElementNotFoundException;
}
