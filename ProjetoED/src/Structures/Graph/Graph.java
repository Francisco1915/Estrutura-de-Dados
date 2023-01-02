package Structures.Graph;

import Structures.Array.ArrayUnorderedList;
import Structures.Linked.LinkedQueue;
import Structures.Linked.LinkedStack;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.GraphADT;
import java.util.Iterator;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 *
 * @param <T>
 */
public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    public Graph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }
    
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    @Override
    public void addVertex(T vertex) {
        if (this.numVertices == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.numVertices] = vertex;
        for (int i = 0; i <= this.numVertices; i++) {
            this.adjMatrix[this.numVertices][i] = false;
            this.adjMatrix[i][this.numVertices] = false;
        }
        this.numVertices++;
    }

    @Override
    public void removeVertex(T vertex) throws EmptyCollectionException, ElementNotFoundException {

        if (this.isEmpty()) {
            throw new EmptyCollectionException("Graph is empty");
        }

        if (this.getIndex(vertex) == -1) {
            throw new ElementNotFoundException("Vertix not found");
        }

        int pos = this.getIndex(vertex);
        this.vertices[this.getIndex(vertex)] = null;

        for (int i = pos; i < this.size() - 1; i++) {
            this.vertices[i] = this.vertices[i + 1];
        }

        for (int i = pos; i < this.size() - 1; i++) {
            for (int j = 0; j <= this.size() - 1; j++) {
                this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
            }
        }

        for (int i = pos; i < this.size() - 1; i++) {
            for (int j = 0; j < this.size() - 1; j++) {
                this.adjMatrix[j][i] = this.adjMatrix[j][i + 1];
            }
        }

        this.numVertices--;
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) throws EmptyCollectionException {

        if (this.isEmpty()) {
            throw new EmptyCollectionException("Graph is empty");
        }

        int index1 = this.getIndex(vertex1);
        int index2 = this.getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = false;
            this.adjMatrix[index2][index1] = false;
        }
    }

    public Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList();
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addRear(vertices[x]);
            
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList();
        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addRear(vertices[startIndex]);
        visited[startIndex] = true;
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) throws EmptyCollectionException {
        Integer x = -1;
        int[] pathLength = new int[this.size()];
        int[] parent = new int[this.size()];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        parent[startIndex] = -1;
        while ((!traversalQueue.isEmpty()) && (x != targetIndex)) {
            x = traversalQueue.dequeue();
            
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    pathLength[i] = pathLength[x] + 1;
                    parent[i] = x;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        if (x != targetIndex) {
            return resultList.iterator();
        }

        LinkedStack<Integer> stack = new LinkedStack<>();
        x = targetIndex;
        stack.push(x);
        do {
            x = parent[x];
            stack.push(x);
        } while (x != startIndex);

        while (!stack.isEmpty()) {
            resultList.addRear(vertices[stack.pop()]);
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException {
        return this.iteratorBFS(this.getIndex(startVertex));
    }

    @Override
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException {
        return this.iteratorDFS(this.getIndex(startVertex));
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        return this.iteratorShortestPath(this.getIndex(startVertex), this.getIndex(targetVertex));
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean isConnected() throws EmptyCollectionException {

        if (this.isEmpty()) {
            throw new EmptyCollectionException("Graph is empty");
        }

        int count = 0;
        Iterator iterator = this.iteratorBFS(this.vertices[0]);

        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        return (count == this.size());
    }

    @Override
    public int size() {
        return this.numVertices;
    }

    private void expandCapacity() {
        T[] temp = (T[]) (new Object[this.vertices.length * 2]);
        boolean[][] temp2 = new boolean[this.vertices.length * 2][this.vertices.length * 2];

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                temp2[i][j] = this.adjMatrix[i][j];
            }
            temp[i] = this.vertices[i];
        }

        this.adjMatrix = temp2;
        this.vertices = temp;
    }

    private boolean indexIsValid(int index1) {
        return ((index1 < this.numVertices) && (index1 >= 0));
    }

    private int getIndex(T vertex1) {

        for (int i = 0; i < this.size(); i++) {
            if (this.vertices[i].equals(vertex1)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }

        String result = "";

        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }

        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }
}
