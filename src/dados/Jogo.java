package dados;

public abstract class Jogo {
    private int codigo;
    private String nome;
    private double valorBase;

    // Construtor para inicializar os atributos básicos
    public Jogo(int codigo, String nome, double valorBase) {
        this.codigo = codigo;
        this.nome = nome;
        this.valorBase = valorBase;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getValorBase() {
        return valorBase;
    }

    public abstract double calculaAluguel();
}
