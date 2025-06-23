package dados;

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

    public Alugueis(Clientela clientela, CatalogoJogos catalogo) {
        alugueis = new TreeMap<>();
        this.clientela = clientela;
        this.catalogo = catalogo;
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
}
