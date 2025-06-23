package dados;

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

    public TreeMap<Integer, Jogo> getCatalogo() {
        return catalogo;
    }
}
