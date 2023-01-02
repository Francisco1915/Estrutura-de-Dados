package gestao.locais.armazens;

import objetos.IMapa;
import objetos.IArmazem;
import objetos.ILocal;
import org.json.simple.JSONObject;


public interface IGestaoArmazem {
    
    /**
     * Valida os parametros de um armazem
     * 
     * @param armazem armazem a ser validado
     * @return true se o armazem for valido, false se for invalido
     */
    public boolean validarInfoArmazem(IArmazem armazem);
    
    /**
     * Lista todos os aramzens do mapa
     * @param mapa mapa da empresa
     * @return true se listar com sucesso, false se não existir armazens
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public boolean printInfoArmazens(IMapa<ILocal> mapa);
    
    /**
     * Alterar informação de um armazem
     * @param mapa mapa da empresa
     * @param novoNome novo nome
     * @param novaCapacidade nova capacidade
     * @param nomeLocal nome do armazem a alterar
     * @param novoStock novo stock
     * @return 0 se alterar a info com sucesso, 1 se o armazem nao existir ou o nome do local
     * não ser de um armazem e 2 se algum dos parametros de nova informacao for invalido
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public int alterarInfoArmazen(IMapa<ILocal> mapa, String novoNome, int novaCapacidade, int novoStock, String nomeLocal);
    
    /**
     * Resturna o JsonObject de um armazem
     * @param mapa mapa da empresa
     * @param nomeLocal nome do armazem
     * @return JsonObject com o conteudo do armazem, null se nao existir o armazem
     * @throws IllegalArgumentException Um dos parametros é invalido
     */
    public JSONObject exportInfoArmazemToJSON(IMapa<ILocal> mapa, String nomeLocal);
}
