package gestao.locais.armazens;

import gestao.locais.GestaoLocal;
import objetos.IMapa;
import objetos.Armazem;
import objetos.IArmazem;
import objetos.ILocal;
import Structures.Interfaces.OrderedListADT;
import Structures.Linked.LinkedOrderedList;
import java.util.Iterator;
import org.json.simple.JSONObject;

public class GestaoArmazem extends GestaoLocal implements IGestaoArmazem {
    
     /**
     * Construtor da gestão de armazens.
     */
    public GestaoArmazem() {     
    }

    @Override
    public boolean validarInfoArmazem(IArmazem armazem) {

        return !(armazem.getName() == "" || armazem.getName() == null || armazem.getCapacidade() <= 0 || armazem.getStock() < 0 ||
                armazem.getStock() > armazem.getCapacidade());
    }

    @Override
    public boolean printInfoArmazens(IMapa<ILocal> mapa) {

        if (mapa == null) {
            throw new IllegalArgumentException("O parametro é null");
        }

        Iterator<ILocal> iter = mapa.getVertices();
        OrderedListADT<IArmazem> armazens = new LinkedOrderedList<>();
        int count = 0;
        while (iter.hasNext()) {

            ILocal current = iter.next();
            if (current instanceof Armazem) {
                count++;
                armazens.add((IArmazem) current);
            }
        }
        Iterator<IArmazem> armazensIter = armazens.iterator();
        System.out.println("------------Armazens------------");
        while (armazensIter.hasNext()) {

            ILocal current = armazensIter.next();
            System.out.println(current.toString());
            System.out.println("--------------------------------");
        }
        System.out.println("--------------------------------");
        return count != 0;
    }

    @Override
    public int alterarInfoArmazen(IMapa<ILocal> mapa, String novoNome, int novaCapacidade, int novoStock, String nomeLocal) {

        if (nomeLocal == null || mapa == null || novaCapacidade < -1 || novoStock < -1) {
            throw new IllegalArgumentException("Um dos parametros é invalido");
        }

        ILocal local = super.procurarLocal(mapa, nomeLocal);

        if (local == null || !(local instanceof Armazem)) {
            return 1;
        }

        IArmazem armazem = (IArmazem) local;
        IArmazem temp = new Armazem(armazem.getName(), armazem.getCapacidade(), armazem.getStock());

        if (novoNome != "" && this.procurarLocal(mapa, novoNome) == null) {
            armazem.setName(novoNome);
        }

        if (novaCapacidade != -1) {
            armazem.setCapacidade(novaCapacidade);
        }

        if (novoStock != -1 && armazem.getCapacidade() >= novoStock) {
            armazem.setStock(novoStock);
        }

        if (!this.validarInfoArmazem(armazem)) {
            armazem.setName(temp.getName());
            armazem.setCapacidade(temp.getCapacidade());
            armazem.setStock(temp.getStock());
            return 2;
        }
        return 0;
    }

    @Override
    public JSONObject exportInfoArmazemToJSON(IMapa<ILocal> mapa, String nomeLocal) {
        
        if (mapa == null) {
            throw new IllegalArgumentException("Parametro invalido");
        }
        
        ILocal local = super.procurarLocal(mapa, nomeLocal);
        
        if (local == null || !(local instanceof Armazem)) {
            return null;
        }
        
        IArmazem armazem = (IArmazem) local;      
        JSONObject jsonArmazem = super.ExportLocal(mapa, nomeLocal);
        jsonArmazem.put("capacidade", armazem.getCapacidade());
        jsonArmazem.put("stock", armazem.getStock());       
        return jsonArmazem;
    }
}
