package dados;

public class Individual extends Cliente{
    private String cpf;

    public Individual(int numero, String nome, String endereco, String cpf) {
        super(numero, nome, endereco);
        this.cpf = cpf;
    }

    //getters
    public String getCpf() {return cpf;}

    //setters
    public void setCpf(String cpf) {this.cpf = cpf;}
}
