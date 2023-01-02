package objetos;

public class Empresa extends Local{
    
    /**
     * Construtor da empresa.
     * 
     * @param name nome da empresa.
     */
    public Empresa(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Empresa:" + "\nNome: " + name;
    }
}
