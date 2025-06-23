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

    /**
     * Salva os clientes em um arquivo CSV.
     * @param arquivoCSV nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarClientesEmCSV(String arquivoCSV) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoCSV)) {
            writer.println("numero;nome;endereco;tipoCliente;cpfOuNomeFantasia;cnpj");
            for (Cliente cliente : clientela.values()) {
                if (cliente instanceof Individual) {
                    Individual ind = (Individual) cliente;
                    writer.printf("%d;%s;%s;1;%s;%s\n",
                        ind.getNumero(), ind.getNome(), ind.getEndereco(), ind.getCpf(), "");
                } else if (cliente instanceof Empresarial) {
                    Empresarial emp = (Empresarial) cliente;
                    writer.printf("%d;%s;%s;2;%s;%s\n",
                        emp.getNumero(), emp.getNome(), emp.getEndereco(), emp.getNomeFantasia(), emp.getCnpj());
                }
            }
        }
    }

    /**
     * Salva os clientes em um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarClientesEmJSON(String arquivoJSON) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoJSON)) {
            writer.println("{");
            writer.println("  \"clientes\": [");
            
            boolean primeiro = true;
            for (Cliente cliente : clientela.values()) {
                if (!primeiro) {
                    writer.println(",");
                }
                primeiro = false;
                
                writer.print("    {");
                writer.printf("\"numero\": %d, ", cliente.getNumero());
                writer.printf("\"nome\": \"%s\", ", escapeJSON(cliente.getNome()));
                writer.printf("\"endereco\": \"%s\"", escapeJSON(cliente.getEndereco()));
                
                if (cliente instanceof Individual) {
                    Individual ind = (Individual) cliente;
                    writer.printf(", \"tipo\": \"individual\", \"cpf\": \"%s\"", escapeJSON(ind.getCpf()));
                } else if (cliente instanceof Empresarial) {
                    Empresarial emp = (Empresarial) cliente;
                    writer.printf(", \"tipo\": \"empresarial\", \"nomeFantasia\": \"%s\", \"cnpj\": \"%s\"", 
                        escapeJSON(emp.getNomeFantasia()), escapeJSON(emp.getCnpj()));
                }
                writer.print("}");
            }
            
            writer.println();
            writer.println("  ]");
            writer.println("}");
        }
    }

    /**
     * Carrega os clientes de um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     */
    public void carregarClientesDoJSON(String arquivoJSON) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoJSON))) {
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            
            String json = conteudo.toString();
            // Remove espaços em branco e quebras de linha
            json = json.replaceAll("\\s+", "");
            
            // Extrai o array de clientes
            int inicio = json.indexOf("\"clientes\":[") + 12;
            int fim = json.lastIndexOf("]");
            if (inicio > 11 && fim > inicio) {
                String clientesJson = json.substring(inicio, fim);
                
                // Processa cada cliente
                String[] clientes = clientesJson.split("\\},\\{");
                for (String clienteJson : clientes) {
                    // Remove chaves extras
                    clienteJson = clienteJson.replaceAll("^\\{|\\}$", "");
                    
                    // Extrai os campos
                    int numero = extrairInteiro(clienteJson, "numero");
                    String nome = extrairString(clienteJson, "nome");
                    String endereco = extrairString(clienteJson, "endereco");
                    String tipo = extrairString(clienteJson, "tipo");
                    
                    Cliente cliente;
                    if ("individual".equals(tipo)) {
                        String cpf = extrairString(clienteJson, "cpf");
                        cliente = new Individual(numero, nome, endereco, cpf);
                    } else if ("empresarial".equals(tipo)) {
                        String nomeFantasia = extrairString(clienteJson, "nomeFantasia");
                        String cnpj = extrairString(clienteJson, "cnpj");
                        cliente = new Empresarial(numero, nome, endereco, nomeFantasia, cnpj);
                    } else {
                        continue; // Tipo desconhecido, pula
                    }
                    
                    addCliente(cliente);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Escapa caracteres especiais para JSON.
     */
    private String escapeJSON(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    /**
     * Extrai um valor inteiro de uma string JSON.
     */
    private int extrairInteiro(String json, String campo) {
        String padrao = "\"" + campo + "\":";
        int inicio = json.indexOf(padrao);
        if (inicio == -1) return 0;
        
        inicio += padrao.length();
        int fim = json.indexOf(",", inicio);
        if (fim == -1) fim = json.indexOf("}", inicio);
        if (fim == -1) return 0;
        
        try {
            return Integer.parseInt(json.substring(inicio, fim).trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Extrai um valor string de uma string JSON.
     */
    private String extrairString(String json, String campo) {
        String padrao = "\"" + campo + "\":\"";
        int inicio = json.indexOf(padrao);
        if (inicio == -1) return "";
        
        inicio += padrao.length();
        int fim = json.indexOf("\"", inicio);
        if (fim == -1) return "";
        
        String valor = json.substring(inicio, fim);
        // Desescapa caracteres especiais
        return valor.replace("\\\"", "\"")
                   .replace("\\\\", "\\")
                   .replace("\\n", "\n")
                   .replace("\\r", "\r")
                   .replace("\\t", "\t");
    }
}
