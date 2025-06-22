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
        clientela = FormularioCliente.getClientelaForm();
        catalogo = FormularioJogos.getCatalogoForm();
        setTitle("Cadastro de Alugueis");
        if(clientela.getClientela().isEmpty() || catalogo.getCatalogo().isEmpty()) {
            setSize(400, 100);
        }else{
            setSize(595, 650);
        }

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações:");
        painelRotulo.add(rotulo);

        JPanel painelRotulo2 = new JPanel();
        painelRotulo2.setLayout(layoutRotulo);
        JLabel rotulo2 = new JLabel("Nenhum cliente ou jogo cadastrado");
        painelRotulo2.add(rotulo2);

        JPanel painelRotulo3 = new JPanel();
        painelRotulo3.setLayout(layoutRotulo);
        JLabel rotulo3 = new JLabel("Nenhum cliente cadastrado");
        painelRotulo3.add(rotulo3);

        JPanel painelRotulo4 = new JPanel();
        painelRotulo4.setLayout(layoutRotulo);
        JLabel rotulo4 = new JLabel("Nenhum jogo cadastrado");
        painelRotulo4.add(rotulo4);

        JPanel painelRotulo5 = new JPanel();
        painelRotulo5.setLayout(layoutRotulo);
        JLabel rotulo5 = new JLabel("Por favor, cadastre pelo menos um cliente e um jogo.");
        painelRotulo5.add(rotulo5);

        //escolher o cliente
        JPanel painelCliente = new JPanel();
        painelCliente.setLayout(layoutRotulo);
        JLabel labelCliente = new JLabel("Escolha o Cliente: ");
        campoCliente = new JComboBox<>();
        for (Cliente c : clientela.getClientela().values()) {
            campoCliente.addItem(c.getNome());
        }
        campoCliente.addActionListener(e -> selecionarCliente());
        painelCliente.add(labelCliente);
        painelCliente.add(campoCliente);

        //escolher o jogo
        JPanel painelJogo = new JPanel();
        painelJogo.setLayout(layoutRotulo);
        JLabel labelJogo = new JLabel("Escolha o Jogo: ");
        campoJogo = new JComboBox<>();
        for(Jogo j : catalogo.getCatalogo().values()){
            campoJogo.addItem(j.getNome());
        }
        campoJogo.addActionListener(e -> selecionarJogo());
        painelCliente.add(labelJogo);
        painelCliente.add(campoJogo);

        //
        JPanel painelCampoTexto = new JPanel();
        painelCampoTexto.setLayout(layoutRotulo);
        JLabel labelId = new JLabel("Identificador:");
        campoTextoId = new JTextField(40);
        painelCampoTexto.add(labelId);
        painelCampoTexto.add(campoTextoId);

        JPanel painelCampoTexto2 = new JPanel();
        painelCampoTexto2.setLayout(layoutRotulo);
        JLabel labelData = new JLabel("Data Inicial:");
        campoTextoData = new JTextField(40);
        painelCampoTexto2.add(labelData);
        painelCampoTexto2.add(campoTextoData);

        JPanel painelCampoTexto3 = new JPanel();
        painelCampoTexto3.setLayout(layoutRotulo);
        JLabel labelPeriodo = new JLabel("Periodo:");
        campoTextoPeriodo = new JTextField(40);
        painelCampoTexto3.add(labelPeriodo);
        painelCampoTexto3.add(campoTextoPeriodo);

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
            mostrarClientes();
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


        if(clientela.getClientela().isEmpty() && catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo2);
            painelPrincipal.add(painelRotulo5);
        }else if(!clientela.getClientela().isEmpty() && catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo4);
            painelPrincipal.add(painelRotulo5);
        }else if(clientela.getClientela().isEmpty() && !catalogo.getCatalogo().isEmpty()) {
            painelPrincipal.add(painelRotulo3);
            painelPrincipal.add(painelRotulo5);
        }else{
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


    private void mostrarClientes(){
        StringBuilder dados = clientela.mostrarDadosCliente();
        areaTexto.setText(dados.toString());
    }


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
        String clienteSelect = (String) campoCliente.getSelectedItem();
    }

    public void selecionarJogo(){
        //percorre a treemap de jogos e acha o jogo pelo nome
        String jogoSelect = (String) campoJogo.getSelectedItem();
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
