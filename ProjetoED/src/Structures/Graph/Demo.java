package Structures.Graph;

import Structures.Exceptions.EmptyCollectionException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Network<String> graph = new Network<>();

        String a = "V1", b= "V2", c = "V3", d = "V4", e = "V5";

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);

        graph.addEdge(a, b, 2);
        graph.addEdge(a, c, 3);
        graph.addEdge(a, e, 6);
        graph.addEdge(b, e, 2);
        graph.addEdge(b, d, 3);
        graph.addEdge(e, d, 2);

//        try {
//            graph.removeEdge(f, l);
//            graph.removeVertex(l);
//        } catch (EmptyCollectionException | ElementNotFoundException ex) {
//            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
//        }
Iterator it = null;
        try {
            it = graph.iteratorDFS(a);
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
while (it.hasNext()) {
    System.out.println(it.next());
}

        System.out.println(graph.toString());

        try {
            System.out.println(graph.isConnected());
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        Iterator its = null;
        try {
            its = graph.iteratorShortestPath(a, e);
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (its.hasNext()) {
            System.out.println(its.next());
        }

    }

}
