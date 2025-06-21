package dados;
import java.util.TreeMap;

public class Alugueis {
    private TreeMap<Integer, Aluguel> alugueis;

    public Alugueis() {alugueis = new TreeMap<>();}

    public TreeMap<Integer, Aluguel> getAlugueis() {return alugueis;}

    public boolean addAluguel(Aluguel aluguel) {
        if(alugueis.containsKey(aluguel.getIdentificador())){
            return false;
        }
        alugueis.put(aluguel.getIdentificador(), aluguel);
        return true;
    }
}
