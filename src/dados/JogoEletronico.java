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
            case ACAO:
                valorAluguel *= 2.0;
                break;
            case AVENTURA:
                valorAluguel *= 1.8;
                break;
            case ESPORTE:
                valorAluguel *= 1.5;
                break;
            case ESTRATEGIA:
                valorAluguel *= 1.7;
                break;
            case RPG:
                valorAluguel *= 1.9;
                break;
        }
        
        // Adiciona valor baseado na plataforma
        if (plataforma.equalsIgnoreCase("PC")) {
            valorAluguel += 5.0;
        } else if (plataforma.equalsIgnoreCase("PlayStation") || 
                  plataforma.equalsIgnoreCase("Xbox")) {
            valorAluguel += 7.0;
        } else if (plataforma.equalsIgnoreCase("Nintendo")) {
            valorAluguel += 6.0;
        }
        
        return valorAluguel;
    }
}
