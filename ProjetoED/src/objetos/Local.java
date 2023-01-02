package objetos;

import java.util.Objects;

public abstract class Local implements ILocal{
    
    String name;
    
     /**
     * Retorna o nome da empresa.
     * 
     * @return o nome da empresa.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Aletra o nome da empresa.
     * 
     * @param name novo nome da empresa.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Local other = (Local) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    } 
}
