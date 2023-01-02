package objetos;

public interface ICaminho<T> {
    
    /**
     * Retorna o local de inicio
     * @return o local de inicio
     */
    public T getInicio();

    /**
     * Retorna o local de destino
     * @return o local de destino
     */
    public T getDestino();

    /**
     * Retorna o peso do caminho
     * @return o peso do caminho
     */
    public double getPeso();
}
