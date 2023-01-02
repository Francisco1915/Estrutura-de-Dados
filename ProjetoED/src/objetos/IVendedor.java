package objetos;

import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import java.util.Iterator;

public interface IVendedor {
    
    /**
     * Retornar o id do vendedor.
     * 
     * @return id do vendedor.
     */
    public int getId();

    /**
     * Alterar o id do vendedor.
     * 
     * @param id novo id do vendedor
     */
    public void setId(int id);
    
    /**
     * Retornar o nome do vendedor.
     * 
     * @return nome do vendedor.
     */
    public String getName();

    /**
     * Alterar o nome do vendedor.
     * 
     * @param name novo nome do vendedor 
     */
    public void setName(String name);

    /**
     * Retornar a capacidade maxima do vendedor.
     * 
     * @return capacidade maxima do vendedor.
     */
    public int getCapacidade();

    /**
     * Alterar a capacidade maxima do vendedor.
     * 
     * @param capacidade nova capacidade maxima do vendedor
     */
    public void setCapacidade(int capacidade);
    
    /**
     * Retorna a mercadoria disponivel do vendedor.
     * 
     * @return a mercadoria disponivel do vendedor.
     */
    public int getStock();

    /**
     * Altera a mercadoria disponivel do vendedor.
     * 
     * @param stock mercadoria disponivel do vendedor
     */
    public void setStock(int stock);
    
    /**
     * Incrementa a mercadoria disponivel do vendedor.
     * 
     * @param stock valor de mercadoria a incrementar
     * @return Stock disponivel se o valor de mercadoria mais o incrementado não ultrapassar a capacidade do vendedor, se ultrapassar retorna -1. 
     */
    public int addStock(int stock);
    
    /**
     * Retira mercadoria disponivel do vendedor.
     * 
     * @param stock valor de mercadoria a retirar
     * @return stock que foi removido.
     */
    public int removeStock(int stock);

    /**
     * Retorna a lista de mercados a visitar.
     * 
     * @return lista de mercados a visitar.
     */
    public UnorderedListADT<IMercado> getMercados_visitar();
    
    /**
     * Retorna o iterador dos mercados a visitar
     * @param mapa mapa da empresa
     * @return o iterador dos mercados a visitar
     */
    public Iterator<IMercado> getMercadosAVisitar(IMapa<ILocal> mapa);

    /**
     * Aletra a lista de mercados a visitar.
     * 
     * @param mercados_visitar nova lista de mercados a visitar
     */
    public void setMercados_visitar(UnorderedListADT<IMercado> mercados_visitar);
    
    /**
     * Adicionar mercado a lista de mercados a visitar.
     * 
     * @param newMercado 
     */
    public void addMercado(IMercado newMercado);
    
    /**
     * Remover mercado a lista de mercados a visitar.
     * 
     * @param newMercado
     * @throws EmptyCollectionException
     * @throws ElementNotFoundException 
     */
    public void removerMercado(IMercado newMercado) throws EmptyCollectionException, ElementNotFoundException;
    
}
