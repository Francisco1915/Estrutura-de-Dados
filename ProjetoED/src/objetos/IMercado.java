package objetos;

import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;

public interface IMercado extends ILocal {
    
    /**
     * Retorna a lista de clientes do mercado.
     * 
     * @return a lista de clientes do mercado.
     */
    public QueueADT<Integer> getClientes();

    /**
     * Altera a lista de clientes do mercado.
     * 
     * @param clientes nova lista de clientes do mercado
     */
    public void setClientes(QueueADT<Integer> clientes);
    
     /**
     * Adicionar um cliente ao mercado
     * 
     * @param cliente novo cliente
     * @return true se cliente for valido, false se for invalido
     */
    public boolean setCliente(int cliente);
      
    /**
     * Servir o cliente. Fornece a mercadoria que o cliente precisa.
     * 
     * @param removidoVendedor mercadoria que o vendedor consegue fornecer
     * @return Quantidade de mercadoria que o cliente ainda percisa.
     * @throws EmptyCollectionException Se a lista de clientes estiver vazia.
     */
    public int servirCliente(int removidoVendedor) throws EmptyCollectionException;
    
}
