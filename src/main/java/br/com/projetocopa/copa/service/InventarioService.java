package br.com.projetocopa.copa.service;

import br.com.projetocopa.copa.entity.Inventario;
import br.com.projetocopa.copa.entity.Jogadores;
import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.repository.InventarioRepository;
import br.com.projetocopa.copa.repository.JogadoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final JogadoresRepository jogadoresRepository;

    /** Mapa nome do jogador -> arquivo local em /img/jogadores/ */
    private static final Map<String, String> FOTOS = new HashMap<>();
    static {
        FOTOS.put("Lionel Messi", "messi.png");
        FOTOS.put("Cristiano Ronaldo", "ronaldo.png");
        FOTOS.put("Kylian Mbappé", "mbappe.png");
        FOTOS.put("Erling Haaland", "haaland.png");
        FOTOS.put("Vinícius Júnior", "vinicius.png");
        FOTOS.put("Rodrygo", "rodrygo.png");
        FOTOS.put("Neymar Jr", "neymar.png");
        FOTOS.put("Harry Kane", "kane.png");
        FOTOS.put("Jude Bellingham", "bellingham.png");
        FOTOS.put("Pedri", "pedri.png");
        FOTOS.put("Gavi", "gavi.png");
        FOTOS.put("Luka Modrić", "modric.png");
        FOTOS.put("Kevin De Bruyne", "debruyne.png");
        FOTOS.put("Rodri", "rodri.png");
        FOTOS.put("Bernardo Silva", "bernardosilva.png");
        FOTOS.put("Phil Foden", "foden.png");
        FOTOS.put("Jamal Musiala", "musiala.png");
        FOTOS.put("Florian Wirtz", "wirtz.png");
        FOTOS.put("Eduardo Camavinga", "camavinga.png");
        FOTOS.put("Aurélien Tchouaméni", "tchouameni.png");
        FOTOS.put("Virgil van Dijk", "vandijk.png");
        FOTOS.put("William Saliba", "saliba.png");
        FOTOS.put("Rúben Dias", "dias.png");
        FOTOS.put("Antonio Rüdiger", "rudiger.png");
        FOTOS.put("Marquinhos", "marquinhos.png");
        FOTOS.put("Éder Militão", "militao.png");
        FOTOS.put("Gabriel Magalhães", "gabriel.png");
        FOTOS.put("Alphonso Davies", "davies.png");
        FOTOS.put("Achraf Hakimi", "hakimi.png");
        FOTOS.put("Theo Hernández", "theo.png");
        FOTOS.put("João Cancelo", "cancelo.png");
        FOTOS.put("Trent Alexander-Arnold", "taa.png");
        FOTOS.put("Kyle Walker", "walker.png");
        FOTOS.put("Alisson Becker", "alisson.png");
        FOTOS.put("Ederson", "ederson.png");
        FOTOS.put("Thibaut Courtois", "courtois.png");
        FOTOS.put("Manuel Neuer", "neuer.png");
        FOTOS.put("Gianluigi Donnarumma", "donnarumma.png");
        FOTOS.put("Emiliano Martínez", "emiliano.png");
        FOTOS.put("Lautaro Martínez", "lautaro.png");
        FOTOS.put("Julián Álvarez", "julian.png");
        FOTOS.put("Antoine Griezmann", "griezmann.png");
        FOTOS.put("Ousmane Dembélé", "dembele.png");
        FOTOS.put("Randal Kolo Muani", "kolomuani.png");
        FOTOS.put("Bukayo Saka", "saka.png");
        FOTOS.put("Rafael Leão", "leao.png");
        FOTOS.put("Khvicha Kvaratskhelia", "kvaratskhelia.png");
        FOTOS.put("Victor Osimhen", "osimhen.png");
        FOTOS.put("Marcus Rashford", "rashford.png");
        FOTOS.put("Son Heung-min", "son.png");
        FOTOS.put("Mohamed Salah", "salah.png");
        FOTOS.put("Robert Lewandowski", "lewandowski.png");
        FOTOS.put("Casemiro", "casemiro.png");
        FOTOS.put("Bruno Fernandes", "bruno.png");
        FOTOS.put("Martin Ødegaard", "odegaard.png");
        FOTOS.put("Declan Rice", "rice.png");
        FOTOS.put("Frenkie de Jong", "frenkie.png");
        FOTOS.put("Joshua Kimmich", "kimmich.png");
        FOTOS.put("Ilkay Gündogan", "gundogan.png");
        FOTOS.put("Federico Valverde", "valverde.png");
        FOTOS.put("Enzo Fernández", "enzo.png");
        FOTOS.put("Alexis Mac Allister", "macallister.png");
        FOTOS.put("Nicolás Otamendi", "otamendi.png");
        FOTOS.put("Cristian Romero", "romero.png");
        FOTOS.put("Lisandro Martínez", "lisandro.png");
        FOTOS.put("Dayot Upamecano", "upamecano.png");
        FOTOS.put("Ronald Araújo", "araujo.png");
        FOTOS.put("Jules Koundé", "kounde.png");
        FOTOS.put("Nuno Mendes", "nunomendes.png");
        FOTOS.put("Ferland Mendy", "mendy.png");
        FOTOS.put("Reece James", "reece.png");
        FOTOS.put("Diogo Costa", "diogocosta.png");
        FOTOS.put("Unai Simón", "unaisimon.png");
        FOTOS.put("Yann Sommer", "sommer.png");
        FOTOS.put("Mike Maignan", "maignan.png");
        FOTOS.put("Richarlison", "richarlison.png");
        FOTOS.put("Raphinha", "raphinha.png");
        FOTOS.put("Antony", "antony.png");
        FOTOS.put("Gabriel Martinelli", "martinelli.png");
        FOTOS.put("Endrick", "endrick.png");
        FOTOS.put("Lamine Yamal", "yamal.png");
        FOTOS.put("Arda Güler", "guler.png");
        FOTOS.put("Kobbie Mainoo", "mainoo.png");
        FOTOS.put("Warren Zaïre-Emery", "zaire.png");
        FOTOS.put("João Félix", "joaofelix.png");
        FOTOS.put("Gonçalo Ramos", "goncalo.png");
        FOTOS.put("Bruno Guimarães", "brunog.png");
        FOTOS.put("Lucas Paquetá", "paqueta.png");
        FOTOS.put("Danilo", "danilo.png");
        FOTOS.put("Gleison Bremer", "bremer.png");
        FOTOS.put("Murillo", "murillo.png");
        FOTOS.put("André", "andre.png");
        FOTOS.put("Savinho", "savinho.png");
        FOTOS.put("Estêvão", "estevao.png");
    }

    @Transactional
    public Inventario getOrCreateInventario(Usuario usuario) {
        return inventarioRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Inventario inv = new Inventario();
                    inv.setUsuario(usuario);
                    inv.setQuantidade(0);
                    return inventarioRepository.save(inv);
                });
    }

    @Transactional
    public List<Jogadores> listarJogadoresDoUsuario(Usuario usuario) {
        Inventario inv = inventarioRepository.findByUsuario(usuario).orElse(null);
        if (inv == null) {
            return List.of();
        }
        List<Jogadores> lista = jogadoresRepository.findByInventario(inv);
        // Corrige cartas antigas sem imagem (ou com URL externa quebrada)
        for (Jogadores j : lista) {
            String img = j.getImgJogadores();
            boolean precisa = img == null || img.isBlank()
                    || img.startsWith("http")
                    || !img.startsWith("/img/jogadores/");
            if (precisa) {
                String arquivo = FOTOS.get(j.getNomeJogadores());
                if (arquivo != null) {
                    j.setImgJogadores("/img/jogadores/" + arquivo);
                    jogadoresRepository.save(j);
                }
            }
        }
        return lista;
    }
}
