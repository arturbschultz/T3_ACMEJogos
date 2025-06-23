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
}
