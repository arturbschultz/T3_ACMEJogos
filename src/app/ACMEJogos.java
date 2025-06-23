package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

public class ACMEJogos {
    //jogosentrada.csv é o arquivo de entrada.
    private Scanner inCliente;
    private Scanner inJogo;
    private Scanner inAluguel;
    private Scanner s = new Scanner(System.in);  // Atributo para entrada padrao (teclado)
    private PrintStream saidaPadrao = System.out;   // Guarda a saida padrao - tela (console)
    private String arquivoEntradaCliente;  // Nome do arquivo de entrada de dados
    private String arquivoEntradaJogo;
    private String arquivoEntradaAluguel;
    private String nomeArquivoSaida;  // Nome do arquivo de saida de dados

    public ACMEJogos() {
        redirecionaEntradaCliente();
        redirecionaEntradaJogo();
        redirecionaEntradaAluguel();
        redirecionaSaida();
    }

    public void inicializar(){
    }

    public void redirecionaEntradaCliente(){
        try{
            BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaCliente));
            inCliente = new Scanner(streamIn);
        }catch(Exception e){
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);
        inCliente.useLocale(Locale.ENGLISH);
    }

    public void redirecionaEntradaJogo(){
        try{
            BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaJogo));
            inJogo = new Scanner(streamIn);
        }catch(Exception e){
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);
        inJogo.useLocale(Locale.ENGLISH);
    }

    public void redirecionaEntradaAluguel(){
        try{
            BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaAluguel));
            inAluguel = new Scanner(streamIn);
        }catch(Exception e){
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);
        inAluguel.useLocale(Locale.ENGLISH);
    }

    public void redirecionaSaida(){}
}
