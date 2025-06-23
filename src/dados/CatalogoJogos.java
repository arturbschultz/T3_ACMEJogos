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
}
