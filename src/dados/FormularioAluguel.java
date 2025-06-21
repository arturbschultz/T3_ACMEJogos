package dados;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class FormularioAluguel extends JFrame {
    private JTextArea areaTexto;
    private Alugueis alugueis;
    private Clientela clientela;

    public FormularioAluguel() {
        super();
        alugueis = new Alugueis();
        setTitle("Cadastro de Alugueis");
        setSize(590,900);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel();
        FlowLayout layoutRotulo = new FlowLayout(FlowLayout.LEFT);
        painelRotulo.setLayout(layoutRotulo);
        JLabel rotulo = new JLabel("Por favor, digite as informações:");
        painelRotulo.add(rotulo);
    }

    private void mostrarDados(){
        TreeMap<Integer, Cliente> clientes = clientela.getClientela();
        StringBuilder dados = new StringBuilder("Clientes ja cadastrados:\n");

        for(Map.Entry<Integer, Cliente> clientela: clientes.entrySet()){
            Cliente cliente = clientela.getValue();
            dados.append(clientela.getKey()).append(" - ").append(cliente.getNome()).append(" - ").append(cliente.getEndereco()).append("\n");
        }
        areaTexto.setText(dados.toString());
    }
}
