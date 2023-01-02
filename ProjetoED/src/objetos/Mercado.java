package objetos;

import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;
import Structures.Linked.LinkedQueue;

public class Mercado extends Local implements Comparable<IMercado>, IMercado {

    private QueueADT<Integer> clientes;

    /**
     * Construtor do Mercado.
     *
     * @param name nome do mercado
     * @param clientes clientes do mercado
     */
    public Mercado(String name, QueueADT<Integer> clientes) {
        super.name = name;
        this.clientes = clientes;
    }

    @Override
    public QueueADT<Integer> getClientes() {
        return this.clientes;
    }

    @Override
    public void setClientes(QueueADT<Integer> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean setCliente(int cliente) {

        if (cliente <= 0) {
            return false;
        }
        this.clientes.enqueue(cliente);
        return true;
    }

    @Override
    public int servirCliente(int removidoVendedor) throws EmptyCollectionException {
        int cliente = -1;

        if (removidoVendedor < this.clientes.first()) {

            cliente = this.clientes.dequeue();
            cliente -= removidoVendedor;

            QueueADT<Integer> clientes1 = new LinkedQueue<>();
            clientes1.enqueue(cliente);

            while (!this.clientes.isEmpty()) {

                clientes1.enqueue(this.clientes.dequeue());
            }
            this.clientes = clientes1;
            return cliente;
        } else {
            cliente = this.clientes.dequeue();
            cliente -= removidoVendedor;
            return cliente;
        }
    }

    @Override
    public String toString() {
        return "Mercado:" + "\nNome:" + name + "\nClientes:" + clientes.toString();
    }

    @Override
    public int compareTo(IMercado o) {
        return o.getClientes().size() - this.clientes.size();
    }
}
