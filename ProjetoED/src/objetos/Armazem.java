package objetos;

public class Armazem extends Local implements Comparable<IArmazem>, IArmazem{
    
    private int capacidade;
    private int stock;

    /**
     * Construtor de um armazem.
     * 
     * @param name nome do armazem
     * @param capacidade capacidade maxima de um armazem
     * @param stock mercadoria disponivel do armazem
     */
    public Armazem(String name,int capacidade, int stock) {
        super.name = name;
        this.capacidade = capacidade;
        this.stock = stock;
    }
    
    @Override
    public int getCapacidade() {
        return this.capacidade;
    }


    @Override
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }


    @Override
    public int getStock() {
        return this.stock;
    }


    @Override
    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public int addStock(int stock) {
        if (this.stock + stock <= this.capacidade) {
            return this.stock += stock;
        }
        return -1;
    }

    @Override
    public int removeStock(int stock) {
        if (this.stock - stock >= 0) {
            return this.stock -= stock;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Armazem: " + "\nNome: " + name + "\nCapacidade: " + capacidade + "\nStock: " + stock;
    }

    @Override
    public int compareTo(IArmazem o) {
        return o.getStock() - this.stock;
    }
}
