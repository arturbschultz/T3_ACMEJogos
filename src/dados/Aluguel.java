package dados;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe que representa um aluguel de jogo.
 * Mantém as informações do aluguel e implementa o cálculo do valor final
 * baseado no tipo de cliente e jogo.
 */
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

    /**
     * Calcula o valor final do aluguel baseado no tipo de cliente e jogo.
     * Para clientes individuais:
     * - Período < 7 dias: 10% de desconto
     * - Período entre 7 e 14 dias: 20% de desconto
     * - Período > 14 dias: 25% de desconto
     * 
     * Para clientes empresariais:
     * - Jogos eletrônicos: sem desconto
     * - Jogos de mesa: 15% de desconto
     * 
     * @return valor final do aluguel considerando o período e descontos aplicáveis
     */
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
