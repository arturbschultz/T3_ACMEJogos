package dados;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Aluguel {
    private int identificador;
    private Cliente cliente;
    private Jogo jogo;
    private Date dataInicial;
    private int periodo;

    public Aluguel(int identificador, Cliente cliente, Jogo jogo, Date dataInicial, int periodo) {
        this.identificador = identificador;
        this.cliente = cliente;
        this.jogo = jogo;
        this.dataInicial = dataInicial;
        this.periodo = periodo;
    }

    //getters
    public int getIdentificador() {return this.identificador;}
    public Cliente getCliente() { return cliente; }
    public Jogo getJogo() { return jogo; }

    public Date getDataInicial() {return this.dataInicial;}

    public int getPeriodo() {return this.periodo;}

    //setters
    public void setIdentificador(int identificador) {this.identificador = identificador;}

    public void setDataInicial(Date dataInicial) {this.dataInicial = dataInicial;}

    public void setPeriodo(int periodo) {this.periodo = periodo;}


    public double calculaValorFinal(){
        double valorBaseAluguel = jogo.calculaAluguel();
        if (cliente instanceof Individual) {
            if (periodo < 7) {
                return valorBaseAluguel * 0.90;
            } else if (periodo <= 14) {
                return valorBaseAluguel * 0.80;
            } else {
                return valorBaseAluguel * 0.75;
            }
        } else if (cliente instanceof Empresarial) {
            return valorBaseAluguel * 0.85;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Aluguel{" +
                "identificador=" + identificador +
                ", cliente=" + cliente.getNome() +
                ", jogo=" + jogo.getNome() +
                ", dataInicial=" + sdf.format(dataInicial) +
                ", periodo=" + periodo + " dias" +
                ", valorFinal= R$" + String.format("%.2f", calculaValorFinal()) +
                '}';
    }
}
