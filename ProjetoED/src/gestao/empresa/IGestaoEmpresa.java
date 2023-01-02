package gestao.empresa;

import Exceptions.NotFoundException;
import Exceptions.AlreadyExistException;
import objetos.ILocal;
import objetos.IMercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;
import Structures.Interfaces.UnorderedListADT;
import objetos.IVendedor;
import java.io.IOException;

public interface IGestaoEmpresa {

    /**
     * Adicionar local á empresa
     *
     * @param local local a ser adicionado
     * @throws AlreadyExistException se o local ja existir
     * @throws IllegalArgumentException se o param estiver invalido ou o local a
     * adicionar nao é valido
     */
    public void adicionarLocal(ILocal local) throws AlreadyExistException;

    /**
     * Remover local á empresa
     *
     * @param nomeLocal nome do local a ser removido
     * @throws NotFoundException se nao econtrar o local
     * @throws IllegalArgumentException se o param estiver invalido Estes dois
     * ultimos throws são lançados porque ao remover o Mercado do mapa
     * consequentemente tem de se remover dos vendedores também.
     * @throws EmptyCollectionException se a lista de mercados dos vendedores
     * estiver vazia
     * @throws ElementNotFoundException se nao existir o mercado na lista de
     * mercados a visitar
     */
    public void removerLocal(String nomeLocal) throws NotFoundException, EmptyCollectionException, ElementNotFoundException;

    /**
     * Adicionar um cliente ao mercado
     *
     * @param nomeMercado nome do mercado que irá receber o cliente
     * @param cliente cliente a ser inserido
     * @throws NotFoundException se o mercado nao existir
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void addClienteAoMercado(String nomeMercado, int cliente) throws NotFoundException;

    /**
     * Alterar informação de um Mercado
     *
     * @param novoNome novo nome para o mercado
     * @param clientes nova lista de clientes para o mercado
     * @param nomeMercado nome do mercado a ser alterado
     * @throws NotFoundException se nao encontrar o mercado
     * @throws IllegalArgumentException se algum dos param estiver invalido
     */
    public void alterarInfoMercado(String novoNome, QueueADT<Integer> clientes, String nomeMercado) throws NotFoundException;

    /**
     * Alterar informação de um Armazem
     *
     * @param novoNome novo nome para o armazem
     * @param novaCapacidade nova capacidade para o armazem
     * @param nomeArmazem nome do local a ser alterado armazem
     * @param novoStock novo stock para o armazem
     * @throws NotFoundException se nao encontrar o armazem
     * @throws IllegalArgumentException se algum dos param estiver invalido
     */
    public void alterarInfoArmazem(String novoNome, int novaCapacidade, int novoStock, String nomeArmazem) throws NotFoundException;

    /**
     * Listar um Local da empresa
     *
     * @param nomeLocal nome do local a ser listado
     * @throws NotFoundException se nao encontar o local
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void printInfoLocal(String nomeLocal) throws NotFoundException;

    /**
     * Listar todos os locais de um tipo
     *
     * @param tipoLocal tipo dos locais a ser listado
     * @throws NotFoundException se não existir armazens ou mercados
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void printInfoLocais(String tipoLocal) throws NotFoundException;

    /**
     * Exporta um local
     *
     * @param nomeLocal
     * @throws java.io.IOException se houver erro a escrever no ficheiro
     * @throws EmptyCollectionException se algum mercado não tiver clientes
     * @throws NotFoundException se nao existir local
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void ExportarLocal(String nomeLocal) throws IOException, EmptyCollectionException, NotFoundException;

    /**
     * Adiciona um Vendedor
     *
     * @param vendedor vendedor a ser adicionado
     * @throws AlreadyExistException se o vendedor ja existir
     * @throws IllegalArgumentException se o param estiver invalido ou o
     * vendedor a adicionar for invalido
     */
    public void adicionarVendedor(IVendedor vendedor) throws AlreadyExistException;

    /**
     * Remover um Vendedor
     *
     * @param idVendedor id do vendedor para ser removido
     * @throws NotFoundException se o vendedor nao existir
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void removerVendedor(int idVendedor) throws NotFoundException;

    /**
     * Alterar um vendedor
     *
     * @param idVendedor id do vendedor para ser alterado
     * @param novoNome novo nome do vendedor
     * @param novaCapacidade nova capacidade do vendedor
     * @param novoStock novo stock do vendedor
     * @param mercados_visitar nova lsiata dos mercados a visitar do vendedor
     * @throws NotFoundException se o vendedor nao existir
     * @throws IllegalArgumentException se algum dos param estiver invalido
     */
    public void alterarInfoVendedor(int idVendedor, String novoNome, int novaCapacidade, int novoStock, UnorderedListADT<IMercado> mercados_visitar) throws NotFoundException;

    /**
     * Adicionar um mercado a um vendedor
     *
     * @param idVendedor id do vendedor que vai ser adicionado o mercado
     * @param nomeMercado nome do mercado a ser adicionado
     * @throws NotFoundException se o vendedor ou o mercado nao existirem
     * @throws AlreadyExistException se o mercado ja existir na lista de
     * mercados a visitar
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void addMercadoAoVendedor(int idVendedor, String nomeMercado) throws NotFoundException, AlreadyExistException;

    /**
     * Remover mercado a um vendedor
     *
     * @param idVendedor id do vendedor que vai ser removido o mercado
     * @param nomeMercado nome do mercado a ser removido
     * @throws NotFoundException se o vendedor ou o mercado nao existirem
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void removerMercadoAoVendedor(int idVendedor, String nomeMercado) throws NotFoundException;

    /**
     * Listar a info de um vendedor
     *
     * @param idVendedor id do vendedor a ser listado
     * @throws NotFoundException se o vendedor nao existir
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void printInfoVendedor(int idVendedor) throws NotFoundException;

    /**
     * Listar todos os vendedores
     *
     * @throws NotFoundException se nao existir um unico vendedor
     */
    public void printInfoVendedores() throws NotFoundException;

    /**
     * Exportar um vendedor
     *
     * @param idVendedor id do vendedor a ser exportado
     * @throws NotFoundException se nao existir vendedor
     * @throws EmptyCollectionException se o vendedor não tiver mercados a visitar
     * @throws IOException algum erro a escrever no ficheiro
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void ExportarVendedor(int idVendedor) throws NotFoundException, EmptyCollectionException, IOException;

    /**
     * Adicionar caminho
     *
     * @param localInicio nome do local de inicio
     * @param localDestino nome do local de destino
     * @param peso peso do caminho
     * @throws AlreadyExistException caminho ja existe
     * @throws NotFoundException pelo menos um dos locais não existir
     * @throws IllegalArgumentException se o param estiver invalido
     */
    public void adicionarCaminho(String localInicio, String localDestino, double peso) throws AlreadyExistException, NotFoundException;

    /**
     * Remover um caminho
     *
     * @param localInicio nome do local de inicio
     * @param localDestino nome do local de destino
     * @throws NotFoundException se o caminho nao existir
     * @throws IllegalArgumentException se algum dos param estiver invalido
     */
    public void removerCaminho(String localInicio, String localDestino) throws NotFoundException;

    /**
     * Alterar o peso de um caminho
     *
     * @param localInicio nome do local de inicio
     * @param localDestino nome do local de destino
     * @param novoPeso novo peso do caminho
     * @throws NotFoundException se o caminho nao existir
     * @throws IllegalArgumentException se algum dos param estiver invalido
     */
    public void alterarCaminho(String localInicio, String localDestino, double novoPeso) throws NotFoundException;

    /**
     * Listar todos os caminhos
     *
     * @throws NotFoundException se nao existir caminhos
     */
    public void printInfoCaminhos() throws NotFoundException;

    /**
     * Criar a rota para satisfazer os mercados a visitar do vendedor
     *
     * @param idVendedor id do vendedor
     * @throws NotFoundException se nao existir vendedor
     * @throws EmptyCollectionException se nao existir caminhos
     */
    public void criarRotaVendedor(int idVendedor) throws NotFoundException, EmptyCollectionException;

    /**
     * Exportar a info de uma empresa
     * @throws IOException se correr mal a escrever no ficheiro
     * @throws EmptyCollectionException se os mercados nao tiverem clientes
     * @throws NotFoundException se nao existirem vendedores
     */
    public void ExportarEmpresa() throws IOException, EmptyCollectionException, NotFoundException;

    /**
     * Procurar um local
     *
     * @param nomeLocal nome do local a procurar
     * @return O local procurado, null se não existir esse local.
     */
    public ILocal procurarLocal(String nomeLocal);

}
