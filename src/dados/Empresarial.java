package dados;

public class Empresarial extends Cliente{
    private String nomeFantasia;
    private String cnpj;

    public Empresarial(int numero, String nome, String endereco, String nomeFantasia, String cnpj) {
        super(numero, nome, endereco);
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
    }

    //getters

    public String getNomeFantasia() {return nomeFantasia;}

    public String getCnpj() {return cnpj;}

    //setters
    public void setNomeFantasia(String nomeFantasia) {this.nomeFantasia = nomeFantasia;}

    public void setCnpj(String cnpj) {this.cnpj = cnpj;}

}
