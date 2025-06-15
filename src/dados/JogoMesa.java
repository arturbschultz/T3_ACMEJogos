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
        return 0;
    }
}
