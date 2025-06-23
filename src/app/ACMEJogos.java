package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dados.*;

public class ACMEJogos extends JFrame {
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

    private JButton bCadastrarCliente, bCadastrarJogo, bCadastrarAluguel, bRelatorioCliente, bRelatorioJogo, bRelatorioAluguel, bFinalizar, bRemoverDadosAluguel, bAlterarDadosCliente;
    private JTextArea areaTexto;

    public ACMEJogos() {
        s = new Scanner(System.in);
        arquivoEntradaCliente = "CLIENTESENTRADA.csv";
        arquivoEntradaJogo = "JOGOSENTRADA.csv";
        arquivoEntradaAluguel = "ALUGUEISENTRADA.csv";
        clientela = new Clientela();
        catalogo = new CatalogoJogos();
        alugueis = new Alugueis();
        inicializar();
    }

    public void inicializar() {
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

    public void executar() {
        setTitle("ACME Kleines Spieleunternehmen in Süddeutschland");
        setSize(800, 600);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        JPanel painelRotulo = new JPanel();
        painelRotulo.setLayout(layoutRotulo);
        painelRotulo.add(new JLabel("Por favor, selecione uma opção:"));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

        bCadastrarCliente = new JButton("Cadastrar Cliente");
        bCadastrarCliente.addActionListener(e -> new FormularioCliente());

        bCadastrarJogo = new JButton("Cadastrar Jogo");
        bCadastrarJogo.addActionListener(e -> new FormularioJogos());

        bCadastrarAluguel = new JButton("Cadastrar Aluguel");
        bCadastrarAluguel.addActionListener(e -> new FormularioAluguel());

        bRelatorioCliente = new JButton("Relatorio de Clientes");
        bRelatorioCliente.addActionListener(e -> mostrarDadosClientes());

        bRelatorioJogo = new JButton("Relatorio de Jogos");
        bRelatorioJogo.addActionListener(e -> mostrarDadosJogos());

        bRelatorioAluguel = new JButton("Relatorio de Alugueis");
        bRelatorioAluguel.addActionListener(e -> mostrarDadosAluguel());

        bRemoverDadosAluguel = new JButton("Remover Dados de um Aluguel");
        bRemoverDadosAluguel.addActionListener(e -> removerDadosAluguel());

        bAlterarDadosCliente = new JButton("Alterar Dados de um Cliente");
        bAlterarDadosCliente.addActionListener(e -> alterarDadosCliente());

        bFinalizar = new JButton("Finalizar");
        bFinalizar.addActionListener(e -> System.exit(0));

        painelBotoes.add(bCadastrarCliente);
        painelBotoes.add(bCadastrarJogo);
        painelBotoes.add(bCadastrarAluguel);
        painelBotoes.add(bRelatorioCliente);
        painelBotoes.add(bRelatorioJogo);
        painelBotoes.add(bRelatorioAluguel);
        painelBotoes.add(bRemoverDadosAluguel);
        painelBotoes.add(bAlterarDadosCliente);
        painelBotoes.add(bFinalizar);

        JPanel painelRotuloMensagens = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelRotuloMensagens.add(new JLabel("Mensagens"));

        areaTexto = new JTextArea(5,47);
        JScrollPane painelAreaTexto = new JScrollPane(areaTexto);

        painelPrincipal.add(painelRotulo);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelRotuloMensagens);
        painelPrincipal.add(painelAreaTexto);

        this.add(painelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void mostrarDadosClientes(){
        StringBuilder dados = clientela.mostrarDadosCliente();
        areaTexto.setText(dados.toString());
    }

    private void mostrarDadosJogos() {
        StringBuilder dados = catalogo.mostrarDadosJogos();
        areaTexto.setText(dados.toString());
    }

    public void mostrarDadosAluguel() {
        StringBuilder dados = alugueis.mostrarDadosAluguel();
        areaTexto.setText(dados.toString());
    }

    private void removerDadosAluguel() {
        String identificador = JOptionPane.showInputDialog(this, "Digite o identificador do aluguel a remover:");
        if (identificador == null) return; // Usuário cancelou
        try {
            int id = Integer.parseInt(identificador.trim());
            boolean removido = alugueis.removerAluguel(id);
            if (removido) {
                areaTexto.setText("Aluguel removido com sucesso. (ID: " + id + ")");
            } else {
                areaTexto.setText("Erro: Não existe aluguel com o identificador informado (ID: " + id + ")");
            }
        } catch (NumberFormatException ex) {
            areaTexto.setText("Identificador inválido. Digite um número inteiro.");
        }
    }

    private void alterarDadosCliente() {
        String num = JOptionPane.showInputDialog(this, "Digite o número do cliente a alterar:");
        if (num == null) return; // Usuário cancelou
        try {
            int numero = Integer.parseInt(num.trim());
            Cliente cliente = clientela.getClientela().get(numero);
            if (cliente == null) {
                areaTexto.setText("Erro: Não existe cliente com o número informado (" + numero + ")");
                return;
            }
            // Monta mensagem com dados atuais
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("Dados atuais:\n");
            mensagem.append("Nome: ").append(cliente.getNome()).append("\n");
            mensagem.append("Endereço: ").append(cliente.getEndereco()).append("\n");
            if (cliente instanceof Individual) {
                mensagem.append("CPF: ").append(((Individual)cliente).getCpf()).append("\n");
            } else if (cliente instanceof Empresarial) {
                mensagem.append("Nome Fantasia: ").append(((Empresarial)cliente).getNomeFantasia()).append("\n");
                mensagem.append("CNPJ: ").append(((Empresarial)cliente).getCnpj()).append("\n");
            }
            areaTexto.setText(mensagem.toString());

            // Solicita novos dados
            String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", cliente.getNome());
            if (novoNome == null){
                return;
            }
            String novoEndereco = JOptionPane.showInputDialog(this, "Novo endereço:", cliente.getEndereco());
            if (novoEndereco == null){
                return;
            }
            if (cliente instanceof Individual) {
                String novoCpf = JOptionPane.showInputDialog(this, "Novo CPF:", ((Individual)cliente).getCpf());
                if (novoCpf == null){
                    return;
                }
                cliente.setNome(novoNome);
                cliente.setEndereco(novoEndereco);
                ((Individual)cliente).setCpf(novoCpf);
            } else if (cliente instanceof Empresarial) {
                String novoNomeFantasia = JOptionPane.showInputDialog(this, "Novo nome fantasia:", ((Empresarial)cliente).getNomeFantasia());
                if (novoNomeFantasia == null){
                    return;
                }
                String novoCnpj = JOptionPane.showInputDialog(this, "Novo CNPJ:", ((Empresarial)cliente).getCnpj());
                if (novoCnpj == null){
                    return;
                }
                cliente.setNome(novoNome);
                cliente.setEndereco(novoEndereco);
                ((Empresarial)cliente).setNomeFantasia(novoNomeFantasia);
                ((Empresarial)cliente).setCnpj(novoCnpj);
            }
            areaTexto.setText("Dados do cliente atualizados com sucesso!\n" +
                "Nome: " + cliente.getNome() + "\n" +
                "Endereço: " + cliente.getEndereco() + "\n" +
                (cliente instanceof Individual ? ("CPF: " + ((Individual)cliente).getCpf()) : ("Nome Fantasia: " + ((Empresarial)cliente).getNomeFantasia() + "\nCNPJ: " + ((Empresarial)cliente).getCnpj()))
            );
        } catch (NumberFormatException ex) {
            areaTexto.setText("Número inválido. Digite um número inteiro.");
        }
    }

    /**
     * public void executar() {
     *         int opcao = -1;
     *         while(opcao != 0) {
     *             try {
     *                 opcao = Integer.parseInt(s.nextLine());
     *             } catch (Exception e) {
     *                 opcao = -1; // Opção inválida, volta ao menu
     *             }
     *
     *             // Processamento da opção escolhida
     *             switch(opcao) {
     *                 case 1:
     *                     new FormularioCliente();
     *                     break;
     *                 case 2:
     *                     new FormularioJogos();
     *                     break;
     *                 case 3:
     *                     new FormularioAluguel();
     *                     break;
     *                 case 4:
     *                     mostrarRelatorios();
     *                     break;
     *                 case 0:
     *                     break;
     *             }
     *         }
     *         // Libera recursos ao encerrar
     *         if (s != null) {
     *             s.close();
     *         }
     *     }
     *
     *     private void mostrarRelatorios() {
     *         System.out.println("\n==== Relatórios ====");
     *         System.out.println("1 - Listar Clientes");
     *         System.out.println("2 - Listar Jogos");
     *         System.out.println("3 - Listar Aluguéis");
     *         System.out.println("0 - Voltar");
     *         System.out.print("Escolha uma opção: ");
     *
     *         try {
     *             int opcao = Integer.parseInt(s.nextLine());
     *             switch(opcao) {
     *                 case 1:
     *                     System.out.println(clientela.mostrarDadosCliente());
     *                     break;
     *                 case 2:
     *                     System.out.println(catalogo.mostrarDadosJogos());
     *                     break;
     *                 case 3:
     *                     System.out.println(alugueis.mostrarDadosAluguel());
     *                     break;
     *                 case 0:
     *                     return;
     *                 default:
     *                     System.out.println("Opção inválida!");
     *             }
     *         } catch (Exception e) {
     *             System.out.println("Opção inválida!");
     *         }
     *     }
     *
     *     private void mostrarDadosAlugueis() {
     *         if (alugueis.getAlugueis().isEmpty()) {
     *             System.out.println("Não há aluguéis cadastrados.");
     *             return;
     *         }
     *
     *         System.out.println("\nAluguéis Cadastrados:");
     *         for (Aluguel aluguel : alugueis.getAlugueis().values()) {
     *             System.out.println(aluguel.toString());
     *             System.out.println();
     *         }
     *     }
     *
     */

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
    public static Clientela getClientelaApp(){return clientela;}
    public static CatalogoJogos getCatalogoApp(){return catalogo;}
    public static Alugueis getAlugueisApp(){return alugueis;}
}
