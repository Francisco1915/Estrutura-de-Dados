package gestao.locais;

import objetos.IMapa;
import objetos.ILocal;
import org.json.simple.JSONObject;

public interface IGestaoLocal {
    
    /**
     * Adicionar um local ao mapa
     * 
     * @param mapa mapa da empresa
     * @param local novo local
     * @return true se adicionar com sucesso, false se o local já existir
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean addLocal(IMapa<ILocal> mapa, ILocal local);
    
    /**
     * Remover local do mapa
     * 
     * @param mapa mapa da empresa
     * @param nomeLocal nome do local a remover
     * @return true se remover com sucesso, false se o local nao existe ou o mapa está vazio
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean removerLocal(IMapa<ILocal> mapa, String nomeLocal);
       
    /**
     * Exportar a informação sobre um local
     * 
     * @param mapa mapa da empresa
     * @param nomeLocal nome do local a ser exportada a informação
     * @return Json do local, null se não existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public JSONObject ExportLocal(IMapa<ILocal> mapa, String nomeLocal); 
    
    /**
     * Lista a informação de um local
     * 
     * @param mapa mapa da empresa
     * @param nomeLocal nome do local
     * @return true se listar a informação corretamente, false se o local nao existir.
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean printInfoLocal(IMapa<ILocal> mapa, String nomeLocal);
}
