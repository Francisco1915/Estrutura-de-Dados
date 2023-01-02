package Structures.Graph;

import Structures.Array.ArrayUnorderedList;
import Structures.Heap.ArrayHeap;
import Structures.Linked.LinkedQueue;
import Structures.Linked.LinkedStack;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.NetworkADT;
import java.util.Iterator;

public class Network<T> implements NetworkADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected double[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2), weight);
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        this.addEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    protected void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    protected void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    @Override
    public void addVertex(T vertex){
             
        if (this.numVertices == this.vertices.length) {
            this.expandCapacity();
        }

        this.vertices[this.numVertices] = vertex;
        for (int i = 0; i <= this.numVertices; i++) {
            this.adjMatrix[this.numVertices][i] = Double.POSITIVE_INFINITY;
            this.adjMatrix[i][this.numVertices] = Double.POSITIVE_INFINITY;
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
    public void removeEdge(T vertex1, T vertex2) throws EmptyCollectionException, ElementNotFoundException{
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Graph is empty");
        }
        
        int index1 = this.getIndex(vertex1);
        int index2 = this.getIndex(vertex2);
        
        if (index1 == -1 || index2 == -1) {
            throw new ElementNotFoundException("Vertix not found");
        }
        
        if (indexIsValid(index1) && indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            this.adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startIndex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     * @throws EmptyCollectionException
     */
    protected Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
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
                if (adjMatrix[x][i] < Double.POSITIVE_INFINITY && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     * @throws EmptyCollectionException
     */
    protected Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
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
                if (adjMatrix[x][i] < Double.POSITIVE_INFINITY && !visited[i]) {
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

    protected Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) throws EmptyCollectionException  {
        int index;
        double weight;
        int[] parent = new int[numVertices];
        ArrayHeap<Double> traversalMinHeap = new ArrayHeap<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator();
        }

        pathWeight[startIndex] = 0;
        parent[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                parent[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]);
            }
        }

        do {
            weight = (traversalMinHeap.removeMin());

            while (!traversalMinHeap.isEmpty()) {
                traversalMinHeap.removeMin();
            }

            if (weight == Double.POSITIVE_INFINITY) {
                return resultList.iterator();
            } else {
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight,
                        weight);
                visited[index] = true;
            }

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY) && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        parent[i] = index;
                    }
                    traversalMinHeap.addElement(pathWeight[i]);
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        LinkedStack<Integer> stack = new LinkedStack<>();
        index = targetIndex;
        stack.push(index);
        do {
            index = parent[index];
            stack.push(index);
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addRear(this.vertices[stack.pop()]);
        }

        return resultList.iterator();
    }

    protected double shortestPathWeight(int startIndex, int targetIndex) throws EmptyCollectionException {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }

        int index1, index2;
        Iterator<T> it = iteratorShortestPath(startIndex, targetIndex);

        if (it.hasNext()) {
            index1 = (this.getIndex(it.next()));
        } else {
            return Double.POSITIVE_INFINITY;
        }

        while (it.hasNext()) {
            index2 = (this.getIndex(it.next()));
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) throws EmptyCollectionException {
        return this.shortestPathWeight(this.getIndex(vertex1), this.getIndex(vertex2));
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

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY)
                            && visited[j]) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    protected int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }

        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

    @Override
    public boolean isEmpty() {
        return this.numVertices == 0;
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
        double[][] temp2 = new double[this.vertices.length * 2][this.vertices.length * 2];

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
                if (adjMatrix[i][j] > 0) {
                    result += adjMatrix[i][j];
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
