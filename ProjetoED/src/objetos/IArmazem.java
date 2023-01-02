package objetos;

public interface IArmazem extends ILocal{
    
    /**
     * Retorna capacidade maxima de um armazem.
     * 
     * @return capacidade maxima de um armazem.
     */
    public int getCapacidade();

    /**
     * Altera a capacidade maxima de um armazem.
     * 
     * @param capacidade nova capacidade maxima de um armazem
     */
    public void setCapacidade(int capacidade);

    /**
     * Retorna a mercadoria disponivel do armazem.
     * 
     * @return a mercadoria disponivel do armazem.
     */
    public int getStock();

    /**
     * Altera a mercadoria disponivel do armazem.
     * 
     * @param stock mercadoria disponivel do armazem
     */
    public void setStock(int stock);
    
    /**
     * Incrementa a mercadoria disponivel no armazem.
     * 
     * @param stock valor de mercadoria a incrementar
     * @return Stock disponivel se o valor de mercadoria mais o incrementado não ultrapassar a capacidade do armazem, se ultrapassar retorna -1. 
     */
    public int addStock(int stock);
    
    /**
     * Retira mercadoria disponivel no armazem.
     * 
     * @param stock valor de mercadoria a retirar
     * @return Stock disponivel se o valor de mercadoria menos o removido for maior do que 0, se for menor retorna -1. 
     */
    public int removeStock(int stock);
}
