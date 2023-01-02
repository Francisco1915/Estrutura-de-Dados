package objetos;

import Structures.Graph.Network;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedUnorderedList;
import java.util.Iterator;

public class Mapa<T> extends Network<T> implements IMapa<T> {

    /**
     * Construtor do mapa.
     */
    public Mapa() {
    }

    @Override
    public Iterator<T> getVertices() {

        LinkedUnorderedList<T> vertices1 = new LinkedUnorderedList<>();

        for (int i = 0; i < super.size(); i++) {
            vertices1.addRear(super.vertices[i]);
        }

        return vertices1.iterator();
    }

    @Override
    public Iterator<ICaminho<T>> getPaths() {

        UnorderedListADT<ICaminho<T>> caminhos = new LinkedUnorderedList<>();

        for (int i = 0; i < super.numVertices; i++) {
            for (int j = i; j < super.numVertices; j++) {
                if (super.adjMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    caminhos.addRear(new Caminho<>(super.vertices[i], super.vertices[j], super.adjMatrix[i][j]));
                }
            }
        }
        return caminhos.iterator();
    }
}
