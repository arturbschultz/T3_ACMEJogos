package dados;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Aluguel {
    // Dados do aluguel
    private int identificador;
    private Date dataInicial;
    private int periodo;
    private Cliente cliente;
    private Jogo jogo;


    public Aluguel(int identificador, Date dataInicial, int periodo, Cliente cliente, Jogo jogo) {
        this.identificador = identificador;
        this.dataInicial = dataInicial;
        this.periodo = periodo;
        this.cliente = cliente;
        this.jogo = jogo;
    }

    // Métodos de acesso
    public int getIdentificador() {return this.identificador;}
    public Date getDataInicial() {return this.dataInicial;}
    public int getPeriodo() {return this.periodo;}
    public Cliente getCliente() {return cliente;}
    public Jogo getJogo() {return jogo;}

    // Métodos de modificação
    public void setIdentificador(int identificador) {this.identificador = identificador;}
    public void setDataInicial(Date dataInicial) {this.dataInicial = dataInicial;}
    public void setPeriodo(int periodo) {this.periodo = periodo;}

    public double calculaValorFinal() {
        double valorBaseAluguel = jogo.calculaAluguel();
        if (cliente instanceof Individual) {
            if (periodo < 7) {
                return (valorBaseAluguel * 0.90)*periodo;
            } else if (periodo <= 14) {
                return (valorBaseAluguel * 0.80)*periodo;
            } else {
                return (valorBaseAluguel * 0.75)*periodo;
            }
        } else if (cliente instanceof Empresarial) {
            if(jogo instanceof JogoEletronico){
                return valorBaseAluguel * periodo; // Sem desconto para jogos eletrônicos
            } else {
                return (valorBaseAluguel * 0.85)*periodo; // 15% de desconto para jogos de mesa
            }
        }
        return valorBaseAluguel * periodo; // Caso base (não deveria ocorrer)
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Aluguel:\n" +
                "Identificador: " + identificador + "\n"+
                "Cliente: " + cliente.getNome() +"\n"+
                "Jogo: " + jogo.getNome() +"\n"+
                "Data Inicial: " + sdf.format(dataInicial) +"\n"+
                "Periodo: " + periodo + " dias" +"\n"+
                "Valor Final: R$" + String.format("%.2f", calculaValorFinal());
    }
}
