package dados;
import app.ACMEJogos;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class FormularioAluguel extends JFrame {
    // Campos do formulário
    private JTextField campoTextoId, campoTextoData, campoTextoPeriodo;
    private JButton botaoEnviar, botaoCancelar, botaoLimparCamposTexto, botaoMostrarDados;
    private JTextArea areaTexto;
    private JComboBox<String> campoCliente, campoJogo;

    // Dados do sistema
    private Alugueis alugueis;
    private Clientela clientela;
    private CatalogoJogos catalogo;
    private Cliente clienteSelecionado;
    private Jogo jogoSelecionado;

    public FormularioAluguel() {
        super();
        alugueis = ACMEJogos.getAlugueisApp();
        clientela = ACMEJogos.getClientelaApp();
        catalogo = ACMEJogos.getCatalogoApp();

        // Configuração da janela
        setTitle("Cadastro de Alugueis");

            if (clientela.getClientela().isEmpty() || catalogo.getCatalogo().isEmpty()) {
                setSize(400, 100);
            } else {
                setSize(595, 650);
            }


        // Configuração do layout principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        // Configuração dos rótulos e mensagens
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        
        JPanel painelRotulo = new JPanel();
        painelRotulo.setLayout(layoutRotulo);
        painelRotulo.add(new JLabel("Por favor, digite as informações:"));

        // Painéis de mensagens para diferentes estados do sistema
        JPanel painelRotulo2 = new JPanel(layoutRotulo);
        painelRotulo2.add(new JLabel("Nenhum cliente ou jogo cadastrado"));

        JPanel painelRotulo3 = new JPanel(layoutRotulo);
        painelRotulo3.add(new JLabel("Nenhum cliente cadastrado"));

        JPanel painelRotulo4 = new JPanel(layoutRotulo);
        painelRotulo4.add(new JLabel("Nenhum jogo cadastrado"));

        JPanel painelRotulo5 = new JPanel(layoutRotulo);
        painelRotulo5.add(new JLabel("Por favor, cadastre pelo menos um cliente e um jogo."));

        // Seleção de cliente
        JPanel painelCliente = new JPanel(layoutRotulo);
        JLabel labelCliente = new JLabel("Escolha o Cliente: ");
        campoCliente = new JComboBox<>();

        for (Cliente c : clientela.getClientela().values()) {
            campoCliente.addItem(c.getNome());
        }

        campoCliente.addActionListener(e -> selecionarCliente());
        painelCliente.add(labelCliente);
        painelCliente.add(campoCliente);

        // Seleção de jogo
        JPanel painelJogo = new JPanel(layoutRotulo);
        JLabel labelJogo = new JLabel("Escolha o Jogo: ");
        campoJogo = new JComboBox<>();
        for(Jogo j : catalogo.getCatalogo().values()) {
            campoJogo.addItem(j.getNome());
        }
        campoJogo.addActionListener(e -> selecionarJogo());
        painelJogo.add(labelJogo);
        painelJogo.add(campoJogo);

        // Campos de entrada de dados
        JPanel painelCampoTexto = new JPanel(layoutRotulo);
        JLabel labelId = new JLabel("Identificador:");
        campoTextoId = new JTextField(40);
        painelCampoTexto.add(labelId);
        painelCampoTexto.add(campoTextoId);

        JPanel painelCampoTexto2 = new JPanel(layoutRotulo);
        JLabel labelData = new JLabel("Data Inicial:");
        campoTextoData = new JTextField(40);
        campoTextoData.setToolTipText("Formato: dd/MM/yyyy");
        painelCampoTexto2.add(labelData);
        painelCampoTexto2.add(campoTextoData);

        JPanel painelCampoTexto3 = new JPanel(layoutRotulo);
        JLabel labelPeriodo = new JLabel("Período (dias):");
        campoTextoPeriodo = new JTextField(40);
        campoTextoPeriodo.setToolTipText("Digite um número inteiro positivo");
        painelCampoTexto3.add(labelPeriodo);
        painelCampoTexto3.add(campoTextoPeriodo);

        // Configuração dos botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));

        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(e -> cadastrarAluguel());

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> System.exit(0));

        botaoLimparCamposTexto = new JButton("Limpar");
        botaoLimparCamposTexto.addActionListener(e -> limparCampos());

        botaoMostrarDados = new JButton("Mostrar Dados");
        botaoMostrarDados.addActionListener(e -> mostrarDadosAluguel());

        painelBotoes.add(botaoLimparCamposTexto);
        painelBotoes.add(botaoEnviar);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoMostrarDados);

        // Área de mensagens
        JPanel painelRotuloMensagens = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelRotuloMensagens.add(new JLabel("Mensagens"));

        areaTexto = new JTextArea(5,47);
        JScrollPane painelAreaTexto = new JScrollPane(areaTexto);


        // Montagem do layout baseada no estado do sistema
        if(clientela.getClientela().isEmpty() && catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo2);
            painelPrincipal.add(painelRotulo5);
        } else if(!clientela.getClientela().isEmpty() && catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo4);
            painelPrincipal.add(painelRotulo5);
        } else if(clientela.getClientela().isEmpty() && !catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo3);
            painelPrincipal.add(painelRotulo5);
        } else {
            painelPrincipal.add(painelRotulo);
            painelPrincipal.add(painelCliente);
            painelPrincipal.add(painelJogo);
            painelPrincipal.add(painelCampoTexto);
            painelPrincipal.add(painelCampoTexto2);
            painelPrincipal.add(painelCampoTexto3);
            painelPrincipal.add(painelBotoes);
            painelPrincipal.add(painelRotuloMensagens);
            painelPrincipal.add(painelAreaTexto);
        }

        this.add(painelPrincipal);
        setVisible(true);
    }

    public void selecionarCliente() {
        String nomeCliente = (String) campoCliente.getSelectedItem();
        clienteSelecionado = clientela.getClienteNome(nomeCliente);
        areaTexto.setText("Cliente selecionado:\n" + clienteSelecionado.getNome() + " - " + clienteSelecionado.getNumero());
    }

    public void selecionarJogo() {
        String nomeJogo = (String) campoJogo.getSelectedItem();
        jogoSelecionado = catalogo.getJogoNome(nomeJogo);
        areaTexto.setText("Jogo selecionado:\n" + jogoSelecionado.getNome());
    }

    public void cadastrarAluguel() {
        String nomeCliente = (String) campoCliente.getSelectedItem();
        String nomeJogo = (String) campoJogo.getSelectedItem();

        try {
            // Validação do identificador
            if (campoTextoId.getText().trim().isEmpty()) {
                areaTexto.setText("O campo \"Identificador\" está vazio.\n");
                return;
            }
            int identificador = Integer.parseInt(campoTextoId.getText());

            // Validação da data
            String dataString = campoTextoData.getText().trim();
            if (dataString.isEmpty()) {
                areaTexto.setText("O campo \"Data inicial\" está vazio.\n");
                return;
            }
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);

            // Validação do período
            if (campoTextoPeriodo.getText().trim().isEmpty()) {
                areaTexto.setText("O campo \"Período\" está vazio.\n");
                return;
            }
            int periodo = Integer.parseInt(campoTextoPeriodo.getText());
            if (periodo <= 0) {
                areaTexto.setText("O período deve ser maior que zero.\n");
                return;
            }

            // Busca e validação de cliente e jogo
            Cliente cliente = clientela.getClienteNome(nomeCliente);
            Jogo jogo = catalogo.getJogoNome(nomeJogo);
            if (cliente == null || jogo == null) {
                if (cliente == null) {
                    areaTexto.setText("Cliente inválido.\n");
                }
                if (jogo == null) {
                    areaTexto.setText("Jogo inválido.\n");
                }
                return;
            }

            // Validação da data e verificação de disponibilidade
            Date dataInicial = formato.parse(dataString);
            Date hoje = new Date();
            if (dataInicial.before(hoje)) {
                areaTexto.setText("A data inicial não pode ser anterior à data atual.\n");
                return;
            }

            // Verificação de conflitos de aluguel
            for (Aluguel aluguelExistente : alugueis.getAlugueis().values()) {
                if (aluguelExistente.getJogo().equals(jogo)) {
                    Date dataInicialExistente = aluguelExistente.getDataInicial();
                    Date dataFinalExistente = new Date(dataInicialExistente.getTime() + 
                        (long)aluguelExistente.getPeriodo() * 24 * 60 * 60 * 1000);
                    Date dataFinalNovo = new Date(dataInicial.getTime() + 
                        (long)periodo * 24 * 60 * 60 * 1000);

                    if (!(dataInicial.after(dataFinalExistente) || dataFinalNovo.before(dataInicialExistente))) {
                        areaTexto.setText("Este jogo já está alugado neste período.\n");
                        return;
                    }
                }
            }

            // Criação e registro do aluguel
            Aluguel aluguel = new Aluguel(identificador, dataInicial, periodo, cliente, jogo);
            if (alugueis.addAluguel(aluguel)) {
                limparCampos();
                areaTexto.setText("Aluguel cadastrado com sucesso.\n" + aluguel.toString());
            } else {
                areaTexto.setText("Este identificador já foi selecionado.\n");
                campoTextoId.setText("");
            }

        } catch (NumberFormatException e) {
            areaTexto.setText("O identificador e o período devem ser números inteiros.\n");
        } catch (ParseException e) {
            areaTexto.setText("Data inválida. Use o formato dd/MM/yyyy.\n");
        } catch (Exception e) {
            areaTexto.setText("Erro ao cadastrar aluguel: " + e.getMessage() + "\n");
        }
    }


    public void mostrarDadosAluguel() {
        StringBuilder dados = alugueis.mostrarDadosAluguel();
        areaTexto.setText(dados.toString());
    }

    public void limparCampos() {
        campoTextoId.setText("");
        campoTextoData.setText("");
        campoTextoPeriodo.setText("");
        areaTexto.setText("");
    }
}
