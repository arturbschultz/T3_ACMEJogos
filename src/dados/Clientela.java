package dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Clientela {
    private TreeMap<Integer, Cliente> clientela;

    public Clientela() {
        clientela = new TreeMap<>();
    }

    //getter
    public TreeMap<Integer, Cliente> getClientela() {
        return clientela;
    }

    public boolean addCliente(Cliente cliente) {
        if (clientela.containsKey(cliente.getNumero())) {
            return false;
        }
        clientela.put(cliente.getNumero(), cliente);
        return true;
    }

    public Cliente getClienteNome(String nome) {
        for (Cliente cliente : clientela.values()) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public StringBuilder mostrarDadosCliente() {
        TreeMap<Integer, Cliente> clientes = clientela;
        StringBuilder dados;
        if (clientela.isEmpty()) {
            dados = new StringBuilder("Não há clientes cadastrados.\n");
            return dados;
        }
        dados = new StringBuilder("Clientes ja cadastrados:\n");
        for (Map.Entry<Integer, Cliente> clientela : clientes.entrySet()) {
            Cliente cliente = clientela.getValue();
            dados.append(clientela.getKey()).append(" - ").append(cliente.getNome()).append(" - ").append(cliente.getEndereco()).append("\n");
        }
        return dados;
    }

    public void carregarClientesDoCSV(String arquivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            boolean primeiraLinha = true;
            int numero, tipoCliente;
            String nome, endereco, cpfOuNomeFantasia, cnpj;
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                String[] dados = linha.split(";");
                try {
                    if(dados.length == 6) {
                        numero = Integer.parseInt(dados[0].trim());
                        nome = dados[1].trim();
                        endereco = dados[2].trim();
                        tipoCliente = Integer.parseInt(dados[3].trim());
                        cpfOuNomeFantasia = dados[4].trim();
                        cnpj = dados[5].trim();
                    }else{
                        numero = Integer.parseInt(dados[0].trim());
                        nome = dados[1].trim();
                        endereco = dados[2].trim();
                        tipoCliente = Integer.parseInt(dados[3].trim());
                        cpfOuNomeFantasia = dados[4].trim();
                        cnpj = "";
                    }
                    Cliente cliente;
                    if (tipoCliente == 1) { // Cliente Individual
                        cliente = new Individual(
                            numero,
                            nome,
                            endereco,
                            cpfOuNomeFantasia
                        );
                    } else { // Cliente Empresarial
                        cliente = new Empresarial(
                            numero,
                            nome,
                            endereco,
                            cpfOuNomeFantasia,
                            cnpj
                        );
                    }
                    addCliente(cliente);
                } catch (Exception e) {
                    System.out.println("Erro ao processar cliente: " + linha);
                    e.printStackTrace(System.out);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
