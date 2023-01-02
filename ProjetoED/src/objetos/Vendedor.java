package objetos;

import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import objetos.Mercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedUnorderedList;
import java.util.Iterator;

public class Vendedor implements Comparable<IVendedor>, IVendedor {

    private int id;
    private String name;
    private int capacidade;
    private int stock;
    private UnorderedListADT<IMercado> mercados_visitar;

    /**
     * Construtor do vendedor.
     *
     * @param id id do vendedor
     * @param name nome do vendedor
     * @param capacidade capacidade maxima do vendedor
     * @param mercados_visitar lista de mercados a visitar
     */
    public Vendedor(int id, String name, int capacidade, int stock, UnorderedListADT<IMercado> mercados_visitar) {
        this.id = id;
        this.name = name;
        this.capacidade = capacidade;
        this.stock = stock;
        this.mercados_visitar = mercados_visitar;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public int removeStock(int consume) {
        if (this.stock - consume >= 0) {
            this.stock -= consume;
            return consume;
        } else {
            int stock = this.stock;
            this.stock = 0;
            return stock;
        }
    }

    @Override
    public UnorderedListADT<IMercado> getMercados_visitar() {
        return this.mercados_visitar;
    }

    @Override
    public void setMercados_visitar(UnorderedListADT<IMercado> mercados_visitar) {
        this.mercados_visitar = mercados_visitar;
    }

    @Override
    public void addMercado(IMercado newMercado) {
        this.mercados_visitar.addFront(newMercado);
    }

    @Override
    public void removerMercado(IMercado newMercado) throws EmptyCollectionException, ElementNotFoundException {
        this.mercados_visitar.remove(newMercado);
    }

    @Override
    public String toString() {
        return "Vendedor: " + "\nId:" + id + "\nName:" + name + "\nCapacidade=" + capacidade + "\nStock:" + stock + "\nMercados_visitar=" + "\n" + mercados_visitar.toString();
    }

    @Override
    public Iterator<IMercado> getMercadosAVisitar(IMapa<ILocal> mapa) {

        UnorderedListADT<IMercado> mercados = new LinkedUnorderedList<>();
        Iterator<IMercado> iterMercados = this.getMercados_visitar().iterator();

        while (iterMercados.hasNext()) {

            String nomeMercado = iterMercados.next().getName();
            Iterator<ILocal> iterLocais = mapa.getVertices();
            while (iterLocais.hasNext()) {

                ILocal current = iterLocais.next();
                if (current.getName().equals(nomeMercado) && current instanceof Mercado) {
                    mercados.addRear((IMercado) current);
                }
            }
        }
        return mercados.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vendedor other = (Vendedor) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(IVendedor o) {
        return this.id - o.getId();
    }
}
