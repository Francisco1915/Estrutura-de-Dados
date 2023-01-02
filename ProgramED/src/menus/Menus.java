package menus;

import Exceptions.*;
import Structures.Exceptions.*;
import Structures.Interfaces.*;
import Structures.Linked.*;
import gestao.empresa.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import objetos.*;
import org.json.simple.parser.ParseException;

public class Menus {

    private Scanner scanChoice;
    private Scanner scanChoice1;
    private IGestaoEmpresa empresaManagement;
    private Importer importer;

    public Menus() {
    }

    public void menuPrincipal() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Principal");
                System.out.println("    1 - Criar Empresa");
                System.out.println("    2 - Importar ficheiro");
                System.out.println("    0 - Sair");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 2);
            switch (choiceEntry) {
                case 1:
                    this.empresaManagement = new GestaoEmpresa(menuCriarEmpresa());
                    this.menuGestao();
                    break;
                case 2:
                try {
                    this.importer = new Importer();
                    this.empresaManagement = this.importer.importarGestaoEmpresa();
                } catch (IOException | ParseException | NotFoundException ex) {
                    System.out.println(ex);
                    return;
                }
                this.menuGestao();
                break;
                case 0:
                    return;
                default:
                    this.menuPrincipal();
                    break;
            }
        } catch (InputMismatchException ex) {
            System.out.println(ex);
            this.menuPrincipal();
        }
    }

    public ILocal menuCriarEmpresa() {

        scanChoice = new Scanner(System.in);
        scanChoice1 = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Empresa");
        System.out.println("=========================================================");

        String nome = "";
        do {
            System.out.println("Nome: ");
            nome = scanChoice1.nextLine();
            if (nome == "") {
                System.out.println("Nome invalido");
            }
        } while (nome == "");

        ILocal newEmpresa = new Empresa(nome);

        return newEmpresa;
    }

    public void menuGestao() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao");
                System.out.println("    1 - Gestao Locais");
                System.out.println("    2 - Gestao Armazens");
                System.out.println("    3 - Gestao Mercados");
                System.out.println("    4 - Gestao Vendedores");
                System.out.println("    5 - Gestao Rotas");
                System.out.println("    0 - Sair");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 5);
            switch (choiceEntry) {
                case 1:
                    this.menuGestaoLocal();
                    break;
                case 2:
                    this.menuGestaoArmazens();
                    break;
                case 3:
                    this.menuGestaoMercados();
                    break;
                case 4:
                    this.menuGestaoVendedor();
                    break;
                case 5:
                    this.menuGestaoCaminhos();
                    break;
                case 0:
                    System.out.println("A sair... ... ...");
                    return;
                default:
                    break;
            }
        } catch (InputMismatchException ex) {
            System.out.println(ex + "--Inserir os dados corretamente!! (Não colocar letras onde devem ser números)");
            this.menuGestao();
        }
    }

    public void menuGestaoLocal() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;

            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao Local");
                System.out.println("    1 - Criar Armazem");
                System.out.println("    2 - Criar Mercado");
                System.out.println("    3 - Remover Local");
                System.out.println("    4 - Listar um Local");
                System.out.println("    5 - Listar um tipo de Locais");
                System.out.println("    6 - Exportar um local");
                System.out.println("    0 - Voltar");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 6);

            switch (choiceEntry) {
                case 1:
                    this.menuCriarArmazem();
                    this.menuGestaoLocal();
                    break;
                case 2:
                    this.menuCriarMercado();
                    this.menuGestaoLocal();
                    break;
                case 3:
                    this.menuRemoverLocal();
                    this.menuGestaoLocal();
                    break;
                case 4:
                    this.menuListarLocal();
                    this.menuGestaoLocal();
                    break;
                case 5:
                    this.menuListarLocais();
                    this.menuGestaoLocal();
                    break;
                case 6:
                    this.menuExportalLocal();
                    this.menuGestaoLocal();
                case 0:
                    this.menuGestao();
                    break;
                default:
                    this.menuGestaoLocal();
                    break;
            }
        } catch (AlreadyExistException | NotFoundException | ElementNotFoundException | EmptyCollectionException | IllegalArgumentException | IOException ex) {
            System.out.println(ex);
            this.menuGestaoLocal();
        }
    }

    public void menuGestaoArmazens() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao Armazens");
                System.out.println("    1 - Alterar Armazem");
                System.out.println("    0 - Voltar");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 1);
            switch (choiceEntry) {
                case 1:
                    this.menuAlterarArmazem();
                    this.menuGestaoArmazens();
                    break;
                case 0:
                    this.menuGestao();
                    break;
                default:
                    this.menuGestaoArmazens();
                    break;
            }
        } catch (NotFoundException ex) {
            System.out.println(ex);
            this.menuGestaoArmazens();
        }
    }

    public void menuGestaoMercados() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao Mercados");
                System.out.println("    1 - Alterar Mercado");
                System.out.println("    2 - Adicionar Clientes a um Mercado");
                System.out.println("    0 - Voltar");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 2);
            switch (choiceEntry) {
                case 1:
                    this.menuAlterarMercado();
                    this.menuGestaoMercados();
                    break;
                case 2:
                    this.menuAddClienteAoMercado();
                    this.menuGestao();
                    break;
                case 0:
                    this.menuGestao();
                    break;
                default:
                    this.menuGestaoMercados();
                    break;
            }
        } catch (NotFoundException | IllegalArgumentException ex) {
            System.out.println(ex);
            this.menuGestaoMercados();
        }
    }

    public void menuGestaoVendedor() {

        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao Vendedor");
                System.out.println("    1 - Criar Vendedor");
                System.out.println("    2 - Remover Vendedor");
                System.out.println("    3 - Alterar Vendedor");
                System.out.println("    4 - Adicionar mercado a visitar a um Vendedor");
                System.out.println("    5 - Remover mercado a visitar a um Vendedor");
                System.out.println("    6 - Listar um vendedor.");
                System.out.println("    7 - Listar todos os vendedores.");
                System.out.println("    8 - Exportar um vendedor.");
                System.out.println("    0 - Voltar");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 8);
            switch (choiceEntry) {
                case 1:
                    this.menuCriarVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 2:
                    this.menuRemoverVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 3:
                    this.menuAlterarInfoVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 4:
                    this.menuAdicionarMercadoVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 5:
                    this.menuRemoverMercadoVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 6:
                    this.menuListarVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 7:
                    this.empresaManagement.printInfoVendedores();
                    this.menuGestaoVendedor();
                    break;
                case 8:
                    this.menuExportarInfoVendedor();
                    this.menuGestaoVendedor();
                    break;
                case 0:
                    this.menuGestao();
                    break;
                default:
                    this.menuGestaoVendedor();
                    break;
            }
        } catch (IllegalArgumentException | AlreadyExistException | NotFoundException | EmptyCollectionException | IOException ex) {
            System.out.println(ex);
            this.menuGestaoVendedor();
        }
    }

    public void menuGestaoCaminhos() {
        try {
            scanChoice = new Scanner(System.in);
            int choiceEntry = -1;
            do {
                System.out.flush();
                System.out.println("\n=========================================================");
                System.out.println("-> Menu Gestao Caminhos");
                System.out.println("    1 - Criar Caminho");
                System.out.println("    2 - Remover Caminho");
                System.out.println("    3 - Alterar Caminho");
                System.out.println("    4 - Listar Caminhos");
                System.out.println("    5 - Criar Rota ao Vendedor");
                System.out.println("    0 - Voltar");
                System.out.println("=========================================================");
                choiceEntry = scanChoice.nextInt();
            } while (choiceEntry < 0 || choiceEntry > 5);
            switch (choiceEntry) {
                case 1:
                    this.menuCriarCaminho();
                    this.menuGestaoCaminhos();
                    break;
                case 2:
                    this.menuRemoverCaminho();
                    this.menuGestaoCaminhos();
                    break;
                case 3:
                    this.menuAlterarCaminho();
                    this.menuGestaoCaminhos();
                    break;
                case 4:
                    this.empresaManagement.printInfoCaminhos();
                    this.menuGestaoCaminhos();
                    break;
                case 5:
                    this.menuCriarRotaVendedor();
                    this.menuGestaoCaminhos();
                    break;
                case 0:
                    this.menuGestao();
                    break;
                default:
                    this.menuGestaoCaminhos();
                    break;
            }
        } catch (NotFoundException | IllegalArgumentException | AlreadyExistException | EmptyCollectionException ex) {
            System.out.println(ex);
            this.menuGestaoCaminhos();
        }
    }

    public void menuCriarRotaVendedor() throws NotFoundException, EmptyCollectionException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Rota ao Vendedor");
        System.out.println("=========================================================");

        System.out.println("Id Vendedor: ");
        int id = scanChoice.nextInt();

        this.empresaManagement.criarRotaVendedor(id);
    }

    public void menuAlterarCaminho() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Alterar Caminho");
        System.out.println("=========================================================");

        System.out.println("Inicio: ");
        String inicio = scanChoice.nextLine();

        System.out.println("Destino: ");
        String destino = scanChoice.nextLine();

        System.out.println("Nova Distancia em km: ");
        double novoPeso = scanChoice.nextDouble();

        this.empresaManagement.alterarCaminho(inicio, destino, novoPeso);
    }

    public void menuCriarCaminho() throws AlreadyExistException, NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Caminho");
        System.out.println("=========================================================");

        System.out.println("Inicio: ");
        String inicio = scanChoice.nextLine();

        System.out.println("Destino: ");
        String destino = scanChoice.nextLine();

        System.out.println("Distancia em km: ");
        double peso = scanChoice.nextDouble();

        this.empresaManagement.adicionarCaminho(inicio, destino, peso);
    }

    public void menuRemoverCaminho() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Remover Caminho");
        System.out.println("=========================================================");

        System.out.println("Inicio: ");
        String inicio = scanChoice.nextLine();

        System.out.println("Destino: ");
        String destino = scanChoice.nextLine();

        this.empresaManagement.removerCaminho(inicio, destino);
    }

    public void menuListarVendedor() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Listar um vendedor");
        System.out.println("=========================================================");

        System.out.println("Id do vendedor: ");
        int id = scanChoice.nextInt();

        this.empresaManagement.printInfoVendedor(id);
    }

    public void menuExportarInfoVendedor() throws NotFoundException, EmptyCollectionException, IOException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu exportar informação de um vendedor");
        System.out.println("=========================================================");

        System.out.println("Id do vendedor: ");
        int id = scanChoice.nextInt();

        this.empresaManagement.ExportarVendedor(id);
    }

    public void menuAlterarInfoVendedor() throws NotFoundException {

        scanChoice = new Scanner(System.in);
        scanChoice1 = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Alterar Vendedro");
        System.out.println("=========================================================");

        System.out.println("Id do vendedor a alterar: ");
        int id = scanChoice.nextInt();

        System.out.println("Se deseja manter o mesmo nome escreva-o de novo!!");
        System.out.println("Novo nome: ");
        String novoNome = scanChoice1.nextLine();

        System.out.println("Se deseja manter a mesma capacidade digite -1!!");
        System.out.println("Nova capacidade: ");
        int novaCap = scanChoice.nextInt();

        System.out.println("Se deseja manter o mesmo stock digite -1!!");
        System.out.println("Novo stock: ");
        int novoStock = scanChoice.nextInt();

        System.out.println("Se não pertender alterar mercados visitados insira -1, se pretender alterar digite outro numero!!");
        int opcao = scanChoice.nextInt();

        if (opcao != -1) {
            System.out.println("Insira 'Feito' quando colocar todos os Mercados");

            UnorderedListADT<IMercado> mercados_visitar = new LinkedUnorderedList<>();
            String nomeMercado = "";
            do {
                System.out.println("Mercado: ");
                nomeMercado = scanChoice1.nextLine();
                if (!nomeMercado.equals("Feito")) {

                    ILocal mercado = this.empresaManagement.procurarLocal(nomeMercado);
                    if (this.empresaManagement.procurarLocal(nomeMercado) != null && mercado instanceof Mercado) {
                        mercados_visitar.addRear((IMercado) mercado);
                    } else {
                        System.out.println("Mercado não existe");
                    }
                }
            } while (!nomeMercado.equals("Feito"));
            this.empresaManagement.alterarInfoVendedor(id, novoNome, novaCap, novoStock, mercados_visitar);
        } else {
            UnorderedListADT<IMercado> mercados_visitar = null;
            this.empresaManagement.alterarInfoVendedor(id, novoNome, novaCap, novoStock, mercados_visitar);
        }
    }

    public void menuAdicionarMercadoVendedor() throws NotFoundException, AlreadyExistException {

        scanChoice = new Scanner(System.in);
        scanChoice1 = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu adicionar Mercado ao Vendedor");
        System.out.println("=========================================================");

        System.out.println("Id do Vendedor: ");
        int id = scanChoice.nextInt();

        System.out.println("Nome do Mercado: ");
        String mercado = scanChoice1.nextLine();

        this.empresaManagement.addMercadoAoVendedor(id, mercado);
    }

    public void menuRemoverMercadoVendedor() throws NotFoundException, AlreadyExistException {

        scanChoice = new Scanner(System.in);
        scanChoice1 = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu remover Mercado ao Vendedor");
        System.out.println("=========================================================");

        System.out.println("Id do Vendedor: ");
        int id = scanChoice.nextInt();

        System.out.println("Nome do Mercado: ");
        String mercado = scanChoice1.nextLine();

        this.empresaManagement.removerMercadoAoVendedor(id, mercado);
    }

    public void menuRemoverVendedor() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Remover Vendedor");
        System.out.println("=========================================================");

        System.out.println("Id: ");
        int nome = scanChoice.nextInt();

        this.empresaManagement.removerVendedor(nome);
    }

    public void menuExportalLocal() throws IOException, EmptyCollectionException, NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Exportar Local");
        System.out.println("=========================================================");

        System.out.println("Nome: ");
        String nome = scanChoice.nextLine();

        this.empresaManagement.ExportarLocal(nome);
    }

    public void menuAddClienteAoMercado() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu adicionar Cliente ao Mercado");
        System.out.println("=========================================================");

        System.out.println("Nome do Mercado: ");
        String nome = scanChoice.nextLine();

        System.out.println("Cliente: ");

        int cliente = scanChoice.nextInt();

        this.empresaManagement.addClienteAoMercado(nome, cliente);
    }

    public void menuCriarArmazem() throws AlreadyExistException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Armazem");
        System.out.println("=========================================================");

        System.out.println("Nome: ");
        String nome = scanChoice.nextLine();

        System.out.println("Capacidade: ");
        int capacidade = scanChoice.nextInt();

        System.out.println("Stock: ");
        int stock = scanChoice.nextInt();

        ILocal newArmazem = new Armazem(nome, capacidade, stock);

        this.empresaManagement.adicionarLocal(newArmazem);
    }

    public void menuCriarMercado() throws AlreadyExistException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Mercado");
        System.out.println("=========================================================");

        System.out.println("Nome: ");
        String nome = scanChoice.nextLine();

        System.out.println("Insira 0 quando colocar todos os clientes");

        QueueADT<Integer> clientes = new LinkedQueue<>();
        int cliente = -1;

        IMercado newMercado = new Mercado(nome, clientes);
        do {
            System.out.println("Cliente: ");
            cliente = scanChoice.nextInt();
            if (cliente <= 0) {
                System.out.println("Cliente invalido");
            } else {
                clientes.enqueue(cliente);
            }
        } while (cliente != 0);

        this.empresaManagement.adicionarLocal(newMercado);
    }

    public void menuCriarVendedor() throws AlreadyExistException {

        scanChoice = new Scanner(System.in);
        scanChoice1 = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Criar Vendedor");
        System.out.println("=========================================================");

        System.out.println("Nome: ");
        String nome = scanChoice1.nextLine();

        System.out.println("Id: ");
        int id = scanChoice.nextInt();

        System.out.println("Capacidade: ");
        int capacidade = scanChoice.nextInt();

        System.out.println("Insira 'Feito' quando colocar todos os Mercados");

        UnorderedListADT<IMercado> mercados_visitar = new LinkedUnorderedList<>();
        String nomeMercado = "";
        do {
            System.out.println("Mercado: ");
            nomeMercado = scanChoice1.nextLine();
            if (!nomeMercado.equals("Feito")) {

                ILocal mercado = this.empresaManagement.procurarLocal(nomeMercado);
                if (this.empresaManagement.procurarLocal(nomeMercado) != null && mercado instanceof Mercado) {
                    mercados_visitar.addRear((IMercado) mercado);
                } else {
                    System.out.println("Mercado não existe");
                }
            }
        } while (!nomeMercado.equals("Feito"));

        IVendedor vendedor = new Vendedor(id, nome, capacidade, capacidade, mercados_visitar);

        this.empresaManagement.adicionarVendedor(vendedor);
    }

    public void menuAlterarArmazem() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Alterar Armazem");
        System.out.println("=========================================================");

        System.out.println("Nome do armazem a alterar: ");
        String nome = scanChoice.nextLine();

        System.out.println("Se deseja manter o mesmo nome escreva-o de novo!!");
        System.out.println("Novo nome: ");
        String novoNome = scanChoice.nextLine();

        System.out.println("Se deseja manter a mesma capacidade digite -1!!");
        System.out.println("Nova capacidade: ");
        int novaCap = scanChoice.nextInt();

        System.out.println("Se deseja manter o mesmo stock digite -1!!");
        System.out.println("Novo stock: ");
        int novoStock = scanChoice.nextInt();

        this.empresaManagement.alterarInfoArmazem(novoNome, novaCap, novoStock, nome);
    }

    public void menuAlterarMercado() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Alterar Mercado");
        System.out.println("=========================================================");

        System.out.println("Nome do mercado a alterar: ");
        String nome = scanChoice.nextLine();

        System.out.println("Se deseja manter o mesmo nome escreva-o de novo!!");
        System.out.println("Novo nome: ");
        String novoNome = scanChoice.nextLine();

        System.out.println("Se não pertender alterar clientes insira -1, se pretender alterar digite outro numero!!");
        int opcao = scanChoice.nextInt();

        if (opcao != -1) {
            System.out.println("Insira 0 quando colocar todos os clientes");
            int cliente = -1;
            QueueADT<Integer> clientes = new LinkedQueue<>();

            do {
                System.out.println("Cliente: ");
                cliente = scanChoice.nextInt();
                if (cliente <= 0) {
                    System.out.println("Cliente invalido");
                } else {
                    clientes.enqueue(cliente);
                }
            } while (cliente > 0);
            this.empresaManagement.alterarInfoMercado(novoNome, clientes, nome);
        } else {
            QueueADT<Integer> clientes = null;
            this.empresaManagement.alterarInfoMercado(novoNome, clientes, nome);
        }
    }

    public void menuRemoverLocal() throws ElementNotFoundException, NotFoundException, EmptyCollectionException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Remover Local");
        System.out.println("=========================================================");

        System.out.println("Nome: ");
        String nome = scanChoice.nextLine();

        this.empresaManagement.removerLocal(nome);
    }

    public void menuListarLocal() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Listar um Local");
        System.out.println("=========================================================");

        System.out.println("Nome do local: ");
        String nome = scanChoice.nextLine();

        this.empresaManagement.printInfoLocal(nome);
    }

    public void menuListarLocais() throws NotFoundException {

        scanChoice = new Scanner(System.in);

        System.out.flush();
        System.out.println("\n=========================================================");
        System.out.println("-> Menu Listar um Local");
        System.out.println("=========================================================");

        int opcao = 0;
        do {
            System.out.println("Tipo de local: ");
            System.out.println("1- Armazens");
            System.out.println("2- Mercados");
            opcao = scanChoice.nextInt();
            if (opcao < 1 || opcao > 2) {
                System.out.println("Opção invalida!!");
            }
        } while (opcao < 1 || opcao > 2);

        String tipo = "";

        switch (opcao) {
            case 1:
                tipo = "Armazens";
                this.empresaManagement.printInfoLocais(tipo);
                this.menuGestaoLocal();
                break;
            case 2:
                tipo = "Mercados";
                this.empresaManagement.printInfoLocais(tipo);
                this.menuGestaoLocal();
                break;
            default:
                this.menuGestaoLocal();
                break;
        }
    }
}
