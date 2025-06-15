package dados;

import java.util.TreeMap;

public class Clientela {
    private TreeMap<Integer, Cliente> clientela = new TreeMap<>();

    public Clientela(TreeMap<Integer, Cliente> clientela) {
        this.clientela = clientela;
    }

    //getter
    public TreeMap<Integer, Cliente> getClientela() {return clientela;}

    public boolean addCliente(Cliente cliente) {
        if(clientela.containsKey(cliente.getNumero())){
            return false;
        }
        clientela.put(cliente.getNumero(), cliente);
        return true;
    }
}
