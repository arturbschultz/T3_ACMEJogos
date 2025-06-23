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
        double valorAluguel = getValorBase();

        // Aplica multiplicador baseado no tipo de jogo eletrônico
        switch (tipo) {
            case AVENTURA:
                valorAluguel *= 1.5;
                break;
            case ESTRATEGIA:
                valorAluguel *= 1.15;
                break;
            case SIMULACAO:
                valorAluguel *= 1.25;
                break;
        }
        return valorAluguel;
    }
}
