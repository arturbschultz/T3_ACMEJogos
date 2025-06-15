package dados;

public class JogoMesa extends Jogo {
    private TipoMesa tipo;
    private int numeroPecas;

    public JogoMesa(int codigo, String nome, double valorBase, TipoMesa tipo, int numeroPecas) {
        super(codigo, nome, valorBase);
        this.tipo = tipo;
        this.numeroPecas = numeroPecas;
    }

    public TipoMesa getTipo() {
        return tipo;
    }

    public int getNumeroPecas() {
        return numeroPecas;
    }

    @Override
    public double calculaAluguel() {
        double valorAluguel = getValorBase();
        
        // Aplica multiplicador baseado no tipo de jogo de mesa
        switch (tipo) {
            case TABULEIRO:
                valorAluguel *= 1.5;
                break;
            case CARTAS:
                valorAluguel *= 1.2;
                break;
            case DADOS:
                valorAluguel *= 1.3;
                break;
            case DOMINO:
                valorAluguel *= 1.1;
                break;
        }
        
        // Adiciona valor baseado no número de peças
        valorAluguel += (numeroPecas * 0.1);
        
        return valorAluguel;
    }
}
