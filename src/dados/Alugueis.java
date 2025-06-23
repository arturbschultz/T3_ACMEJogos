package dados;

import app.ACMEJogos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class Alugueis {
    private TreeMap<Integer, Aluguel> alugueis;
    private Clientela clientela;
    private CatalogoJogos catalogo;

    public Alugueis() {
        alugueis = new TreeMap<>();
        clientela = ACMEJogos.getClientelaApp();
        catalogo = ACMEJogos.getCatalogoApp();
    }

    public boolean addAluguel(Aluguel aluguel) {
        if (alugueis.containsKey(aluguel.getIdentificador())) {
            return false;
        }
        alugueis.put(aluguel.getIdentificador(), aluguel);
        return true;
    }

    public TreeMap<Integer, Aluguel> getAlugueis() {
        return alugueis;
    }

    public StringBuilder mostrarDadosAluguel() {
        TreeMap<Integer, Aluguel> mapaAlugueis = alugueis;
        StringBuilder dados;
        if (mapaAlugueis.isEmpty()) {
            dados = new StringBuilder("Não há aluguéis cadastrados.");
            return dados;
        }

        dados = new StringBuilder("Lista de Aluguéis (ID Decrescente):\n\n");
        for (Aluguel aluguel : mapaAlugueis.descendingMap().values()) {
            dados.append(aluguel.toString()).append("\n");
        }
        return dados;
    }

    public void carregarAlugueisDoCSV(String arquivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            boolean primeiraLinha = true;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    try {
                        int identificador = Integer.parseInt(dados[0].trim());
                        Date dataInicial = formato.parse(dados[1].trim());
                        int periodo = Integer.parseInt(dados[2].trim());
                        int numeroCliente = Integer.parseInt(dados[3].trim());
                        int codigoJogo = Integer.parseInt(dados[4].trim());

                        Cliente cliente = clientela.getClientela().get(numeroCliente);
                        Jogo jogo = catalogo.getCatalogo().get(codigoJogo);

                        if (cliente != null && jogo != null) {
                            Aluguel aluguel = new Aluguel(
                                identificador,
                                dataInicial,
                                periodo,
                                cliente,
                                jogo
                            );
                            addAluguel(aluguel);
                        } else {
                            System.out.println("Cliente ou jogo não encontrado para o aluguel: " + linha);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar aluguel: " + linha);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de aluguéis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean removerAluguel(int identificador) {
        if (alugueis.containsKey(identificador)) {
            alugueis.remove(identificador);
            return true;
        }
        return false;
    }

    /**
     * Salva os aluguéis em um arquivo CSV.
     * @param arquivoCSV nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarAlugueisEmCSV(String arquivoCSV) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoCSV)) {
            writer.println("identificador;dataInicial;periodo;numeroCliente;codigoJogo");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (Aluguel aluguel : alugueis.values()) {
                writer.printf("%d;%s;%d;%d;%d\n",
                    aluguel.getIdentificador(),
                    sdf.format(aluguel.getDataInicial()),
                    aluguel.getPeriodo(),
                    aluguel.getCliente().getNumero(),
                    aluguel.getJogo().getCodigo()
                );
            }
        }
    }

    /**
     * Salva os aluguéis em um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarAlugueisEmJSON(String arquivoJSON) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoJSON)) {
            writer.println("{");
            writer.println("  \"alugueis\": [");
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            boolean primeiro = true;
            for (Aluguel aluguel : alugueis.values()) {
                if (!primeiro) {
                    writer.println(",");
                }
                primeiro = false;
                
                writer.print("    {");
                writer.printf("\"identificador\": %d, ", aluguel.getIdentificador());
                writer.printf("\"dataInicial\": \"%s\", ", sdf.format(aluguel.getDataInicial()));
                writer.printf("\"periodo\": %d, ", aluguel.getPeriodo());
                writer.printf("\"numeroCliente\": %d, ", aluguel.getCliente().getNumero());
                writer.printf("\"codigoJogo\": %d", aluguel.getJogo().getCodigo());
                writer.print("}");
            }
            
            writer.println();
            writer.println("  ]");
            writer.println("}");
        }
    }

    /**
     * Carrega os aluguéis de um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     */
    public void carregarAlugueisDoJSON(String arquivoJSON) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoJSON))) {
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            
            String json = conteudo.toString();
            // Remove espaços em branco e quebras de linha
            json = json.replaceAll("\\s+", "");
            
            // Extrai o array de aluguéis
            int inicio = json.indexOf("\"alugueis\":[") + 12;
            int fim = json.lastIndexOf("]");
            if (inicio > 11 && fim > inicio) {
                String alugueisJson = json.substring(inicio, fim);
                
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                
                // Processa cada aluguel
                String[] alugueisArray = alugueisJson.split("\\},\\{");
                for (String aluguelJson : alugueisArray) {
                    // Remove chaves extras
                    aluguelJson = aluguelJson.replaceAll("^\\{|\\}$", "");
                    
                    try {
                        // Extrai os campos
                        int identificador = extrairInteiro(aluguelJson, "identificador");
                        String dataInicialStr = extrairString(aluguelJson, "dataInicial");
                        int periodo = extrairInteiro(aluguelJson, "periodo");
                        int numeroCliente = extrairInteiro(aluguelJson, "numeroCliente");
                        int codigoJogo = extrairInteiro(aluguelJson, "codigoJogo");

                        Date dataInicial = formato.parse(dataInicialStr);
                        Cliente cliente = clientela.getClientela().get(numeroCliente);
                        Jogo jogo = catalogo.getCatalogo().get(codigoJogo);

                        if (cliente != null && jogo != null) {
                            Aluguel aluguel = new Aluguel(
                                identificador,
                                dataInicial,
                                periodo,
                                cliente,
                                jogo
                            );
                            addAluguel(aluguel);
                        } else {
                            System.out.println("Cliente ou jogo não encontrado para o aluguel: " + aluguelJson);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar aluguel: " + aluguelJson);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON de aluguéis: " + e.getMessage());
            e.printStackTrace();
        }
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
