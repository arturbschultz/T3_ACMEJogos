package dados;
import javax.swing.*;
import java.awt.*;

public class FormularioJogos extends JFrame {
    private JTextField campoTextoNome, campoTextoCodigo, campoTextoValorBase;
    private JComboBox<String> campoTipoJogo;
    private JComboBox<TipoEletronico> campoEletronico;
    private JComboBox<TipoMesa> campoMesa;
    private JTextField campoTextoPlataforma, campoTextoNumeroPecas;
    private JButton botaoEnviar, botaoCancelar, botaoLimparCamposTexto, botaoMostrarDados;
    private JTextArea areaTexto;
    private JPanel painelEletronico;
    private JPanel painelMesa;

    public FormularioJogos() {
        super();
        setTitle("Cadastro de Jogos");
        setSize(590, 700);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações do jogo:");
        painelRotulo.add(rotulo);

        JPanel painelTipoJogo = new JPanel();
        painelTipoJogo.setLayout(layoutRotulo);
        JLabel labelTipoJogo = new JLabel("Tipo de Jogo: ");
        String[] tiposJogo = {"Eletrônico", "Mesa"};
        campoTipoJogo = new JComboBox<>(tiposJogo);
        campoTipoJogo.addActionListener(e -> atualizarCamposEspecificos());
        painelTipoJogo.add(labelTipoJogo);
        painelTipoJogo.add(campoTipoJogo);

        JPanel painelCodigo = new JPanel();
        painelCodigo.setLayout(layoutRotulo);
        JLabel labelCodigo = new JLabel("Código: ");
        campoTextoCodigo = new JTextField(40);
        painelCodigo.add(labelCodigo);
        painelCodigo.add(campoTextoCodigo);

        JPanel painelNome = new JPanel();
        painelNome.setLayout(layoutRotulo);
        JLabel labelNome = new JLabel("Nome: ");
        campoTextoNome = new JTextField(40);
        painelNome.add(labelNome);
        painelNome.add(campoTextoNome);

        JPanel painelValorBase = new JPanel();
        painelValorBase.setLayout(layoutRotulo);
        JLabel labelValorBase = new JLabel("Valor Base: ");
        campoTextoValorBase = new JTextField(40);
        painelValorBase.add(labelValorBase);
        painelValorBase.add(campoTextoValorBase);

        painelEletronico = new JPanel();
        painelEletronico.setLayout(layoutRotulo);
        JLabel labelTipoEletronico = new JLabel("Tipo Eletrônico: ");
        campoEletronico = new JComboBox<>(TipoEletronico.values());
        JLabel labelPlataforma = new JLabel("Plataforma: ");
        campoTextoPlataforma = new JTextField(40);
        painelEletronico.add(labelTipoEletronico);
        painelEletronico.add(campoEletronico);
        painelEletronico.add(labelPlataforma);
        painelEletronico.add(campoTextoPlataforma);
        painelEletronico.setVisible(false);

        painelMesa = new JPanel();
        painelMesa.setLayout(layoutRotulo);
        JLabel labelTipoMesa = new JLabel("Tipo Mesa: ");
        campoMesa = new JComboBox<>(TipoMesa.values());
        JLabel labelNumeroPecas = new JLabel("Número de Peças: ");
        campoTextoNumeroPecas = new JTextField(40);
        painelMesa.add(labelTipoMesa);
        painelMesa.add(campoMesa);
        painelMesa.add(labelNumeroPecas);
        painelMesa.add(campoTextoNumeroPecas);
        painelMesa.setVisible(false);

        JPanel painelBotoes = new JPanel();
        FlowLayout layoutBotoes = new FlowLayout(FlowLayout.RIGHT);
        painelBotoes.setLayout(layoutBotoes);
        
        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(e -> {
        });
        
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> {
            System.exit(0);
        });
        
        botaoLimparCamposTexto = new JButton("Limpar");
        botaoLimparCamposTexto.addActionListener(e -> {
            limparCampos();
        });
        
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

        areaTexto = new JTextArea(5, 47);
        JScrollPane painelAreaTexto = new JScrollPane(areaTexto);

        painelPrincipal.add(painelRotulo);
        painelPrincipal.add(painelTipoJogo);
        painelPrincipal.add(painelCodigo);
        painelPrincipal.add(painelNome);
        painelPrincipal.add(painelValorBase);
        painelPrincipal.add(painelEletronico);
        painelPrincipal.add(painelMesa);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelRotuloMensagens);
        painelPrincipal.add(painelAreaTexto);

        this.add(painelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void atualizarCamposEspecificos() {
        String tipoSelecionado = (String) campoTipoJogo.getSelectedItem();
        if (tipoSelecionado.equals("Eletrônico")) {
            painelEletronico.setVisible(true);
            painelMesa.setVisible(false);
        } else {
            painelEletronico.setVisible(false);
            painelMesa.setVisible(true);
        }
    }

    private void limparCampos() {
        campoTextoNome.setText("");
        campoTextoCodigo.setText("");
        campoTextoValorBase.setText("");
        campoTextoPlataforma.setText("");
        campoTextoNumeroPecas.setText("");
        campoTipoJogo.setSelectedIndex(0);
        campoEletronico.setSelectedIndex(0);
        campoMesa.setSelectedIndex(0);
        areaTexto.setText("");
    }
}
