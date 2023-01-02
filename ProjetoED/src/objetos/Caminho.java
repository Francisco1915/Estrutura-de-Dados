package objetos;

public class Caminho<T> implements ICaminho<T> {

    private T inicio;
    private T destino;
    private double peso;

    /**
     * Construtor caminho
     * 
     * @param inicio inicio 
     * @param destino fim
     * @param peso distancia
     */
    public Caminho(T inicio, T destino, double peso) {
        this.inicio = inicio;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public T getInicio() {
        return inicio;
    }

    @Override
    public T getDestino() {
        return destino;
    }

    @Override
    public double getPeso() {
        return peso;
    }
}
