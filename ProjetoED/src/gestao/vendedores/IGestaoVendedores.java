package gestao.vendedores;

import Exceptions.NotFoundException;
import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import objetos.IVendedor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface IGestaoVendedores {
    
    public boolean validarInfoVendedor(IMapa<ILocal> mapa, IVendedor vendedor);

    /**
     * Adicionar um vendedor á empresa
     *
     * @param vendedor vendedor a ser adicionado
     * @return true se o vendedor for adicionado com sucesso, false se o
     * vendedor já existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean addVendedor(IVendedor vendedor);

    /**
     * Remover vendedor da empresa
     *
     * @param idVendedor id vendedor
     * @return true se remover, false se o vendedor não existir.
     */
    public boolean removerVendedor(int idVendedor);

    /**
     * Adicionar mercado ao vendedor
     *
     * @param mapa mapa da empresa
     * @param idVendedor id do vendedor
     * @param nomeMercado nome do mercado a ser adicionado
     * @return 2 se o mercado for adicionado com sucesso, 0 se o mercado nao
     * existir,1 se vendedor nao existir ou 3 se o mercado ja for da lista de mercados do vendedor
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int addMercadoAoVendedor(IMapa<ILocal> mapa, int idVendedor, String nomeMercado);

    /**
     * Remover mercado ao vendedor
     *
     * @param idVendedor id vendedor
     * @param nomeMercado nome do mercado a ser removido
     * @param mapa mapa da empresa
     * @return 2 se o mercado for removido com sucesso, 0 se o mercado nao
     * existir ou 1 se vendedor nao existir
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int removerMercadoAoVendedor(int idVendedor, String nomeMercado, IMapa<ILocal> mapa);

    /**
     * Remover mercado a todos os vendedores
     * 
     * @param nomeMercado nome do mercado a ser removido
     * @param mapa mapa da empresa
     * @return true se remover com sucesso, false se o mercado nao existir ou nao for um mercado.
     * @throws EmptyCollectionException se o vendedro não tiver mercados.
     * @throws ElementNotFoundException se o mercado não existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean removerMercadoAosVendedores(String nomeMercado, IMapa<ILocal> mapa) throws EmptyCollectionException, ElementNotFoundException;
    
    /**
     * Alterar toda a informação sobre um vendedor
     *
     * @param mapa mapa da emrpesa
     * @param idVendedor id vendedor
     * @param novoNome novo nome do vendedor
     * @param novaCapacidade nova capacidade do vendedor
     * @param novoStock novo stock do vendedor
     * @param mercados_visitar nova lista de mercados a visitar do vendedor
     * @return 0 se alterar a informação com sucesso, 1 se o vendedor nao
     * existir ou 2 se algum dos parametros de nova informação for invalido.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int alterarInfoVendedor(IMapa<ILocal> mapa, int idVendedor, String novoNome, int novaCapacidade, int novoStock, UnorderedListADT<IMercado> mercados_visitar);

    /**
     * Lista a informação de um vendedor
     *
     * @param idVendedor id vendedor
     * @return true se listar a informação corretamente, false se o vendedor nao
     * existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean printInfoVendedor(int idVendedor);

    /**
     * Lista a informação de todos os vendedores
     *
     * @return true se listar a informação corretamente, false se não existir
     * vendedores existir.
     */
    public boolean printInfoVendedores();
    
    /**
     * Procurar um vendedor
     * 
     * @param idVendedor id do vendedor a ser procurado
     * @return o vendedor
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public IVendedor procurarVendedor(int idVendedor);
    
    /**
     * Retorna um Json Object com a informação de um vendedor
     * 
     * @param idVendedor id do vendedor
     * @return um Json Object com a informação de um vendedor
     * @throws NotFoundException se vendedor nao existir
     */
    public JSONObject exportInfoVendedor(int idVendedor) throws NotFoundException;

    /**
     * Retorna um Json Array com a informação de todos os vendedores
     * 
     * @return um Json Array com a informação de todos os vendedores
     * @throws NotFoundException se nao existir vendedores
     */
    public JSONArray exportInfoAllVendedores() throws NotFoundException;
}
