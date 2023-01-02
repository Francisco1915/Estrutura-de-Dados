package gestao.locais;

import gestao.locais.armazens.GestaoArmazem;
import gestao.locais.armazens.IGestaoArmazem;
import gestao.locais.mercados.GestaoMercado;
import gestao.locais.mercados.IGestaoMercado;
import gestao.locais.GestaoLocal;
import gestao.locais.IGestaoLocal;
import gestao.caminhos.IGestaoCaminhos;
import gestao.caminhos.GestaoCaminhos;
import objetos.IMapa;
import objetos.Mapa;
import objetos.Armazem;
import objetos.Empresa;
import objetos.ILocal;
import objetos.Mercado;
import Structures.Linked.LinkedQueue;
import gestao.vendedores.GestaoVendedores;
import gestao.vendedores.IGestaoVendedores;

public class Demo {

    public static void main(String[] args) {

        IMapa<ILocal> mapa = new Mapa();

        IGestaoLocal localManagement = new GestaoLocal();
        IGestaoArmazem armazemManagement = new GestaoArmazem();
        IGestaoMercado mercadoManagement = new GestaoMercado();
        IGestaoCaminhos caminhosManagement = new GestaoCaminhos();
        IGestaoVendedores gestaoVendedores = new GestaoVendedores();

        ILocal armazem = new Armazem("Armazem 1", 200, 200);
        ILocal armazem3 = new Armazem("Armazem 3", 150, 150);
        ILocal empresa = new Empresa("Empresa");

        LinkedQueue<Integer> clientes = new LinkedQueue<>();
        clientes.enqueue(-5);
        clientes.enqueue(10);

        LinkedQueue<Integer> clientes1 = new LinkedQueue<>();
        clientes1.enqueue(10);
        clientes1.enqueue(10);
        clientes1.enqueue(10);

        LinkedQueue<Integer> clientes2 = new LinkedQueue<>();
        clientes2.enqueue(15);
        clientes2.enqueue(10);
        clientes2.enqueue(10);
        clientes2.enqueue(10);
        
        ILocal mercado = new Mercado("Mercado 1", clientes);
        ILocal mercado3 = new Mercado("Mercado 3", clientes1);

        try {

            System.out.println(localManagement.addLocal(mapa, empresa));
            System.out.println(localManagement.addLocal(mapa, mercado3));
            System.out.println(localManagement.addLocal(mapa, armazem));
            
            System.out.println(caminhosManagement.addCaminho(mapa, "Empresa", "Mercado 2", 10));
            System.out.println(caminhosManagement.addCaminho(mapa, "Empresa", "Armazem 2", 5));
            System.out.println(caminhosManagement.addCaminho(mapa, "Armazem 2", "Mercado 2", 15));
            
            //gestaoVendedores.addVendedor(vendedor);

            
            caminhosManagement.printInfoCaminhos(mapa);
            
            System.out.println(localManagement.ExportLocal(mapa, "Mercado 2"));
           
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }
}
