package gestao.caminhos;

import objetos.IMapa;
import objetos.Armazem;
import objetos.IArmazem;
import objetos.ICaminho;
import objetos.ILocal;
import objetos.IMercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedUnorderedList;
import objetos.IVendedor;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GestaoCaminhos implements IGestaoCaminhos {

    /**
     * Construtor da gestão de Caminhos.
     */
    public GestaoCaminhos() {
    }

    @Override
    public int addCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino, double peso) {

        if (mapa == null || peso < 0) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        ILocal inicio = this.procurarLocal(mapa, nomeInicio);
        ILocal destino = this.procurarLocal(mapa, nomeDestino);

        if (inicio == null || destino == null) {
            return 2;
        }

        if (this.procurarCaminho(mapa, nomeInicio, nomeDestino) || this.procurarCaminho(mapa, nomeDestino, nomeInicio)) {
            return 1;
        }

        mapa.addEdge(inicio, destino, peso);
        return 0;
    }

    @Override
    public boolean removerCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino) {

        if (mapa == null) {
            throw new IllegalArgumentException("O parametro é invalido");
        }

        try {
            mapa.removeEdge(this.procurarLocal(mapa, nomeInicio), this.procurarLocal(mapa, nomeDestino));
        } catch (EmptyCollectionException | ElementNotFoundException ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean alteralInfoAoCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino, double novoPeso) {

        if (mapa == null || novoPeso < 0) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        ILocal inicio = this.procurarLocal(mapa, nomeInicio);
        ILocal destino = this.procurarLocal(mapa, nomeDestino);

        if (inicio == null || destino == null || !(this.procurarCaminho(mapa, nomeInicio, nomeDestino) || this.procurarCaminho(mapa, nomeDestino, nomeInicio))) {
            return false;
        }

        mapa.addEdge(inicio, destino, novoPeso);
        return true;
    }

    @Override
    public boolean printInfoCaminhos(IMapa<ILocal> mapa) {

        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (mapa.isEmpty()) {
            return false;
        }

        Iterator<ICaminho<ILocal>> caminhos = mapa.getPaths();
        while (caminhos.hasNext()) {
            ICaminho<ILocal> caminho = caminhos.next();
            System.out.println("Caminho: " + caminho.getInicio().getName() + " <--> " + caminho.getDestino().getName() + " com distancia: " + caminho.getPeso());
        }

        return true;
    }

    /**
     * Adicionar um local ou locais á rota.
     *
     * @param rota rota a ser adicionado os locais ou local
     * @param caminhoIter caminho que tem os locais.
     */
    private void addLocalArota(UnorderedListADT<ILocal> rota, Iterator<ILocal> caminhoIter) {
        if (caminhoIter.hasNext()) {
            caminhoIter.next();
        }
        while (caminhoIter.hasNext()) {
            ILocal local = caminhoIter.next();
            rota.addRear(local);
        }
    }

    @Override
    public Iterator<ILocal> criarRotaVendedor(ILocal empresa, IVendedor vendedor, IMapa<ILocal> mapa) throws EmptyCollectionException {

        UnorderedListADT<ILocal> rota = new LinkedUnorderedList<>();
        Iterator<IMercado> mercadosIterator = vendedor.getMercadosAVisitar(mapa);
        ILocal localAtual = empresa;
        rota.addFront(empresa);

        while (mercadosIterator.hasNext()) {
            IMercado mercado = mercadosIterator.next();
            boolean existeCaminho = true;

            if (mercado.getClientes().size() != 0) {

                Iterator<ILocal> caminhoIter = mapa.iteratorShortestPath(localAtual, mercado);
                if (!caminhoIter.hasNext()) {
                    existeCaminho = false;
                } else {
                    this.addLocalArota(rota, caminhoIter);
                    localAtual = mercado;
                }
            }

            if (existeCaminho) {
                while (mercado.getClientes().size() != 0) {
                    int clienteConsume = mercado.getClientes().first();
                    int removidoVendedor = vendedor.removeStock(clienteConsume);

                    while (mercado.servirCliente(removidoVendedor) > 0) {
                        IArmazem armazem = this.encontrarVisitarArmazem(localAtual, vendedor, mapa);

                        if (armazem != null) {

                            Iterator<ILocal> caminhoIter = mapa.iteratorShortestPath(localAtual, armazem);
                            this.addLocalArota(rota, caminhoIter);
                            caminhoIter = mapa.iteratorShortestPath(armazem, localAtual);
                            this.addLocalArota(rota, caminhoIter);

                            clienteConsume = mercado.getClientes().first();
                            removidoVendedor = vendedor.removeStock(clienteConsume);
                        } else {
                            Iterator<ILocal> caminhoIter = mapa.iteratorShortestPath(localAtual, empresa);
                            this.addLocalArota(rota, caminhoIter);
                            return rota.iterator();
                        }
                    }
                }
            }
        }
        Iterator<ILocal> caminhoIter = mapa.iteratorShortestPath(localAtual, empresa);

        this.addLocalArota(rota, caminhoIter);

        return rota.iterator();
    }

    /**
     * Encontra o armazem mais perto e com mais mercadoria para reabastecer o
     * vendedor, e também reabastesse o vendedor, retirando stock ao armazem e
     * adicionado ao vendedor.
     *
     * @param mercado mercado onde o vendedor se econtra
     * @param vendedor vendedor
     * @param mapa mapa da empresa
     * @return Armazem mais perto, null se o mapa estiver vazio
     * @throws EmptyCollectionException se nao houver caminhos
     */
    private IArmazem encontrarVisitarArmazem(ILocal mercado, IVendedor vendedor, IMapa<ILocal> mapa) throws EmptyCollectionException {

        Iterator<ILocal> todosLocaisIterator = mapa.getVertices();
        UnorderedListADT<IArmazem> listaArmazens = new LinkedUnorderedList<>();

        while (todosLocaisIterator.hasNext()) {

            ILocal local = todosLocaisIterator.next();

            if (local instanceof Armazem) {
                IArmazem armazem = (IArmazem) local;

                if (armazem.getStock() > 0) {
                    listaArmazens.addRear(armazem);
                }
            }
        }

        IArmazem armazemMaisPerto = null;
        Iterator<IArmazem> armazemsIterator = listaArmazens.iterator();
        int espacoLivre = vendedor.getCapacidade() - vendedor.getStock();
        boolean found = false;
        boolean permission = true;

        if (armazemsIterator.hasNext()) {
            armazemMaisPerto = armazemsIterator.next();

            double min = Double.POSITIVE_INFINITY;

            if (armazemMaisPerto.getStock() >= espacoLivre) {
                min = mapa.shortestPathWeight(mercado, armazemMaisPerto);
                permission = false;
                found = true;
            }

            while (armazemsIterator.hasNext()) {

                IArmazem armazem = armazemsIterator.next();
                double comparar = mapa.shortestPathWeight(mercado, armazem);
                if (min > comparar && armazem.getStock() >= espacoLivre) {
                    min = comparar;
                    armazemMaisPerto = armazem;
                    found = true;
                    permission = false;
                } else if (min > comparar && !found) {
                    min = comparar;
                    armazemMaisPerto = armazem;
                    permission = true;
                }
            }
        }
        if (armazemMaisPerto != null) {
            if (found) {
                armazemMaisPerto.removeStock(espacoLivre);
                vendedor.addStock(espacoLivre);
            } else {
                if (permission) {
                    int valorRemovido = armazemMaisPerto.getStock();
                    vendedor.addStock(valorRemovido);
                    armazemMaisPerto.removeStock(valorRemovido);
                } else {
                    int valorRemovido = armazemMaisPerto.getStock() - vendedor.getStock();
                    vendedor.addStock(valorRemovido);
                    armazemMaisPerto.removeStock(valorRemovido);
                }
            }
        }
        return armazemMaisPerto;
    }

    @Override
    public JSONArray exportInfoAllCaminhos(IMapa<ILocal> mapa) {

        Iterator<ICaminho<ILocal>> caminhosIter = mapa.getPaths();
        JSONArray jsonCaminhos = new JSONArray();
        JSONObject jsonCaminho = new JSONObject();

        while (caminhosIter.hasNext()) {

            ICaminho<ILocal> caminho = caminhosIter.next();
            jsonCaminho.put("de", caminho.getInicio().getName());
            jsonCaminho.put("para", caminho.getDestino().getName());
            jsonCaminho.put("distancia", caminho.getPeso());

            jsonCaminhos.add(jsonCaminho);
        }

        return jsonCaminhos;
    }

    /**
     * Procurar caminho no mapa
     *
     * @param mapa mapa da empresa
     * @param localInicio nome do localAtual inicio
     * @param localDestino nome do localAtual destino
     * @return true se encontrar, false se nao existir caminho
     */
    private boolean procurarCaminho(IMapa<ILocal> mapa, String localInicio, String localDestino) {

        Iterator<ICaminho<ILocal>> caminhos = mapa.getPaths();

        while (caminhos.hasNext()) {
            ICaminho<ILocal> caminho = caminhos.next();

            if (caminho.getInicio().getName().equals(localInicio) && caminho.getDestino().getName().equals(localDestino)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Procurar um localAtual
     *
     * @param mapa mapa da emrpesa
     * @param nomeLocal nome do localAtual a procurar
     * @return O Local se existir, null se não existir esse localAtual.
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
}
