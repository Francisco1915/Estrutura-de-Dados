package menus;

import Exceptions.AlreadyExistException;
import Exceptions.NotFoundException;
import objetos.IMercado;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedQueue;
import Structures.Linked.LinkedUnorderedList;
import gestao.empresa.GestaoEmpresa;
import objetos.IVendedor;
import objetos.Vendedor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import objetos.Armazem;
import objetos.Empresa;
import objetos.ILocal;
import objetos.Mercado;
import gestao.empresa.IGestaoEmpresa;

public class Importer {

    IGestaoEmpresa gestaoEmpresa;
    JSONObject jsonObject;

    public Importer() throws FileNotFoundException, IOException, ParseException {

        this.gestaoEmpresa = new GestaoEmpresa();

        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("m.json");
        Object obj = jsonparser.parse(reader);
        this.jsonObject = (JSONObject) obj;
    }

    public IGestaoEmpresa importarGestaoEmpresa() throws NotFoundException {

        this.importerLocais();
        this.importerCaminhos();
        this.importerVendedores();

        return this.gestaoEmpresa;
    }

    public void importerCaminhos() {

        JSONArray arrayCaminhos = (JSONArray) this.jsonObject.get("caminhos");

        try {

            for (int i = 0; i < arrayCaminhos.toArray().length; i++) {

                JSONObject jsonCaminho = (JSONObject) arrayCaminhos.get(i);

                double distancia = this.longToDouble((long) jsonCaminho.get("distancia"));
                String de = (String) jsonCaminho.get("de");
                String para = (String) jsonCaminho.get("para");

                try {
                    this.gestaoEmpresa.adicionarCaminho(de, para, (int) distancia);
                } catch (AlreadyExistException | NotFoundException | IllegalArgumentException ex) {
                    System.out.println(ex + " De: " + de + " Para: " + para);
                }
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
    }

    public void importerLocais() throws NotFoundException {

        JSONArray arrayLocais = (JSONArray) this.jsonObject.get("locais");

        try {
            boolean empresa = false;

            for (int i = 0; i < arrayLocais.toArray().length; i++) {

                JSONObject jsonLocal = (JSONObject) arrayLocais.get(i);

                String tipo = (String) jsonLocal.get("tipo");

                ILocal local = null;
                String nome = null;

                boolean mercado = false;

                if (tipo.equals("Sede")) {
                    nome = (String) jsonLocal.get("nome");
                    empresa = true;
                    local = new Empresa(nome);
                }

                if (tipo.equals("Armazém")) {
                    nome = (String) jsonLocal.get("nome");
                    int capacidade = this.longToInt((long) jsonLocal.get("capacidade"));
                    int stock = this.longToInt((long) jsonLocal.get("stock"));
                    local = new Armazem(nome, capacidade, stock);
                }

                if (tipo.equals("Mercado")) {
                    mercado = true;
                    nome = (String) jsonLocal.get("nome");
                    LinkedQueue<Integer> clientes = new LinkedQueue<>();
                    JSONArray jsonClientes = (JSONArray) jsonLocal.get("clientes");
                    local = new Mercado(nome, clientes);
                    boolean exist = false;

                    try {
                        this.gestaoEmpresa.adicionarLocal(local);
                    } catch (AlreadyExistException | IllegalArgumentException ex) {
                        System.out.println(ex + "---Nome Mercado: " + nome + " Nº: " + (i + 1));
                        exist = true;
                    }

                    if (!exist) {
                        for (int j = 0; j < jsonClientes.toArray().length; j++) {

                            try {
                                this.gestaoEmpresa.addClienteAoMercado(nome, this.longToInt((long) jsonClientes.get(j)));
                            } catch (NotFoundException | IllegalArgumentException ex) {
                                System.out.println(ex + "---Nome Mercado: " + nome);
                                try {
                                    this.gestaoEmpresa.removerLocal(nome);
                                    break;
                                    //Ctach nunca vai acontecer porque existe sempre o mercado
                                } catch (NotFoundException | EmptyCollectionException | ElementNotFoundException ex1) {
                                    System.out.println(ex1);
                                }
                            }
                        }
                    }
                }

                if (!mercado) {
                    try {
                        this.gestaoEmpresa.adicionarLocal(local);
                    } catch (AlreadyExistException | IllegalArgumentException ex) {
                        System.out.println(ex + " Tipo : " + tipo + " Nome: " + nome + " Nº: " + (i + 1));
                    }
                }
            }
            if (!empresa) {
                throw new NotFoundException("Nao existe empresa, por favor importar json com empresa");
            }
        } catch (NullPointerException ex) {
            System.out.println("Algum parametro nos locais é null!!!1");
        }
    }

    public void importerVendedores() {

        JSONArray arrayVendedores = (JSONArray) this.jsonObject.get("vendedores");

        try {

            for (int i = 0; i < arrayVendedores.toArray().length; i++) {

                JSONObject jsonVendedor = (JSONObject) arrayVendedores.get(i);

                int id = this.longToInt((long) jsonVendedor.get("id"));
                String nome = (String) jsonVendedor.get("nome");
                int capacidade = this.longToInt((long) jsonVendedor.get("capacidade"));

                UnorderedListADT<IMercado> mercados = new LinkedUnorderedList<>();
                JSONArray arrayMercados = (JSONArray) jsonVendedor.get("mercados_a_visitar");

                IVendedor vendedor = new Vendedor(id, nome, capacidade, capacidade, mercados);
                boolean exist = false;
                try {
                    this.gestaoEmpresa.adicionarVendedor(vendedor);
                } catch (AlreadyExistException | IllegalArgumentException ex) {
                    System.out.println(ex + "---ID: " + id);
                    exist = true;
                }

                if (!exist) {
                    for (int j = 0; j < arrayMercados.toArray().length; j++) {

                        try {
                            this.gestaoEmpresa.addMercadoAoVendedor(id, (String) arrayMercados.get(j));
                        } catch (NotFoundException | AlreadyExistException | IllegalArgumentException ex) {
                            System.out.println(ex + "---ID: " + id);
                            try {
                                this.gestaoEmpresa.removerVendedor(id);
                                break;
                                //Catch nunca vai ser utilizado porque vai sempre existir o vendedor.
                            } catch (NotFoundException ex1) {
                                System.out.println(ex1);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("Algum parametro nos vendedores é invalido!!!");
        }

    }

    private double longToDouble(long number) {
        double numberDouble = (double) number;
        return numberDouble;
    }

    private int longToInt(long number) {
        int numberInt = (int) number;
        return numberInt;
    }

}
