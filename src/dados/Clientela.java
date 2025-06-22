package dados;

import java.util.Map;
import java.util.TreeMap;

public class Clientela {
    private TreeMap<Integer, Cliente> clientela;

    public Clientela() {clientela = new TreeMap<>();}

    //getter
    public TreeMap<Integer, Cliente> getClientela() {return clientela;}

    public boolean addCliente(Cliente cliente) {
        if(clientela.containsKey(cliente.getNumero())){
            return false;
        }
        clientela.put(cliente.getNumero(), cliente);
        return true;
    }

    public Cliente getClienteNome(String nome){
        return null;
    }

    public StringBuilder mostrarDadosCliente(){
        TreeMap<Integer, Cliente> clientes = clientela;
        StringBuilder dados;
        if (clientela.isEmpty()) {
            dados = new StringBuilder("Não há clientes cadastrados.\n");
            return dados;
        }
       dados = new StringBuilder("Clientes ja cadastrados:\n");
        for(Map.Entry<Integer, Cliente> clientela: clientes.entrySet()){
            Cliente cliente = clientela.getValue();
            dados.append(clientela.getKey()).append(" - ").append(cliente.getNome()).append(" - ").append(cliente.getEndereco()).append("\n");
        }
        return dados;
    }
}
