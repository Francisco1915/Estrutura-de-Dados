package gestao.locais.mercados;

import gestao.locais.GestaoLocal;
import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import objetos.Mercado;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.OrderedListADT;
import Structures.Interfaces.QueueADT;
import Structures.Linked.LinkedOrderedList;
import Structures.Linked.LinkedQueue;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GestaoMercado extends GestaoLocal implements IGestaoMercado {

    /**
     * Construtor da gestão de Mercado.
     */
    public GestaoMercado() {
    }

    @Override
    public boolean validarInfoMercado(IMapa<ILocal> mapa, IMercado mercado) {

        boolean validation = true;
        QueueADT<Integer> temp = new LinkedQueue<>();

        while (!mercado.getClientes().isEmpty()) {

            try {
                int cliente = mercado.getClientes().dequeue();
                if (cliente <= 0) {
                    validation = false;
                }
                temp.enqueue(cliente);
            } catch (EmptyCollectionException ex) {
                validation = true;
            }
        }

        if (mercado.getName() == "" || mercado.getName() == null || !validation) {
            mercado.setClientes(temp);
            return false;
        }

        mercado.setClientes(temp);
        return true;
    }

    @Override
    public boolean addClienteAoMercado(IMapa<ILocal> mapa, String nomeMercado, int cliente) {
        if (mapa == null || cliente < 0) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        ILocal local = super.procurarLocal(mapa, nomeMercado);

        if (local == null || !(local instanceof Mercado)) {
            return false;
        }

        IMercado mercado = (IMercado) local;
        mercado.setCliente(cliente);
        return true;
    }

    @Override
    public int alterarInfoAoMercado(IMapa<ILocal> mapa, String novoNome, QueueADT<Integer> novosClientes, String nomeLocal) {
        if (mapa == null) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        ILocal local = super.procurarLocal(mapa, nomeLocal);

        if (local == null || !(local instanceof Mercado)) {
            return 1;
        }

        IMercado mercado = (IMercado) local;
        IMercado temp = new Mercado(mercado.getName(), mercado.getClientes());

        if (novoNome != "" && super.procurarLocal(mapa, novoNome) == null) {
            mercado.setName(novoNome);
        }

        if (novosClientes != null) {
            mercado.setClientes(novosClientes);
        }

        if (!this.validarInfoMercado(mapa, mercado)) {
            mercado.setName(temp.getName());
            mercado.setClientes(temp.getClientes());
            return 2;
        }

        return 0;
    }

    @Override
    public boolean printInfoMercados(IMapa<ILocal> mapa) {
        if (mapa == null) {
            throw new IllegalArgumentException("O parametro é invalido");
        }

        Iterator<ILocal> iter = mapa.getVertices();
        OrderedListADT<IMercado> mercados = new LinkedOrderedList<>();
        int count = 0;

        while (iter.hasNext()) {

            ILocal current = iter.next();
            if (current instanceof Mercado) {
                count++;
                mercados.add((IMercado) current);
            }
        }

        Iterator<IMercado> mercadosIter = mercados.iterator();

        System.out.println("------------Mercados------------");
        while (mercadosIter.hasNext()) {

            System.out.println(mercadosIter.next().toString());
        }

        System.out.println("--------------------------------");
        return count != 0;
    }

    @Override
    public JSONObject exportInfoMercadoToJSON(IMapa<ILocal> mapa, String nomeLocal) throws EmptyCollectionException {

        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = super.procurarLocal(mapa, nomeLocal);

        if (local == null || !(local instanceof Mercado)) {
            return null;
        }

        IMercado mercado = (IMercado) local;
        JSONObject jsonMercado = super.ExportLocal(mapa, nomeLocal);

        int size = mercado.getClientes().size();
        QueueADT<Integer> clientes1 = new LinkedQueue<>();
        JSONArray jsonClientes = new JSONArray();

        if (size != 0) {
            for (int i = 0; i < size; i++) {

                int cliente = mercado.getClientes().dequeue();
                jsonClientes.add(cliente);
                clientes1.enqueue(cliente);
            }
            mercado.setClientes(clientes1);
        }

        jsonMercado.put("clientes", jsonClientes);
        return jsonMercado;
    }
}
