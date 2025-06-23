package dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

// Classe responsavel por gerenciar a coleçao de jogos
public class CatalogoJogos {

    private TreeMap<Integer, Jogo> catalogo;

    // Construtor
    public CatalogoJogos() {
        catalogo = new TreeMap<>();
    }

    public boolean addJogo(Jogo jogo) {
        if (catalogo.containsKey(jogo.getCodigo())) {
            return false;
        }
        catalogo.put(jogo.getCodigo(), jogo);
        return true;
    }

    public Jogo getJogoNome(String nome) {
        String nomeJogo = nome;
        for (Jogo jogo : catalogo.values()) {
            if (jogo.getNome().equals(nomeJogo)) {
                return jogo;
            }
        }
        return null;
    }

    public StringBuilder mostrarDadosJogos() {
        StringBuilder dados;
        if (catalogo.isEmpty()) {
            dados = new StringBuilder("\nNão há jogos cadastrados.");
            return dados;
        }

        dados = new StringBuilder("\nJogos Cadastrados:");
        for (Jogo jogo : catalogo.values()) {
            dados.append("\nCódigo: " + jogo.getCodigo() + "\nNome: " + jogo.getNome());
            dados.append("\nValor Base: R$" + String.format("%.2f", jogo.getValorBase()));

            if (jogo instanceof JogoEletronico) {
                JogoEletronico jogoEletronico = (JogoEletronico) jogo;
                dados.append("\nTipo: Eletrônico");
                dados.append("\nTipo Eletrônico: " + jogoEletronico.getTipo());
                dados.append("\nPlataforma: " + jogoEletronico.getPlataforma());
            } else if (jogo instanceof JogoMesa) {
                JogoMesa jogoMesa = (JogoMesa) jogo;
                dados.append("\nTipo: Mesa");
                dados.append("\nTipo Mesa: " + jogoMesa.getTipo());
                dados.append("\nNúmero de Peças: " + jogoMesa.getNumeroPecas());
            }

            dados.append("\nValor do Aluguel: R$" + String.format("%.2f", jogo.calculaAluguel()));
        }
        return dados;
    }

    public TreeMap<Integer, Jogo> getCatalogo() {
        return catalogo;
    }

    public void carregarJogosDoCSV(String arquivoCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                String[] dados = linha.split(";");
                if (dados.length >= 6) {
                    try {
                        int codigo = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        double valorBase = Double.parseDouble(dados[2].trim());
                        int tipoJogo = Integer.parseInt(dados[3].trim());
                        String tipo = dados[4].trim();
                        String plataformaOuPecas = dados[5].trim();

                        Jogo jogo;
                        if (tipoJogo == 1) { // Jogo Eletrônico
                            jogo = new JogoEletronico(codigo, nome, valorBase, TipoEletronico.valueOf(tipo), plataformaOuPecas);
                        }else{ // Jogo de Mesa
                            jogo = new JogoMesa(codigo, nome, valorBase, TipoMesa.valueOf(tipo), Integer.parseInt(plataformaOuPecas));
                        }
                        addJogo(jogo);
                    } catch (Exception e) {
                        System.out.println("Erro ao processar jogo: " + linha);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de jogos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Salva os jogos em um arquivo CSV.
     * @param arquivoCSV nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarJogosEmCSV(String arquivoCSV) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoCSV)) {
            writer.println("codigo;nome;valorBase;tipoJogo;tipo;plataformaOuPecas");
            for (Jogo jogo : catalogo.values()) {
                if (jogo instanceof JogoEletronico) {
                    JogoEletronico eletronico = (JogoEletronico) jogo;
                    writer.printf("%d;%s;%.2f;1;%s;%s\n",
                        eletronico.getCodigo(), eletronico.getNome(), eletronico.getValorBase(),
                        eletronico.getTipo().name(), eletronico.getPlataforma());
                } else if (jogo instanceof JogoMesa) {
                    JogoMesa mesa = (JogoMesa) jogo;
                    writer.printf("%d;%s;%.2f;2;%s;%d\n",
                        mesa.getCodigo(), mesa.getNome(), mesa.getValorBase(),
                        mesa.getTipo().name(), mesa.getNumeroPecas());
                }
            }
        }
    }

    /**
     * Salva os jogos em um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     * @throws Exception se houver erro de escrita
     */
    public void salvarJogosEmJSON(String arquivoJSON) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(arquivoJSON)) {
            writer.println("{");
            writer.println("  \"jogos\": [");
            
            boolean primeiro = true;
            for (Jogo jogo : catalogo.values()) {
                if (!primeiro) {
                    writer.println(",");
                }
                primeiro = false;
                
                writer.print("    {");
                writer.printf("\"codigo\": %d, ", jogo.getCodigo());
                writer.printf("\"nome\": \"%s\", ", escapeJSON(jogo.getNome()));
                writer.printf("\"valorBase\": %.2f", jogo.getValorBase());
                
                if (jogo instanceof JogoEletronico) {
                    JogoEletronico eletronico = (JogoEletronico) jogo;
                    writer.printf(", \"tipo\": \"eletronico\", \"tipoEletronico\": \"%s\", \"plataforma\": \"%s\"", 
                        eletronico.getTipo().name(), escapeJSON(eletronico.getPlataforma()));
                } else if (jogo instanceof JogoMesa) {
                    JogoMesa mesa = (JogoMesa) jogo;
                    writer.printf(", \"tipo\": \"mesa\", \"tipoMesa\": \"%s\", \"numeroPecas\": %d", 
                        mesa.getTipo().name(), mesa.getNumeroPecas());
                }
                writer.print("}");
            }
            
            writer.println();
            writer.println("  ]");
            writer.println("}");
        }
    }

    /**
     * Carrega os jogos de um arquivo JSON.
     * @param arquivoJSON nome do arquivo (com extensão)
     */
    public void carregarJogosDoJSON(String arquivoJSON) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoJSON))) {
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            
            String json = conteudo.toString();
            // Remove espaços em branco e quebras de linha
            json = json.replaceAll("\\s+", "");
            
            // Extrai o array de jogos
            int inicio = json.indexOf("\"jogos\":[") + 9;
            int fim = json.lastIndexOf("]");
            if (inicio > 8 && fim > inicio) {
                String jogosJson = json.substring(inicio, fim);
                
                // Processa cada jogo
                String[] jogos = jogosJson.split("\\},\\{");
                for (String jogoJson : jogos) {
                    // Remove chaves extras
                    jogoJson = jogoJson.replaceAll("^\\{|\\}$", "");
                    
                    // Extrai os campos
                    int codigo = extrairInteiro(jogoJson, "codigo");
                    String nome = extrairString(jogoJson, "nome");
                    double valorBase = extrairDouble(jogoJson, "valorBase");
                    String tipo = extrairString(jogoJson, "tipo");
                    
                    Jogo jogo;
                    if ("eletronico".equals(tipo)) {
                        String tipoEletronico = extrairString(jogoJson, "tipoEletronico");
                        String plataforma = extrairString(jogoJson, "plataforma");
                        jogo = new JogoEletronico(codigo, nome, valorBase, TipoEletronico.valueOf(tipoEletronico), plataforma);
                    } else if ("mesa".equals(tipo)) {
                        String tipoMesa = extrairString(jogoJson, "tipoMesa");
                        int numeroPecas = extrairInteiro(jogoJson, "numeroPecas");
                        jogo = new JogoMesa(codigo, nome, valorBase, TipoMesa.valueOf(tipoMesa), numeroPecas);
                    } else {
                        continue; // Tipo desconhecido, pula
                    }
                    
                    addJogo(jogo);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON de jogos: " + e.getMessage());
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
     * Extrai um valor double de uma string JSON.
     */
    private double extrairDouble(String json, String campo) {
        String padrao = "\"" + campo + "\":";
        int inicio = json.indexOf(padrao);
        if (inicio == -1) return 0.0;
        
        inicio += padrao.length();
        int fim = json.indexOf(",", inicio);
        if (fim == -1) fim = json.indexOf("}", inicio);
        if (fim == -1) return 0.0;
        
        try {
            return Double.parseDouble(json.substring(inicio, fim).trim());
        } catch (NumberFormatException e) {
            return 0.0;
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
