package dados;
import javax.swing.*;
import java.awt.*;

//eu
public class FormularioCliente extends JFrame {
    private JTextField campoTextoNome, campoTextoEndereco, campoTextoNumero;
    private JButton botaoEnviar, botaoCancelar, botaoLimparCamposTexto, botaoMostrarDados;
    private JTextArea areaTexto;


    public FormularioCliente(){
        /**
         * Fazer cliente escrever dados um a um.
         *
         * ---------------------------------------
         * Titulo
         *
         * Por favor, digite as informações:
         *
         * Nome completo:
         *  *caixa de texto*
         *
         * Endereço:
         *  *caixa de texto*
         *
         * Número de cadastro:
         *  *caixa de texto*
         *
         *   /ENVIAR/  /CANCELAR/  /LIMPAR DADOS/  /MOSTRAR DADOS/
         *
         * Mensagens ao cliente:
         * ---------------------------------------
         *
         * nome pode repetir, endereço tambem, numero nao.
         * se algum dado tiver erro, esvaziar a caixa de texto e mostrar o motivo do erro.
         */

        super();
        setTitle("Cadastro de Clientes");
        setSize(590,600);

        // Painel principal da janela
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        // Painel intermediario para o texto inicial
        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações:");
        painelRotulo.add(rotulo);

        // Painel intermediario para o campo de texto
        JPanel painelCampoTexto = new JPanel();
        FlowLayout layoutCampoTexto = new FlowLayout(FlowLayout.LEFT);
        painelCampoTexto.setLayout(layoutCampoTexto);
        JLabel labelNome = new JLabel("Nome completo:");
        campoTextoNome = new JTextField(40);
        painelCampoTexto.add(labelNome);
        painelCampoTexto.add(campoTextoNome);

        // Painel intermediario para o campo de texto
        JPanel painelCampoTexto2 = new JPanel();
        painelCampoTexto2.setLayout(layoutCampoTexto);
        JLabel labelMensagem2 = new JLabel("Endereço: ");
        campoTextoEndereco = new JTextField(40);
        painelCampoTexto.add(labelMensagem2);
        painelCampoTexto.add(campoTextoEndereco);

        JPanel painelCampoTexto3 = new JPanel();
        painelCampoTexto3.setLayout(layoutCampoTexto);
        JLabel labelMensagem3 = new JLabel("Numero de cadastro: ");
        campoTextoNumero = new JTextField(40);
        painelCampoTexto.add(labelMensagem3);
        painelCampoTexto.add(campoTextoNumero);

        // Painel intermediario para os botoes
        JPanel painelBotoes = new JPanel();
        FlowLayout layoutBotoes = new FlowLayout(FlowLayout.RIGHT);
        painelBotoes.setLayout(layoutBotoes);
        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(e -> {

        });
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> {
            System.exit(0);        //por default 0 = sair.
        });
        botaoLimparCamposTexto = new JButton("Limpar");
        botaoLimparCamposTexto.addActionListener(e -> {
            campoTextoNome.setText("");
            campoTextoEndereco.setText("");
            campoTextoNumero.setText("");
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
        painelPrincipal.add(painelCampoTexto);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelRotuloMensagens);
        painelPrincipal.add(painelAreaTexto);

        this.add(painelPrincipal);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
