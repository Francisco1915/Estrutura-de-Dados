package Structures.Interfaces;

import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import java.util.Iterator;

/**
 * GraphADT defines the interface to a graph data structure.
 *
 * @param <T>
 */
public interface GraphADT<T>
{
 /**
 * Adds a vertex to this graph, associating object with vertex.
 *
 * @param vertex the vertex to be added to this graph
 */
 public void addVertex (T vertex);
 
/**
 * Removes a single vertex with the given value from this graph.
 *
 * @param vertex the vertex to be removed from this graph
     * @throws EmptyCollectionException if graph is empty
     * @throws ElementNotFoundException if vertex not found
 */
 public void removeVertex (T vertex) throws EmptyCollectionException, ElementNotFoundException;
 /**
 * Inserts an edge between two vertices of this graph.
 *
 * @param vertex1 the first vertex
 * @param vertex2 the second vertex
 */
 public void addEdge (T vertex1, T vertex2);
 /**
 * Removes an edge between two vertices of this graph.
 *
 * @param vertex1 the first vertex
 * @param vertex2 the second vertex
     * @throws EmptyCollectionException if graph is empty
     * @throws ElementNotFoundException if vertex not found
 */
 public void removeEdge (T vertex1, T vertex2) throws EmptyCollectionException, ElementNotFoundException;
 
/**
 * Returns a breadth first iterator starting with the given vertex.
 *
 * @param startVertex the starting vertex
 * @return a breadth first iterator beginning at
 * the given vertex
     * @throws EmptyCollectionException if graph is empty
 */
 public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException;
 /**
 * Returns a depth first iterator starting with the given vertex.
 *
 * @param startVertex the starting vertex
 * @return a depth first iterator starting at the
 * given vertex
     * @throws EmptyCollectionException if graph is empty
 */
 public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;
 
/**
 * Returns an iterator that contains the shortest path between
 * the two vertices.
 *
 * @param startVertex the starting vertex
 * @param targetVertex the ending vertex
 * @return an iterator that contains the shortest
 * path between the two vertices
     * @throws EmptyCollectionException if graph is empty
 */
 public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException;
 /**
 * Returns true if this graph is empty, false otherwise.
 *
 * @return true if this graph is empty
 */
 public boolean isEmpty();
 /**
 * Returns true if this graph is connected, false otherwise.
 *
 * @return true if this graph is connected
     * @throws EmptyCollectionException if graph is empty
 */
 public boolean isConnected() throws EmptyCollectionException;
 
/**
 * Returns the number of vertices in this graph.
 *
 * @return the integer number of vertices in this graph
 */
 public int size();
 /**
 * Returns a string representation of the adjacency matrix.
 *
 * @return a string representation of the adjacency matrix
 */
 @Override
 public String toString();
}