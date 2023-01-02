package gestao.locais.mercados;

import objetos.IMapa;
import objetos.ILocal;
import objetos.IMercado;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;
import org.json.simple.JSONObject;

public interface IGestaoMercado {
    
    /**
     * Validar os dados de um mercado
     * 
     * @param mapa mapa da empresa
     * @param mercado mercado a ser validado
     * @return  true se for valido, false se for invalido
     */
    public boolean validarInfoMercado(IMapa<ILocal> mapa, IMercado mercado);
    
    /**
     * Adicionar um cliente a um mercado
     * @param mapa mapa da empresa
     * @param nomeMercado nome do mercado
     * @param cliente consumo do cliente
     * @return true se adicionar, false se o mercado não existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean addClienteAoMercado(IMapa<ILocal> mapa, String nomeMercado, int cliente);
    
    /**
     * Alterar informação de um mercado
     * @param mapa mapa da empresa
     * @param novoNome novo nome
     * @param novosClientes nova lista de clientes
     * @param nomeLocal nome do mercado a ser alterado
     * @return 0 se alterar a info com sucesso, 1 se o mercado nao existir ou 
     * se o nome do local não for de um mercado e 2 se algum parametro de informaçao for invalido
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int alterarInfoAoMercado(IMapa<ILocal> mapa, String novoNome, QueueADT<Integer> novosClientes, String nomeLocal);
    
    /**
     * Listar a informaçao de todos os mercados
     * @param mapa mapa da empresa
     * @return true se listar, false se não existir mercados
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean printInfoMercados(IMapa<ILocal> mapa);
    
    /**
     * Resturna o JsonObject de um mercado
     * @param mapa mapa da empresa
     * @param nomeLocal nome do mercado
     * @return JsonObject com o conteudo do mercado, null se nao existir o mercado
     * @throws EmptyCollectionException se o mercado não tiver clientes.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public JSONObject exportInfoMercadoToJSON(IMapa<ILocal> mapa, String nomeLocal) throws EmptyCollectionException ;
}
