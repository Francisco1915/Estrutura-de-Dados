package gestao.vendedores;

import objetos.IMapa;
import objetos.Mapa;
import gestao.locais.armazens.GestaoArmazem;
import gestao.locais.GestaoLocal;
import gestao.locais.mercados.GestaoMercado;
import objetos.Armazem;
import objetos.Empresa;
import objetos.ILocal;
import objetos.Mercado;
import Structures.Interfaces.UnorderedListADT;
import Structures.Linked.LinkedQueue;
import Structures.Linked.LinkedUnorderedList;
import gestao.locais.IGestaoLocal;
import gestao.locais.mercados.IGestaoMercado;
import gestao.locais.armazens.IGestaoArmazem;
import java.util.Iterator;
import objetos.IMercado;
import objetos.IVendedor;
import objetos.Vendedor;

public class Demo {

    public static void main(String[] args) {

        try {
            IMapa<ILocal> mapa = new Mapa();

            IGestaoLocal localManagement = new GestaoLocal();
            IGestaoArmazem armazemManagement = new GestaoArmazem();
            IGestaoMercado mercadoManagement = new GestaoMercado();
            
            
            IGestaoVendedores vendedorManagement = new GestaoVendedores();

            ILocal armazem = new Armazem("Armazem 1", 200, 200);
            ILocal armazem3 = new Armazem("Armazem 3", 150, 150);
            ILocal empresa = new Empresa("Empresa");

            LinkedQueue<Integer> clientes = new LinkedQueue<>();
            clientes.enqueue(10);
            clientes.enqueue(10);
            clientes.enqueue(10);

            ILocal mercado = new Mercado("Mercado 1", clientes);
            ILocal mercado3 = new Mercado("Mercado 3", clientes);

            UnorderedListADT<IMercado> mercados_visitar = new LinkedUnorderedList();

            IVendedor vendedor = new Vendedor(1, "Francisco", 200, 200, mercados_visitar);

            System.out.println(localManagement.addLocal(mapa, empresa));
            System.out.println(localManagement.addLocal(mapa, mercado));
            System.out.println(localManagement.addLocal(mapa, mercado3));
            System.out.println(localManagement.addLocal(mapa, armazem));

            System.out.println(vendedorManagement.addVendedor(vendedor));
            System.out.println(vendedorManagement.printInfoVendedor(1));

            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 1, "Mercado 3"));
            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 1, "Mercado 1"));
            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 1, ""));
            System.out.println(vendedorManagement.addMercadoAoVendedor(mapa, 4, "Mercado 1"));
            
            System.out.println(vendedorManagement.printInfoVendedor(1));

            System.out.println(vendedorManagement.alterarInfoVendedor(mapa, 1, "Luis", 20, 50, mercados_visitar));
            System.out.println(vendedorManagement.printInfoVendedor(1));
            

            System.out.println(vendedorManagement.removerMercadoAoVendedor(1, "Mercado 3", mapa));
            System.out.println(vendedorManagement.printInfoVendedor(1));

            System.out.println(vendedorManagement.removerVendedor(1));
            System.out.println(vendedorManagement.printInfoVendedor(1));
            
           // System.out.println(vendedorManagement.exportInfoVendedor(1));
            
            Iterator<IMercado> mercadoiter = vendedor.getMercadosAVisitar(mapa);
            
            System.out.println(mercadoiter.next().toString());
            System.out.println(mercadoiter.next().toString());

        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }

   }

}
