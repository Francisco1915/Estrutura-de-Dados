package gestao.caminhos;

import objetos.IMapa;
import objetos.Mapa;
import objetos.Armazem;
import objetos.Empresa;
import objetos.ILocal;
import objetos.IMercado;
import objetos.Mercado;
import Structures.Exceptions.EmptyCollectionException;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedQueue;
import Structures.Linked.LinkedUnorderedList;
import objetos.IVendedor;
import objetos.Vendedor;
import gestao.vendedores.GestaoVendedores;
import gestao.vendedores.IGestaoVendedores;
import java.util.Iterator;

/**
 *
 * @author Luis
 */
public class DemoRotas {

    public static void main(String[] args) {

//        IMapa<ILocal> mapa = new Mapa();
//
//        IGestaoLocal localManagement = new GestaoLocal();
//        IGestaoArmazem armazemManagement = new GestaoArmazem();
//        IGestaoMercado mercadoManagement = new GestaoMercado();
//        GestaoCaminhos caminhosManagement = new GestaoCaminhos();
//        IGestaoVendedores vendedorManagement = new GestaoVendedores();
//
//        ILocal armazem = new Armazem("Armazem 1", 200, 120);
//        ILocal armazem2 = new Armazem("Armazem 2", 150, 90);
//        ILocal empresa = new Empresa("Empresa");
//
//        LinkedQueue<Integer> clientes = new LinkedQueue<>();
//        clientes.enqueue(50);
//        clientes.enqueue(100);
//        clientes.enqueue(150);
//
//        ILocal mercado = new Mercado("Mercado 1", clientes);
//
//        UnorderedListADT<IMercado> mercados_visitar = new LinkedUnorderedList();
//
//        IVendedor vendedor = new Vendedor(1, "Francisco", 200, mercados_visitar);
//
//        try {
//
//            System.out.println(localManagement.addLocal(mapa, empresa));
//            System.out.println(localManagement.addLocal(mapa, mercado));
//            System.out.println(localManagement.addLocal(mapa, armazem));
////            System.out.println(localManagement.addLocal(mapa, armazem2));
//
//            System.out.println(caminhosManagement.addCaminho(mapa, "Empresa", "Mercado 1", 10));
////            System.out.println(caminhosManagement.addCaminho(mapa, "Empresa", "Armazem 1", 5));
////            System.out.println(caminhosManagement.addCaminho(mapa, "Mercado 1", "Armazem 1", 4));
////            System.out.println(caminhosManagement.addCaminho(mapa, "Mercado 1", "Armazem 2", 2));
////
////            System.out.println(caminhosManagement.printInfoCaminhos(mapa));
////
//            System.out.println(vendedorManagement.addVendedor(vendedor));
//            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 1, "Mercado 1"));
////
//            System.out.println(vendedorManagement.printInfoVendedor(1));
////
////            vendedor.removeStock(100);
////
////            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 1, "Mercado 1"));
////
////            System.out.println(vendedorManagement.printInfoVendedor(1));
////
////            System.out.println(caminhosManagement.encontrarVisitarArmazem(mercado, vendedor, mapa));
//
//            Iterator<ILocal> rota = caminhosManagement.criarRotaVendedor(empresa, vendedor, mapa);
//
//            while (rota.hasNext()) {
//              System.out.println(rota.next().getName());
//            }
//            
//            System.out.println(vendedorManagement.printInfoVendedor(1));
//        } catch (IllegalArgumentException | EmptyCollectionException ex) {
//            System.out.println(ex);
//        }
    }

}
