package dados;

public class Cliente {
    private int numero;
    private String nome;
    private String endereco;

    public Cliente(int numero, String nome, String endereco) {
        this.numero = numero;
        this.nome = nome;
        this.endereco = endereco;
    }

    //getters
    public int getNumero() {return numero;}

    public String getNome() {return nome;}

    public String getEndereco() {return endereco;}

    //setters

    public void setNumero(int numero) {this.numero = numero;}

    public void setNome(String nome) {this.nome = nome;}

    public void setEndereco(String endereco) {this.endereco = endereco;}

}
