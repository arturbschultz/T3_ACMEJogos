package dados;

public class JogoEletronico extends Jogo {
    private TipoEletronico tipo;
    private String plataforma;

    public JogoEletronico(int codigo, String nome, double valorBase, TipoEletronico tipo, String plataforma) {
        super(codigo, nome, valorBase);
        this.tipo = tipo;
        this.plataforma = plataforma;
    }

    public TipoEletronico getTipo() {
        return tipo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    @Override
    public double calculaAluguel() {
        return 0;
    }
}
