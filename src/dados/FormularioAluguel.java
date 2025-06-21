package dados;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class FormularioAluguel extends JFrame {
    private JTextField campoTextoId, campoTextoData, campoTextoPeriodo;
    private JButton botaoEnviar, botaoCancelar, botaoLimparCamposTexto, botaoMostrarDados;
    private JTextArea areaTexto;
    private JComboBox<String> campoCliente, campoJogo;
    private Alugueis alugueis;
    private Clientela clientela;
    private CatalogoJogos catalogo;

    public FormularioAluguel() {

        /**
         * escolher cliente
         * areatextoCliente
         *
         *
         * escolher jogo
         * areatextoJogo
         *
         */
        super();
        alugueis = new Alugueis();
        clientela = new Clientela();
        catalogo = new CatalogoJogos();
        setTitle("Cadastro de Alugueis");
        setSize(590,900);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações:");
        painelRotulo.add(rotulo);

        //escolher o cliente
        JPanel painelTipoCliente = new JPanel();
        painelTipoCliente.setLayout(layoutRotulo);
        JLabel labelCliente = new JLabel("Escolha o Cliente: ");
        campoCliente = new JComboBox<>();
        for(Cliente c : clientela.getClientela().values()){
            campoCliente.addItem(c.getNome());
        }
        campoCliente.addActionListener(e -> selecionarCliente());
        painelTipoCliente.add(labelCliente);
        painelTipoCliente.add(campoCliente);

        //escolher o jogo
        JPanel painelJogo = new JPanel();
        painelJogo.setLayout(layoutRotulo);
        JLabel labelJogo = new JLabel("Escolha o Jogo: ");
        campoJogo = new JComboBox<>();
        for(Jogo j : catalogo.getCatalogo().values()){
            campoJogo.addItem(j.getNome());
        }
        campoJogo.addActionListener(e -> selecionarJogo());
        painelTipoCliente.add(labelJogo);
        painelTipoCliente.add(campoJogo);

        //
        JPanel painelCampoTexto = new JPanel();
        painelCampoTexto.setLayout(layoutRotulo);
        JLabel labelId = new JLabel("Identificador:");
        campoTextoId = new JTextField(40);
        painelCampoTexto.add(labelId);
        painelCampoTexto.add(campoTextoId);

        JPanel painelCampoTexto2 = new JPanel();
        painelCampoTexto.setLayout(layoutRotulo);
        JLabel labelData = new JLabel("Data Inicial:");
        campoTextoData = new JTextField(40);
        painelCampoTexto.add(labelData);
        painelCampoTexto.add(campoTextoData);

        JPanel painelCampoTexto3 = new JPanel();
        painelCampoTexto.setLayout(layoutRotulo);
        JLabel labelPeriodo = new JLabel("Data Inicial:");
        campoTextoPeriodo = new JTextField(40);
        painelCampoTexto.add(labelPeriodo);
        painelCampoTexto.add(campoTextoPeriodo);

        // Painel intermediario para os botoes
        JPanel painelBotoes = new JPanel();
        FlowLayout layoutBotoes = new FlowLayout(FlowLayout.RIGHT);
        painelBotoes.setLayout(layoutBotoes);

        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(e -> {
            cadastrarAluguel();
        });

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> {
            System.exit(0);        //por default 0 = sair.
        });
        botaoLimparCamposTexto = new JButton("Limpar");
        botaoLimparCamposTexto.addActionListener(e -> {
            limparCampos();
        });

        botaoMostrarDados = new JButton("Mostrar Dados");
        botaoMostrarDados.addActionListener(e -> {
            mostrarDadosAluguel();
        });


        painelBotoes.add(botaoLimparCamposTexto);
        painelBotoes.add(botaoEnviar);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoMostrarDados);

        JPanel painelRotuloMensagens = new JPanel();
        FlowLayout layoutMensagens = new FlowLayout(FlowLayout.LEFT);
        painelRotuloMensagens.setLayout(layoutMensagens);
        JLabel labelMensagens = new JLabel("Mensagens");
        painelRotuloMensagens.add(labelMensagens);

        // Painel intermediario para a area de texto (com scroll)
        areaTexto = new JTextArea(5,47);
        JScrollPane painelAreaTexto = new JScrollPane(areaTexto);

        painelPrincipal.add(painelRotulo);
        painelPrincipal.add(painelTipoCliente);
        painelPrincipal.add(painelCampoTexto);
        painelPrincipal.add(painelCampoTexto2);
        painelPrincipal.add(painelCampoTexto3);

        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelRotuloMensagens);
        painelPrincipal.add(painelAreaTexto);

        this.add(painelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * private void mostrarDadosClientes(){
     *         TreeMap<Integer, Cliente> clientes = clientela.getClientela();
     *         StringBuilder dados = new StringBuilder("Clientes ja cadastrados:\n");
     *
     *         for(Map.Entry<Integer, Cliente> clientela: clientes.entrySet()){
     *             Cliente cliente = clientela.getValue();
     *             dados.append(clientela.getKey()).append(" - ").append(cliente.getNome()).append(" - ").append(cliente.getEndereco()).append("\n");
     *         }
     *         areaTexto.setText(dados.toString());
     *     }
     */


    /**
     *private void mostrarDadosJogos() {
     *         TreeMap<Integer, Jogo> jogos = catalogo.getCatalogo();
     *         if (jogos.isEmpty()) {
     *             areaTexto.setText("Não há jogos cadastrados.\n");
     *             return;
     *         }
     *
     *         StringBuilder dados = new StringBuilder("Jogos Cadastrados:\n\n");
     *         for (Jogo jogo : jogos.values()) {
     *             dados.append("Código: ").append(jogo.getCodigo())
     *                     .append("\nNome: ").append(jogo.getNome())
     *                     .append("\nValor Base: R$").append(String.format("%.2f", jogo.getValorBase()));
     *
     *             if (jogo instanceof JogoEletronico) {
     *                 JogoEletronico jogoEletronico = (JogoEletronico) jogo;
     *                 dados.append("\nTipo: Eletrônico")
     *                         .append("\nTipo Eletrônico: ").append(jogoEletronico.getTipo())
     *                         .append("\nPlataforma: ").append(jogoEletronico.getPlataforma());
     *             } else if (jogo instanceof JogoMesa) {
     *                 JogoMesa jogoMesa = (JogoMesa) jogo;
     *                 dados.append("\nTipo: Mesa")
     *                         .append("\nTipo Mesa: ").append(jogoMesa.getTipo())
     *                         .append("\nNúmero de Peças: ").append(jogoMesa.getNumeroPecas());
     *             }
     *
     *             dados.append("\nValor do Aluguel: R$").append(String.format("%.2f", jogo.calculaAluguel()))
     *                     .append("\n\n");
     *         }
     *
     *         areaTexto.setText(dados.toString());
     *     }
     */

    public void selecionarCliente(){
        //percorre a treemap de clientes e acha o cliente com o nome
    }

    public void selecionarJogo(){
        //percorre a treemap de jogos e acha o jogo pelo nome
    }

    public void cadastrarAluguel(){
        //recebe as infos informadas e verifica e manda pro alugueis.addAluguel()
    }

    public void mostrarDadosAluguel(){
        //mostra os alugueis cadstrados q nem no  mostra clientes no form de clientes
    }

    public void limparCampos(){
        campoTextoId.setText("");
        campoTextoData.setText("");
        campoTextoPeriodo.setText("");
        areaTexto.setText("");

    }
}
