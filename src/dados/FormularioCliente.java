package dados;
import javax.swing.*;
import java.awt.*;

//eu
public class FormularioCliente extends JFrame {
    private JTextField campoTextoNome, campoTextoEndereco, campoTextoNumero, campoTextoNomeFantasia, campoTextoCnpj;
    private JButton botaoEnviar, botaoCancelar, botaoLimparCamposTexto, botaoMostrarDados;
    private JTextArea areaTexto;
    private JComboBox<String> campoTipoCliente;
    private JPanel painelCampoTexto, painelCampoTexto2, painelCampoTexto3, painelCampoTexto4, painelCampoTexto5;
    private Clientela clientela;

    public FormularioCliente(){
        /**
         * d
         */

        super();
        clientela = new Clientela();
        setTitle("Cadastro de Clientes");
        setSize(590,1200);

        // Painel principal da janela
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        // Painel intermediario para o texto inicial
        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações:");
        painelRotulo.add(rotulo);

        JPanel painelTipoCliente = new JPanel();
        painelTipoCliente.setLayout(layoutRotulo);
        JLabel labelTipoCliente = new JLabel("Tipo de Cliente: ");
        String[] tiposCliente = {"Empresarial", "Individual"};
        campoTipoCliente = new JComboBox<>(tiposCliente);
        campoTipoCliente.addActionListener(e -> atualizarCamposEspecificos());
        painelTipoCliente.add(labelTipoCliente);
        painelTipoCliente.add(campoTipoCliente);

        // Painel intermediario para o campo de texto
        JPanel painelCampoTexto = new JPanel();
        FlowLayout layoutCampoTexto = new FlowLayout(FlowLayout.LEFT);
        painelCampoTexto.setLayout(layoutRotulo);
        JLabel labelNome = new JLabel("Nome completo:");
        campoTextoNome = new JTextField(40);
        painelCampoTexto.add(labelNome);
        painelCampoTexto.add(campoTextoNome);

        // Painel intermediario para o campo de texto
        JPanel painelCampoTexto2 = new JPanel();
        painelCampoTexto2.setLayout(layoutRotulo);
        JLabel labelMensagem2 = new JLabel("Endereço: ");
        campoTextoEndereco = new JTextField(40);
        painelCampoTexto2.add(labelMensagem2);
        painelCampoTexto2.add(campoTextoEndereco);

        JPanel painelCampoTexto3 = new JPanel();
        painelCampoTexto3.setLayout(layoutRotulo);
        JLabel labelMensagem3 = new JLabel("Numero de cadastro: ");
        campoTextoNumero = new JTextField(40);
        painelCampoTexto3.add(labelMensagem3);
        painelCampoTexto3.add(campoTextoNumero);

        painelCampoTexto4 = new JPanel();
        painelCampoTexto4.setLayout(layoutRotulo);
        JLabel labelMensagem4 = new JLabel("Nome fantasia:");
        campoTextoNomeFantasia = new JTextField(40);
        painelCampoTexto4.add(labelMensagem4);
        painelCampoTexto4.add(campoTextoNomeFantasia);

        painelCampoTexto5 = new JPanel();
        painelCampoTexto5.setLayout(layoutRotulo);
        JLabel labelMensagem5 = new JLabel("CNPJ:");
        campoTextoCnpj = new JTextField(40);
        painelCampoTexto5.add(labelMensagem5);
        painelCampoTexto5.add(campoTextoCnpj);

        // Painel intermediario para os botoes
        JPanel painelBotoes = new JPanel();
        FlowLayout layoutBotoes = new FlowLayout(FlowLayout.RIGHT);
        painelBotoes.setLayout(layoutBotoes);
        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(e -> {
            cadastrarCliente();
        });
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> {
            System.exit(0);        //por default 0 = sair.
        });
        botaoLimparCamposTexto = new JButton("Limpar");
        botaoLimparCamposTexto.addActionListener(e -> {
            limparCampos();
        });
        //

        botaoMostrarDados = new JButton("Mostrar Dados");
        botaoMostrarDados.addActionListener(e -> {

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
        painelPrincipal.add(painelCampoTexto4);
        painelPrincipal.add(painelCampoTexto5);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelRotuloMensagens);
        painelPrincipal.add(painelAreaTexto);

        this.add(painelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void atualizarCamposEspecificos() {
        String tipoSelecionado = (String) campoTipoCliente.getSelectedItem();
        if (tipoSelecionado.equals("Empresarial")) {
            painelCampoTexto4.setVisible(true);
            painelCampoTexto5.setVisible(true);
        } else {
            painelCampoTexto4.setVisible(false);
            painelCampoTexto5.setVisible(false);
        }
    }

    public void limparCampos(){
        campoTextoNome.setText("");
        campoTextoEndereco.setText("");
        campoTextoNumero.setText("");
        areaTexto.setText("");
    }

    public void cadastrarCliente(){
        try {
            String nome = campoTextoNome.getText();
            String endereco = campoTextoEndereco.getText();
            int numero = Integer.parseInt(campoTextoNumero.getText());
            if (nome.isEmpty() || endereco.isEmpty()) {
                if (nome.isEmpty()) {
                    areaTexto.append("Erro: o campo \"Nome completo\" esta vazio.\n");
                }
                if (endereco.isEmpty()) {
                    areaTexto.append("Erro: o campo \"Endereço\" esta vazio.\n");
                }
            } else {
                Cliente cliente = new Cliente(numero, nome, endereco);
                if (clientela.addCliente(cliente)) {
                    limparCampos();
                    areaTexto.setText("Cliente cadastrado com sucesso.\n");
                } else {
                    areaTexto.append("Erro: este numero ja foi cadastrado por outro cliente.\n");
                    campoTextoNumero.setText("");
                }
            }
        }
        catch(NumberFormatException e){
            areaTexto.append("Erro: o campo \"Numero de cadastro\" esta vazio.\n");
        }
    }
}
