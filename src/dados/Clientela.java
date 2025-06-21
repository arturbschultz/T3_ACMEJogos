package dados;

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
}
