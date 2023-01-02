package objetos;

import objetos.ICaminho;
import Structures.Interfaces.NetworkADT;
import java.util.Iterator;


public interface IMapa<T> extends NetworkADT<T>{
    
    /**
     * Retorna todos os vertices do mapa
     * @return todos os vertices do mapa
     */
    public Iterator<T>getVertices();
    
    /**
     * Retorna todos os caminhos que existem
     * @return todos os caminhos que existem
     */
    public Iterator<ICaminho<T>> getPaths();
}
