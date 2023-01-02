package gestao.empresa;

import Exceptions.NotFoundException;
import Exceptions.AlreadyExistException;
import gestao.locais.armazens.GestaoArmazem;
import gestao.caminhos.GestaoCaminhos;
import objetos.Empresa;
import gestao.locais.GestaoLocal;
import gestao.locais.mercados.GestaoMercado;
import objetos.ILocal;
import gestao.vendedores.GestaoVendedores;
import gestao.locais.IGestaoLocal;
import gestao.locais.mercados.IGestaoMercado;
import gestao.locais.armazens.IGestaoArmazem;
import gestao.caminhos.IGestaoCaminhos;
import objetos.Armazem;
import objetos.IArmazem;
import objetos.IMercado;
import objetos.Mercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.QueueADT;
import Structures.Interfaces.UnorderedListADT;
import gestao.vendedores.IGestaoVendedores;
import objetos.IVendedor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import objetos.IMapa;
import objetos.Mapa;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GestaoEmpresa implements IGestaoEmpresa {

    private IMapa<ILocal> mapa;
    private IGestaoVendedores gestaoVendedores;
    private IGestaoLocal gestaoLocal;
    private IGestaoArmazem gestaoArmazem;
    private IGestaoMercado gestaoMercado;
    private IGestaoCaminhos gestaoCaminhos;
    private ILocal root;

    public GestaoEmpresa() {
        this.mapa = new Mapa();
        this.gestaoLocal = new GestaoLocal();
        this.gestaoCaminhos = new GestaoCaminhos();
        this.gestaoArmazem = new GestaoArmazem();
        this.gestaoMercado = new GestaoMercado();
        this.gestaoVendedores = new GestaoVendedores();
    }

    public GestaoEmpresa(ILocal empresa) {
        this.mapa = new Mapa();
        this.gestaoLocal = new GestaoLocal();
        this.gestaoVendedores = new GestaoVendedores();
        this.gestaoCaminhos = new GestaoCaminhos();
        this.gestaoArmazem = new GestaoArmazem();
        this.gestaoMercado = new GestaoMercado();
        this.root = empresa;
        this.mapa.addVertex(empresa);
    }

    @Override
    public void adicionarLocal(ILocal local) throws AlreadyExistException {

        if (local == null) {
            throw new IllegalArgumentException("O parametro é invalido");
        }

        if (local instanceof Empresa) {
            if (!(local.getName() == "")) {
                if (this.root != null) {
                    throw new AlreadyExistException("Empresa já existe");
                }
                this.root = local;
            } else {
                throw new IllegalArgumentException("A Empresa é invalida");
            }
        }

        if (local instanceof Armazem) {
            if (!this.gestaoArmazem.validarInfoArmazem((IArmazem) local)) {
                throw new IllegalArgumentException("O armazem é invalido");
            }
        }

        if (local instanceof Mercado) {
            if (!this.gestaoMercado.validarInfoMercado(this.mapa, (IMercado) local)) {
                throw new IllegalArgumentException("O Mercado é invalido");
            }
        }

        if (!this.gestaoLocal.addLocal(this.mapa, local)) {
            throw new AlreadyExistException("Local já existe");
        }
    }

    @Override
    public void removerLocal(String nomeLocal) throws NotFoundException, EmptyCollectionException, ElementNotFoundException {

        if (nomeLocal == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = this.procurarLocal(nomeLocal);

        if (local != null && local instanceof Mercado) {
            this.gestaoVendedores.removerMercadoAosVendedores(nomeLocal, this.mapa);
        }

        if (!this.gestaoLocal.removerLocal(this.mapa, nomeLocal)) {
            throw new NotFoundException("Local nao existe");
        }
    }

    @Override
    public void addClienteAoMercado(String nomeMercado, int cliente) throws NotFoundException {

        if (nomeMercado == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (!(this.gestaoMercado.addClienteAoMercado(this.mapa, nomeMercado, cliente))) {
            throw new NotFoundException("Mercado não encontrado");
        }

    }

    @Override
    public void alterarInfoMercado(String novoNome, QueueADT<Integer> clientes, String nomeMercado) throws NotFoundException {

        if (nomeMercado == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        int retorno = this.gestaoMercado.alterarInfoAoMercado(this.mapa, novoNome, clientes, nomeMercado);

        if (retorno == 2) {
            throw new IllegalArgumentException("Aletrações invalidas");
        }

        if (retorno == 1) {
            throw new NotFoundException("Mercado não encontrado");
        }
    }

    @Override
    public void alterarInfoArmazem(String novoNome, int novaCapacidade, int novoStock, String nomeArmazem) throws NotFoundException {

        if (nomeArmazem == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        int retorno = this.gestaoArmazem.alterarInfoArmazen(this.mapa, novoNome, novaCapacidade, novoStock, nomeArmazem);

        if (retorno == 2) {
            throw new IllegalArgumentException("Aletrações invalidas");
        }

        if (retorno == 1) {
            throw new NotFoundException("Armazem não encontrado");
        }
    }

    @Override
    public void printInfoLocal(String nomeLocal) throws NotFoundException {

        if (nomeLocal == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (!this.gestaoLocal.printInfoLocal(this.mapa, nomeLocal)) {
            throw new NotFoundException("Local não encontrado");
        }
    }

    @Override
    public void printInfoLocais(String tipoLocal) throws NotFoundException {

        if (tipoLocal == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (tipoLocal.equals("Armazens")) {
            if (!this.gestaoArmazem.printInfoArmazens(this.mapa)) {
                throw new NotFoundException("Não existe armazens");
            }
        }

        if (tipoLocal.equals("Mercados")) {
            if (!this.gestaoMercado.printInfoMercados(this.mapa)) {
                throw new NotFoundException("Não existe mercados");
            }
        }
    }

    @Override
    public void ExportarLocal(String nomeLocal) throws IOException, EmptyCollectionException, NotFoundException {

        if (nomeLocal == "") {
            throw new IllegalArgumentException("Parametro invalido");
        }

        ILocal local = this.procurarLocal(nomeLocal);
        
        if (local == null) {
            throw new NotFoundException("Local nao existe");
        }

        if (local != null && local instanceof Empresa) {

            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("empresa.json"));
            buffWrite.write(this.gestaoLocal.ExportLocal(this.mapa, nomeLocal).toJSONString());
            buffWrite.close();
        }

        if (local != null && local instanceof Armazem) {

            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("armazem.json"));
            buffWrite.write(this.gestaoArmazem.exportInfoArmazemToJSON(this.mapa, nomeLocal).toJSONString());
            buffWrite.close();
        }

        if (local != null && local instanceof Mercado) {

            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("mercado.json"));
            buffWrite.write(this.gestaoMercado.exportInfoMercadoToJSON(this.mapa, nomeLocal).toJSONString());
            buffWrite.close();
        }
    }

    @Override
    public void adicionarVendedor(IVendedor vendedor) throws AlreadyExistException {

        if (vendedor == null) {
            throw new IllegalArgumentException("O parametro é invalido");
        }

        if (!this.gestaoVendedores.validarInfoVendedor(this.mapa, vendedor)) {
            throw new IllegalArgumentException("O Vendedor é invalido");
        }

        if (!this.gestaoVendedores.addVendedor(vendedor)) {
            throw new AlreadyExistException("Vendedor já existe");
        }
    }

    @Override
    public void removerVendedor(int idVendedor) throws NotFoundException {

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (!this.gestaoVendedores.removerVendedor(idVendedor)) {
            throw new NotFoundException("Vendedor não existe");
        }
    }

    @Override
    public void alterarInfoVendedor(int idVendedor, String novoNome, int novaCapacidade, int novoStock, UnorderedListADT<IMercado> mercados_visitar) throws NotFoundException {

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        int retorno = this.gestaoVendedores.alterarInfoVendedor(mapa, idVendedor, novoNome, novaCapacidade, novoStock, mercados_visitar);

        if (retorno == 2) {
            throw new IllegalArgumentException("Aletrações invalidas");
        }

        if (retorno == 1) {
            throw new NotFoundException("Vendedor não encontrado");
        }
    }

    @Override
    public void addMercadoAoVendedor(int idVendedor, String nomeMercado) throws NotFoundException, AlreadyExistException {

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        int retorno = this.gestaoVendedores.addMercadoAoVendedor(this.mapa, idVendedor, nomeMercado);

        if (retorno == 3) {
            throw new AlreadyExistException("Mercado já existe na lista");
        }

        if (retorno == 0) {
            throw new NotFoundException("Mercado não existe");
        }

        if (retorno == 1) {
            throw new NotFoundException("Vendedor não existe");
        }
    }

    @Override
    public void removerMercadoAoVendedor(int idVendedor, String nomeMercado) throws NotFoundException {

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        int retorno = this.gestaoVendedores.removerMercadoAoVendedor(idVendedor, nomeMercado, mapa);

        if (retorno == 0) {
            throw new NotFoundException("Mercado não existe");
        }

        if (retorno == 1) {
            throw new NotFoundException("Vendedor não existe");
        }
    }

    @Override
    public void printInfoVendedor(int idVendedor) throws NotFoundException {

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        if (!this.gestaoVendedores.printInfoVendedor(idVendedor)) {
            throw new NotFoundException("Vendedor não existe");
        }
    }

    @Override
    public void printInfoVendedores() throws NotFoundException {

        if (!this.gestaoVendedores.printInfoVendedores()) {
            throw new NotFoundException("Não existem vendedores");
        }
    }

    @Override
    public void ExportarVendedor(int idVendedor) throws NotFoundException, EmptyCollectionException, IOException {

        if (idVendedor < 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("vendedor.json"));
        buffWrite.write(this.gestaoVendedores.exportInfoVendedor(idVendedor).toJSONString());
        buffWrite.close();
    }

    @Override
    public void adicionarCaminho(String localInicio, String localDestino, double peso) throws AlreadyExistException, NotFoundException {

        if (localInicio == "" || localDestino == "" || peso < 0) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }
        
        int retorno = this.gestaoCaminhos.addCaminho(this.mapa, localInicio, localDestino, peso);
        
        if (retorno == 2) {
            throw new NotFoundException("Pelo menos um dos locais não existe");
        }

        if (retorno == 1) {
            throw new AlreadyExistException("Caminho já existe");
        }
    }

    @Override
    public void removerCaminho(String localInicio, String localDestino) throws NotFoundException {

        if (localInicio == "" || localDestino == "") {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        if (!this.gestaoCaminhos.removerCaminho(this.mapa, localInicio, localDestino)) {
            throw new NotFoundException("Caminho não encontrado");
        }
    }

    @Override
    public void alterarCaminho(String localInicio, String localDestino, double novoPeso) throws NotFoundException {

        if (localInicio == "" || localDestino == "" || novoPeso < 0) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        if (!this.gestaoCaminhos.alteralInfoAoCaminho(this.mapa, localInicio, localDestino, novoPeso)) {
            throw new NotFoundException("Caminho não encontrado");
        }
    }

    @Override
    public void printInfoCaminhos() throws NotFoundException {

        if (!this.gestaoCaminhos.printInfoCaminhos(this.mapa)) {
            throw new NotFoundException("Não existem caminhos");
        }
    }

    @Override
    public void criarRotaVendedor(int idVendedor) throws NotFoundException, EmptyCollectionException {

        if (this.root == null) {
            throw new NotFoundException("Empresa nao existe");
        }

        if (idVendedor <= 0) {
            throw new IllegalArgumentException("Parametro invalido");
        }

        IVendedor vendedor = this.gestaoVendedores.procurarVendedor(idVendedor);

        if (vendedor == null) {
            throw new NotFoundException("Vendedor nao existe");
        }

        Iterator<ILocal> rota = this.gestaoCaminhos.criarRotaVendedor(this.root, vendedor, this.mapa);

        ILocal local = rota.next();

        if (!rota.hasNext()) {
            throw new NotFoundException("Não foi encontra nenhuma rota, verificar dados do vendedor");
        }

        System.out.print(local.getName());
        while (rota.hasNext()) {

            System.out.print(" -> " + rota.next().getName());
        }
        System.out.println("");
    }

    @Override
    public void ExportarEmpresa() throws IOException, EmptyCollectionException, NotFoundException {
        
        JSONObject empresa = new JSONObject();
        
        empresa.put("name", this.root.getName());
        Iterator<ILocal> locais = this.mapa.getVertices();
        JSONArray jsonLocais = new JSONArray();
        
        while (locais.hasNext()) {
            
            ILocal current = locais.next();
            
            if (current instanceof Mercado) {
                jsonLocais.add(this.gestaoMercado.exportInfoMercadoToJSON(this.mapa, current.getName()));
            }    
            
            if (current instanceof Armazem) {
                jsonLocais.add(this.gestaoArmazem.exportInfoArmazemToJSON(this.mapa, current.getName()));
            }    
        }
        
        empresa.put("locais", jsonLocais);
        empresa.put("vendedores", this.gestaoVendedores.exportInfoAllVendedores());
        empresa.put("caminhos", this.gestaoCaminhos.exportInfoAllCaminhos(this.mapa));
        
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("empresaInfo.json"));
        buffWrite.write(empresa.toJSONString());
        buffWrite.close();
    }

    @Override
    public ILocal procurarLocal(String nomeLocal) {

        Iterator<ILocal> iter = this.mapa.getVertices();

        while (iter.hasNext()) {

            ILocal current = iter.next();
            if (current.getName().equals(nomeLocal)) {
                return current;
            }
        }
        return null;
    }
}
