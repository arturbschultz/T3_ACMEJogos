package dados;
import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

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
    private CatalogoJogos jogos;
    private static CatalogoJogos catalogo;

    public FormularioJogos() {
        super();
        setTitle("Cadastro de Jogos");
        setSize(590, 700);
        catalogo = new CatalogoJogos();

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
        campoTextoPlataforma.setToolTipText("Exemplo: PlayStation 5, Xbox Series X, PC, Nintendo Switch");
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
            cadastrarJogo();
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
            mostrarDadosJogos();
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
        atualizarCamposEspecificos();
    }

    private void cadastrarJogo() {
        // validar os campos básicos
        if (!validarCamposBasicos()) {
            return;
        }

        // Obter os valores basicos
        int codigo = Integer.parseInt(campoTextoCodigo.getText());
        String nome = campoTextoNome.getText();
        double valorBase = Double.parseDouble(campoTextoValorBase.getText());

        // Criar o jogo baseado no tipo selecionado
        Jogo jogo = criarJogo(codigo, nome, valorBase);
        if (jogo == null) {
            return; // Se retornou null, significa que houve erro na criação do jogo
        }

        // Tenta adicionar ao catálogo
        if (catalogo.addJogo(jogo)) {
            limparCampos();
            areaTexto.setText("Jogo cadastrado com sucesso!\n");
        } else {
            areaTexto.append("Erro: já existe um jogo com este código.\n");
            campoTextoCodigo.setText("");
        }
        new FormularioAluguel();
    }

    // Valida os campos basicos
    private boolean validarCamposBasicos() {
        try {
            // Valida código
            if (campoTextoCodigo.getText().isEmpty()) {
                areaTexto.append("Erro: o campo \"Código\" está vazio.\n");
                return false;
            }
            Integer.parseInt(campoTextoCodigo.getText()); // Testa se é número válido

            // Valida nome
            if (campoTextoNome.getText().isEmpty()) {
                areaTexto.append("Erro: o campo \"Nome\" está vazio.\n");
                return false;
            }

            // Valida valor base
            if (campoTextoValorBase.getText().isEmpty()) {
                areaTexto.append("Erro: o campo \"Valor Base\" está vazio.\n");
                return false;
            }
            Double.parseDouble(campoTextoValorBase.getText()); // Testa se é número válido

            return true;
        } catch (NumberFormatException e) {
            areaTexto.append("Erro: campos numéricos inválidos.\n");
            return false;
        }
    }

    // Método para criar o jogo específico
    private Jogo criarJogo(int codigo, String nome, double valorBase) {
        String tipoSelecionado = (String) campoTipoJogo.getSelectedItem();

        try {
            if (tipoSelecionado.equals("Eletrônico")) {
                return criarJogoEletronico(codigo, nome, valorBase);
            } else {
                return criarJogoMesa(codigo, nome, valorBase);
            }
        } catch (Exception e) {
            areaTexto.append("Erro ao criar jogo: " + e.getMessage() + "\n");
            return null;
        }
    }

    // Método para criar jogo eletrônico
    private JogoEletronico criarJogoEletronico(int codigo, String nome, double valorBase) {
        // Validar campos específicos
        if (campoTextoPlataforma.getText().isEmpty()) {
            areaTexto.append("Erro: o campo \"Plataforma\" está vazio.\n");
            return null;
        }

        // Cria e retorna o jogo
        return new JogoEletronico(
                codigo,
                nome,
                valorBase,
                (TipoEletronico) campoEletronico.getSelectedItem(),
                campoTextoPlataforma.getText()
        );
    }

    // Método para criar jogo de mesa
    private JogoMesa criarJogoMesa(int codigo, String nome, double valorBase) {
        // Validar campos específicos
        if (campoTextoNumeroPecas.getText().isEmpty()) {
            areaTexto.append("Erro: o campo \"Número de Peças\" está vazio.\n");
            return null;
        }

        try {
            int numeroPecas = Integer.parseInt(campoTextoNumeroPecas.getText());

            // Criar e retornar o jogo
            return new JogoMesa(
                    codigo,
                    nome,
                    valorBase,
                    (TipoMesa) campoMesa.getSelectedItem(),
                    numeroPecas
            );
        } catch (NumberFormatException e) {
            areaTexto.append("Erro: número de peças inválido.\n");
            return null;
        }
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

    private void mostrarDadosJogos() {
        TreeMap<Integer, Jogo> jogos = catalogo.getCatalogo();
        if (jogos.isEmpty()) {
            areaTexto.setText("Não há jogos cadastrados.\n");
            return;
        }

        StringBuilder dados = new StringBuilder("Jogos Cadastrados:\n\n");
        for (Jogo jogo : jogos.values()) {
            dados.append("Código: ").append(jogo.getCodigo())
                    .append("\nNome: ").append(jogo.getNome())
                    .append("\nValor Base: R$").append(String.format("%.2f", jogo.getValorBase()));

            if (jogo instanceof JogoEletronico) {
                JogoEletronico jogoEletronico = (JogoEletronico) jogo;
                dados.append("\nTipo: Eletrônico")
                        .append("\nTipo Eletrônico: ").append(jogoEletronico.getTipo())
                        .append("\nPlataforma: ").append(jogoEletronico.getPlataforma());
            } else if (jogo instanceof JogoMesa) {
                JogoMesa jogoMesa = (JogoMesa) jogo;
                dados.append("\nTipo: Mesa")
                        .append("\nTipo Mesa: ").append(jogoMesa.getTipo())
                        .append("\nNúmero de Peças: ").append(jogoMesa.getNumeroPecas());
            }

            dados.append("\nValor do Aluguel: R$").append(String.format("%.2f", jogo.calculaAluguel()))
                    .append("\n\n");
        }

        areaTexto.setText(dados.toString());
    }
    public static CatalogoJogos getCatalogoForm(){return catalogo;}
}
