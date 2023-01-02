package gestao.vendedores;

import Exceptions.NotFoundException;
import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import objetos.Mercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.OrderedListADT;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedOrderedList;
import objetos.IVendedor;
import objetos.Vendedor;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GestaoVendedores implements IGestaoVendedores {

    private OrderedListADT<IVendedor> vendedores;

    /**
     * Construtor da gestão de vendedores.
     */
    public GestaoVendedores() {
        this.vendedores = new LinkedOrderedList<>();
    }

    /**
     * Construtor da gestão de vendedores.
     *
     * @param vendedores vendedores importados
     */
    public GestaoVendedores(OrderedListADT<IVendedor> vendedores) {
        this.vendedores = vendedores;
    }

    @Override
    public boolean validarInfoVendedor(IMapa<ILocal> mapa, IVendedor vendedor) {

        Iterator<IMercado> iter = vendedor.getMercadosAVisitar(mapa);
        boolean validation = true;

        while (iter.hasNext()) {

            if (this.procurarLocal(mapa, iter.next().getName()) == null) {
                validation = false;
            }
        }

        if (!validation || vendedor.getId() < 0 || vendedor.getName() == "" || vendedor.getCapacidade() <= 0 || vendedor.getStock() < 0 || vendedor.getStock() > vendedor.getCapacidade()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addVendedor(IVendedor vendedor) {

        if (vendedor == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (this.vendedores.contains(vendedor)) {
            return false;
        }

        this.vendedores.add(vendedor);
        return true;
    }

    @Override
    public boolean removerVendedor(int idVendedor) {

        try {
            this.vendedores.remove(this.procurarVendedor(idVendedor));
        } catch (EmptyCollectionException | ElementNotFoundException ex) {
            return false;
        }

        return true;
    }

    @Override
    public int addMercadoAoVendedor(IMapa<ILocal> mapa, int idVendedor, String nomeMercado) {

        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = this.procurarLocal(mapa, nomeMercado);
        IVendedor vendedor = this.procurarVendedor(idVendedor);

        if (local == null || !(local instanceof Mercado)) {
            return 0;
        }

        if (vendedor == null) {
            return 1;
        }

        Iterator<IMercado> iter = vendedor.getMercados_visitar().iterator();

        while (iter.hasNext()) {

            if (iter.next().getName().equals(nomeMercado)) {
                return 3;
            }
        }

        vendedor.addMercado((IMercado) local);
        return 2;
    }

    @Override
    public int removerMercadoAoVendedor(int idVendedor, String nomeMercado, IMapa<ILocal> mapa) {

        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = this.procurarLocal(mapa, nomeMercado);
        IVendedor vendedor = this.procurarVendedor(idVendedor);

        if (local == null || !(local instanceof Mercado)) {
            return 0;
        }

        if (vendedor == null) {
            return 1;
        }
        try {
            vendedor.removerMercado((IMercado) local);
            return 2;
        } catch (EmptyCollectionException | ElementNotFoundException ex) {
            return 0;
        }
    }

    @Override
    public boolean removerMercadoAosVendedores(String nomeMercado, IMapa<ILocal> mapa) throws EmptyCollectionException, ElementNotFoundException {

        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = this.procurarLocal(mapa, nomeMercado);

        if (local == null || !(local instanceof Mercado)) {
            return false;
        }

        Iterator<IVendedor> iter = this.vendedores.iterator();
        while (iter.hasNext()) {

            IVendedor vendedor = iter.next();
            Iterator<IMercado> mercados = vendedor.getMercados_visitar().iterator();
            boolean found = false;

            if (mercados.hasNext()) {
                if (mercados.next().getName().equals(nomeMercado)) {
                    mercados.remove();
                    found = true;
                }
            }
            if (!found) {
                while (mercados.hasNext() && !found) {

                    IMercado mercado = mercados.next();
                    if (mercado.getName().equals(nomeMercado)) {
                        mercados.remove();
                        found = true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int alterarInfoVendedor(IMapa<ILocal> mapa, int idVendedor, String novoNome, int novaCapacidade, int novoStock, UnorderedListADT<IMercado> mercados_visitar) {

        if (novaCapacidade < -1 || novoStock < -1) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        IVendedor vendedor = this.procurarVendedor(idVendedor);
        IVendedor temp = new Vendedor(vendedor.getId(), vendedor.getName(), vendedor.getCapacidade(), vendedor.getStock(), vendedor.getMercados_visitar());

        if (vendedor == null || novaCapacidade < -1 || novoStock < -1) {
            return 1;
        }

        if (novoNome != "") {
            vendedor.setName(novoNome);
        }

        if (novaCapacidade != -1) {
            vendedor.setCapacidade(novaCapacidade);
        }

        if (novoStock != -1) {
            vendedor.setStock(novoStock);
        }

        if (mercados_visitar != null) {
            vendedor.setMercados_visitar(mercados_visitar);
        }

        if (!this.validarInfoVendedor(mapa, vendedor)) {
            vendedor.setName(temp.getName());
            vendedor.setId(temp.getId());
            vendedor.setCapacidade(temp.getCapacidade());
            vendedor.setStock(temp.getStock());
            vendedor.setMercados_visitar(temp.getMercados_visitar());
            return 2;
        }
        return 0;
    }

    @Override
    public boolean printInfoVendedor(int idVendedor) {

        IVendedor vendedor = this.procurarVendedor(idVendedor);

        if (vendedor == null) {
            return false;
        }

        System.out.println("-------------Vendedor--------------");
        System.out.println(vendedor.toString());
        System.out.println("-----------------------------------");
        return true;
    }

    @Override
    public boolean printInfoVendedores() {

        if (this.vendedores.isEmpty()) {
            return false;
        }

        Iterator<IVendedor> vendedoresIter = this.vendedores.iterator();

        System.out.println("-------------Vendedores-------------");
        while (vendedoresIter.hasNext()) {

            System.out.print(vendedoresIter.next().toString());
            System.out.println("------------------------------------");
        }
        return true;
    }

    @Override
    public IVendedor procurarVendedor(int idVendedor) {

        if (idVendedor < 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        Iterator<IVendedor> iter = this.vendedores.iterator();

        while (iter.hasNext()) {

            IVendedor current = iter.next();
            if (current.getId() == idVendedor) {
                return current;
            }
        }
        return null;

    }

    /**
     * Procurar um local
     *
     * @param mapa mapa da emrpesa
     * @param nomeLocal nome do local a procurar
     * @return O local procurado, null se não existir esse local.
     */
    private ILocal procurarLocal(IMapa<ILocal> mapa, String nomeLocal) {

        Iterator<ILocal> iter = mapa.getVertices();

        while (iter.hasNext()) {

            ILocal current = iter.next();
            if (current.getName().equals(nomeLocal)) {
                return current;
            }
        }
        return null;
    }

    @Override
    public JSONObject exportInfoVendedor(int idVendedor) throws NotFoundException{

        if (idVendedor < 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        IVendedor vendedor = this.procurarVendedor(idVendedor);

        if (vendedor == null) {
            throw new NotFoundException("Vendedor nao existe");
        }

        JSONObject jsonVendedor = new JSONObject();
        jsonVendedor.put("id", vendedor.getId());
        jsonVendedor.put("name", vendedor.getName());
        jsonVendedor.put("capacidade", vendedor.getCapacidade());
        jsonVendedor.put("stock", vendedor.getStock());

        Iterator<IMercado> mercadosIter = vendedor.getMercados_visitar().iterator();
        JSONArray jsonArrayMercados = new JSONArray();

        while (mercadosIter.hasNext()) {
            jsonArrayMercados.add(mercadosIter.next().getName());
        }

        jsonVendedor.put("mercados_visitar", jsonArrayMercados);
        return jsonVendedor;
    }

    @Override
    public JSONArray exportInfoAllVendedores() throws NotFoundException{

        Iterator<IVendedor> vendedoresIter = this.vendedores.iterator();
        JSONArray jsonVendedores = new JSONArray();

        if (this.vendedores.size() != 0) {
            while (vendedoresIter.hasNext()) {

                jsonVendedores.add(this.exportInfoVendedor(vendedoresIter.next().getId()));
            }
        }
        return jsonVendedores;
    }
}
