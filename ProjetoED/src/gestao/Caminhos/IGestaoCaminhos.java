package gestao.caminhos;

import objetos.IMapa;
import objetos.ILocal;
import Structures.Exceptions.EmptyCollectionException;
import objetos.IVendedor;
import java.util.Iterator;
import org.json.simple.JSONArray;

public interface IGestaoCaminhos {
    
     /**
     * Adicionar um caminho ao mapa
     * 
     * @param mapa mapa da empresa
     * @param nomeInicio nome do local inicio
     * @param nomeDestino nome do local destino
     * @param peso peso do caminho
     * @return 0 se adicionar com sucesso, 1 se o caminho já existir ou 2 se pelo menos um dos locais nao existir
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int addCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino, double peso);
    
    /**
     * Remover caminho do mapa
     * 
     * @param mapa mapa da empresa
     * @param nomeInicio nome do local inicio
     * @param nomeDestino nome do local destino
     * @return true se remover com sucesso, false se o caminho nao existe ou o mapa está vazio
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean removerCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino);
    
    /**
     * Alterar a informação de um caminho
     * @param mapa mapa da empresa
     * @param nomeInicio nome do local inicio
     * @param nomeDestino nome do local destino
     * @param novoPeso novo peso
     * @return true se alterar info com sucesso, false se o caminho nao existir
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean alteralInfoAoCaminho(IMapa<ILocal> mapa, String nomeInicio, String nomeDestino, double novoPeso);
    
    /**
     * Lista a informação de todos os caminhos
     * 
     * @param mapa mapa da empresa
     * @return true se listar a informação corretamente, false se o local nao existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean printInfoCaminhos(IMapa<ILocal> mapa);
    
    /**
     * Criar a rota para um vendedor considerando o caminho mais perto, quando um vendedor tem de reabastecer, é gerada uma rota que identifica o caminho
     * mais curto entre a posição do vendedor e do armazém com capacidade atual para recarregar a mercadoria, se nao for possivel satisfazer mais clientes a rota 
     * é dada como concluida.
     * 
     * @param empresa local de partida
     * @param vendedor vendedor
     * @param mapa mapa da empresa
     * @return Rota do vendedor.
     * @throws EmptyCollectionException se nao houver caminhos
     */
    public Iterator<ILocal> criarRotaVendedor(ILocal empresa, IVendedor vendedor, IMapa<ILocal> mapa) throws EmptyCollectionException;
    
    /**
     * Retorna um Json Array com a informação de todos os caminhos.
     * @param mapa mapa da empresa
     * @return um Json Array com a informação de todos os caminhos.
     */
    public JSONArray exportInfoAllCaminhos(IMapa<ILocal> mapa);
}
