package gestao.locais;

import objetos.ILocal;
import objetos.IMapa;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Exceptions.EmptyCollectionException;
import java.util.Iterator;
import org.json.simple.JSONObject;


public class GestaoLocal implements IGestaoLocal{

    /**
     * Construtor da gestão de locais.
     */
    public GestaoLocal() {
    }

    @Override
    public boolean addLocal(IMapa<ILocal> mapa, ILocal local){
        
        if (mapa == null || local == null) {
            throw new IllegalArgumentException("Um ou ambos os parametros são invalios");
        }
        
        if (this.procurarLocal(mapa, local.getName()) != null) {
            return false;
        }
        
        mapa.addVertex(local);
        return true;
    }

    @Override
    public boolean removerLocal(IMapa<ILocal> mapa, String nomeLocal) {
        
        if (mapa == null ) {
            throw new IllegalArgumentException("Parametros invalido");
        }
        
        try {
            mapa.removeVertex(this.procurarLocal(mapa, nomeLocal));
        } catch (EmptyCollectionException | ElementNotFoundException ex) {
            return false;
        }
        
        return true;
    }

    @Override
    public JSONObject ExportLocal(IMapa<ILocal> mapa, String nomeLocal) {
        
        if (mapa == null) {
            throw new IllegalArgumentException("Parametro é invalido");
        }
        
        ILocal local = this.procurarLocal(mapa, nomeLocal);

        if (local == null) {
            return null;
        }
        
        JSONObject jsonLocal = new JSONObject();
        jsonLocal.put("nome", local.getName());
        return jsonLocal;
    } 

    @Override
    public boolean printInfoLocal(IMapa<ILocal> mapa, String nomeLocal) {
        
        if (mapa == null) {
            throw new IllegalArgumentException("Parametro é invalido");
        }
        
        ILocal local = this.procurarLocal(mapa, nomeLocal);
        
        if (local == null) {
            return false;
        }
        System.out.println("---------Local----------");
        System.out.println(local.toString());
        System.out.println("------------------------");
        return true;
    }
    
    /**
     * Procurar um local
     * 
     * @param mapa mapa da emrpesa
     * @param nomeLocal nome do local a procurar
     * @return O local procurado, null se não existir esse local.
     */
    protected ILocal procurarLocal(IMapa<ILocal> mapa, String nomeLocal) {

        Iterator<ILocal> iter = mapa.getVertices();
       
        while(iter.hasNext()) {
            
            ILocal current = iter.next();
            if (current.getName().equals(nomeLocal)) {
                return current;
            }
        }          
        return null;
    }
    
}
