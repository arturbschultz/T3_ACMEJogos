package dados;
import java.util.Date;

public class Aluguel {
    private int identificador;
    private Date dataInicial;
    private int periodo;

    public Aluguel(int identificador, Date dataInicial, int periodo) {
        this.identificador = identificador;
        this.dataInicial = dataInicial;
        this.periodo = periodo;
    }

    //getters
    public int getIdentificador() {return this.identificador;}

    public Date getDataInicial() {return this.dataInicial;}

    public int getPeriodo() {return this.periodo;}

    //setters
    public void setIdentificador(int identificador) {this.identificador = identificador;}

    public void setDataInicial(Date dataInicial) {this.dataInicial = dataInicial;}

    public void setPeriodo(int periodo) {this.periodo = periodo;}


    public double calculaValorFinal(){
        return 0;
    }
}
