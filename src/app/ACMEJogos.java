package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

import dados.*;

public class ACMEJogos {
    // Scanners para leitura de arquivos
    private Scanner inCliente;
    private Scanner inJogo;
    private Scanner inAluguel;
    private Scanner s;  // Scanner para leitura do teclado
    private PrintStream saidaPadrao = System.out;   // Saída padrão do sistema

    // Caminhos dos arquivos de entrada e saída
    private String arquivoEntradaCliente;
    private String arquivoEntradaJogo;
    private String arquivoEntradaAluguel;
    private String nomeArquivoSaida;

    private static Clientela clientela;
    private static CatalogoJogos catalogo;
    private static Alugueis alugueis;

    public ACMEJogos() {
        s = new Scanner(System.in);
        arquivoEntradaCliente = "CLIENTESENTRADA.csv";
        arquivoEntradaJogo = "JOGOSENTRADA.csv";
        arquivoEntradaAluguel = "ALUGUEISENTRADA.csv";
        clientela = new Clientela();
        catalogo = new CatalogoJogos();
        alugueis = new Alugueis();
        initialize();
    }

    public void initialize() {
        // Carregar dados dos arquivos CSV

        
        // Carregar clientes
        clientela.carregarClientesDoCSV(arquivoEntradaCliente);
        
        // Carregar jogos
        System.out.println("Carregando jogos...");
        catalogo.carregarJogosDoCSV(arquivoEntradaJogo);
        
        // Carregar aluguéis
        System.out.println("Carregando aluguéis...");
        alugueis.carregarAlugueisDoCSV(arquivoEntradaAluguel);
        
        System.out.println("Sistema inicializado com sucesso!");
    }

    public void inicializar() {
        int opcao = -1;
        while(opcao != 0) {
            try {
                opcao = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                opcao = -1; // Opção inválida, volta ao menu
            }

            // Processamento da opção escolhida
            switch(opcao) {
                case 1:
                    new FormularioCliente();
                    break;
                case 2:
                    new FormularioJogos();
                    break;
                case 3:
                    new FormularioAluguel();
                    break;
                case 4:
                    mostrarRelatorios();
                    break;
                case 0:
                    break;
            }
        }
        // Libera recursos ao encerrar
        if (s != null) {
            s.close();
        }
    }

    private void mostrarRelatorios() {
        System.out.println("\n==== Relatórios ====");
        System.out.println("1 - Listar Clientes");
        System.out.println("2 - Listar Jogos");
        System.out.println("3 - Listar Aluguéis");
        System.out.println("0 - Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(s.nextLine());
            switch(opcao) {
                case 1:
                    System.out.println(clientela.mostrarDadosCliente());
                    break;
                case 2:
                    mostrarDadosJogos();
                    break;
                case 3:
                    mostrarDadosAlugueis();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("Opção inválida!");
        }
    }

    private void mostrarDadosJogos() {
        if (catalogo.getCatalogo().isEmpty()) {
            System.out.println("Não há jogos cadastrados.");
            return;
        }

        System.out.println("\nJogos Cadastrados:");
        for (Jogo jogo : catalogo.getCatalogo().values()) {
            System.out.println("\nCódigo: " + jogo.getCodigo());
            System.out.println("Nome: " + jogo.getNome());
            System.out.println("Valor Base: R$" + String.format("%.2f", jogo.getValorBase()));
            
            if (jogo instanceof JogoEletronico) {
                JogoEletronico jogoEletronico = (JogoEletronico) jogo;
                System.out.println("Tipo: Eletrônico");
                System.out.println("Tipo Eletrônico: " + jogoEletronico.getTipo());
                System.out.println("Plataforma: " + jogoEletronico.getPlataforma());
            } else if (jogo instanceof JogoMesa) {
                JogoMesa jogoMesa = (JogoMesa) jogo;
                System.out.println("Tipo: Mesa");
                System.out.println("Tipo Mesa: " + jogoMesa.getTipo());
                System.out.println("Número de Peças: " + jogoMesa.getNumeroPecas());
            }
            
            System.out.println("Valor do Aluguel: R$" + String.format("%.2f", jogo.calculaAluguel()));
        }
    }

    private void mostrarDadosAlugueis() {
        if (alugueis.getAlugueis().isEmpty()) {
            System.out.println("Não há aluguéis cadastrados.");
            return;
        }

        System.out.println("\nAluguéis Cadastrados:");
        for (Aluguel aluguel : alugueis.getAlugueis().values()) {
            System.out.println(aluguel.toString());
            System.out.println();
        }
    }

    public static Clientela getClientela() {
        return clientela;
    }

    public static CatalogoJogos getCatalogo() {
        return catalogo;
    }

    public static Alugueis getAlugueis() {
        return alugueis;
    }

    // Métodos para gerenciamento de arquivos
    private void redirecionaEntradaCliente(){
        if (arquivoEntradaCliente != null && !arquivoEntradaCliente.isEmpty()) {
            try {
                BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaCliente));
                inCliente = new Scanner(streamIn);
                inCliente.useLocale(Locale.ENGLISH);
            } catch(Exception e) {
                System.out.println("Erro ao abrir arquivo de clientes: " + e.getMessage());
            }
        }
    }

    private void redirecionaEntradaJogo(){
        if (arquivoEntradaJogo != null && !arquivoEntradaJogo.isEmpty()) {
            try {
                BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaJogo));
                inJogo = new Scanner(streamIn);
                inJogo.useLocale(Locale.ENGLISH);
            } catch(Exception e) {
                System.out.println("Erro ao abrir arquivo de jogos: " + e.getMessage());
            }
        }
    }

    private void redirecionaEntradaAluguel(){
        if (arquivoEntradaAluguel != null && !arquivoEntradaAluguel.isEmpty()) {
            try {
                BufferedReader streamIn = new BufferedReader(new FileReader(arquivoEntradaAluguel));
                inAluguel = new Scanner(streamIn);
                inAluguel.useLocale(Locale.ENGLISH);
            } catch(Exception e) {
                System.out.println("Erro ao abrir arquivo de aluguéis: " + e.getMessage());
            }
        }
    }

    private void redirecionaSaida(){
        if (nomeArquivoSaida != null && !nomeArquivoSaida.isEmpty()) {
            try {
                PrintStream ps = new PrintStream(nomeArquivoSaida);
                System.setOut(ps);
            } catch (Exception e) {
                System.out.println("Erro ao criar arquivo de saída: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ACMEJogos app = new ACMEJogos();
        app.inicializar();
    }
}
