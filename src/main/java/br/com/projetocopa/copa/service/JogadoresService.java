package br.com.projetocopa.copa.service;

import br.com.projetocopa.copa.entity.Inventario;
import br.com.projetocopa.copa.entity.Jogadores;
import br.com.projetocopa.copa.repository.InventarioRepository;
import br.com.projetocopa.copa.repository.JogadoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class JogadoresService {

    private final JogadoresRepository jogadoresRepository;
    private final InventarioRepository inventarioRepository;

    /** nome, posição, arquivo em /img/jogadores/ */
    private static final String[][] POOL = {
            {"Lionel Messi", "ATA", "messi.png"},
            {"Cristiano Ronaldo", "ATA", "ronaldo.png"},
            {"Kylian Mbappé", "ATA", "mbappe.png"},
            {"Erling Haaland", "ATA", "haaland.png"},
            {"Vinícius Júnior", "ATA", "vinicius.png"},
            {"Rodrygo", "ATA", "rodrygo.png"},
            {"Neymar Jr", "ATA", "neymar.png"},
            {"Harry Kane", "ATA", "kane.png"},
            {"Jude Bellingham", "MEI", "bellingham.png"},
            {"Pedri", "MEI", "pedri.png"},
            {"Gavi", "MEI", "gavi.png"},
            {"Luka Modrić", "MEI", "modric.png"},
            {"Kevin De Bruyne", "MEI", "debruyne.png"},
            {"Rodri", "MEI", "rodri.png"},
            {"Bernardo Silva", "MEI", "bernardosilva.png"},
            {"Phil Foden", "MEI", "foden.png"},
            {"Jamal Musiala", "MEI", "musiala.png"},
            {"Florian Wirtz", "MEI", "wirtz.png"},
            {"Eduardo Camavinga", "MEI", "camavinga.png"},
            {"Aurélien Tchouaméni", "MEI", "tchouameni.png"},
            {"Virgil van Dijk", "ZAG", "vandijk.png"},
            {"William Saliba", "ZAG", "saliba.png"},
            {"Rúben Dias", "ZAG", "dias.png"},
            {"Antonio Rüdiger", "ZAG", "rudiger.png"},
            {"Marquinhos", "ZAG", "marquinhos.png"},
            {"Éder Militão", "ZAG", "militao.png"},
            {"Gabriel Magalhães", "ZAG", "gabriel.png"},
            {"Alphonso Davies", "LAT", "davies.png"},
            {"Achraf Hakimi", "LAT", "hakimi.png"},
            {"Theo Hernández", "LAT", "theo.png"},
            {"João Cancelo", "LAT", "cancelo.png"},
            {"Trent Alexander-Arnold", "LAT", "taa.png"},
            {"Kyle Walker", "LAT", "walker.png"},
            {"Alisson Becker", "GOL", "alisson.png"},
            {"Ederson", "GOL", "ederson.png"},
            {"Thibaut Courtois", "GOL", "courtois.png"},
            {"Manuel Neuer", "GOL", "neuer.png"},
            {"Gianluigi Donnarumma", "GOL", "donnarumma.png"},
            {"Emiliano Martínez", "GOL", "emiliano.png"},
            {"Lautaro Martínez", "ATA", "lautaro.png"},
            {"Julián Álvarez", "ATA", "julian.png"},
            {"Antoine Griezmann", "ATA", "griezmann.png"},
            {"Ousmane Dembélé", "ATA", "dembele.png"},
            {"Randal Kolo Muani", "ATA", "kolomuani.png"},
            {"Bukayo Saka", "ATA", "saka.png"},
            {"Rafael Leão", "ATA", "leao.png"},
            {"Khvicha Kvaratskhelia", "ATA", "kvaratskhelia.png"},
            {"Victor Osimhen", "ATA", "osimhen.png"},
            {"Marcus Rashford", "ATA", "rashford.png"},
            {"Son Heung-min", "ATA", "son.png"},
            {"Mohamed Salah", "ATA", "salah.png"},
            {"Robert Lewandowski", "ATA", "lewandowski.png"},
            {"Casemiro", "MEI", "casemiro.png"},
            {"Bruno Fernandes", "MEI", "bruno.png"},
            {"Martin Ødegaard", "MEI", "odegaard.png"},
            {"Declan Rice", "MEI", "rice.png"},
            {"Frenkie de Jong", "MEI", "frenkie.png"},
            {"Joshua Kimmich", "MEI", "kimmich.png"},
            {"Ilkay Gündogan", "MEI", "gundogan.png"},
            {"Federico Valverde", "MEI", "valverde.png"},
            {"Enzo Fernández", "MEI", "enzo.png"},
            {"Alexis Mac Allister", "MEI", "macallister.png"},
            {"Nicolás Otamendi", "ZAG", "otamendi.png"},
            {"Cristian Romero", "ZAG", "romero.png"},
            {"Lisandro Martínez", "ZAG", "lisandro.png"},
            {"Dayot Upamecano", "ZAG", "upamecano.png"},
            {"Ronald Araújo", "ZAG", "araujo.png"},
            {"Jules Koundé", "LAT", "kounde.png"},
            {"Nuno Mendes", "LAT", "nunomendes.png"},
            {"Ferland Mendy", "LAT", "mendy.png"},
            {"Reece James", "LAT", "reece.png"},
            {"Diogo Costa", "GOL", "diogocosta.png"},
            {"Unai Simón", "GOL", "unaisimon.png"},
            {"Yann Sommer", "GOL", "sommer.png"},
            {"Mike Maignan", "GOL", "maignan.png"},
            {"Richarlison", "ATA", "richarlison.png"},
            {"Raphinha", "ATA", "raphinha.png"},
            {"Antony", "ATA", "antony.png"},
            {"Gabriel Martinelli", "ATA", "martinelli.png"},
            {"Endrick", "ATA", "endrick.png"},
            {"Lamine Yamal", "ATA", "yamal.png"},
            {"Arda Güler", "MEI", "guler.png"},
            {"Kobbie Mainoo", "MEI", "mainoo.png"},
            {"Warren Zaïre-Emery", "MEI", "zaire.png"},
            {"João Félix", "ATA", "joaofelix.png"},
            {"Gonçalo Ramos", "ATA", "goncalo.png"},
            {"Bruno Guimarães", "MEI", "brunog.png"},
            {"Lucas Paquetá", "MEI", "paqueta.png"},
            {"Danilo", "LAT", "danilo.png"},
            {"Gleison Bremer", "ZAG", "bremer.png"},
            {"Murillo", "ZAG", "murillo.png"},
            {"André", "MEI", "andre.png"},
            {"Savinho", "ATA", "savinho.png"},
            {"Estêvão", "ATA", "estevao.png"}
    };

    private static final String[] RARIDADES = {"COMUM", "INCOMUM", "RARO", "EPICO", "LENDARIO"};
    private static final double[] PROBS = {0.60, 0.85, 0.95, 0.995, 1.0};

    public String sortearRaridade() {
        double r = ThreadLocalRandom.current().nextDouble();
        for (int i = 0; i < PROBS.length; i++) {
            if (r < PROBS[i]) {
                return RARIDADES[i];
            }
        }
        return "COMUM";
    }

    private String caminhoImagem(String arquivo) {
        if (arquivo == null || arquivo.isBlank()) {
            return "/img/jogadores/placeholder.png";
        }
        return "/img/jogadores/" + arquivo;
    }

    @Transactional
    public List<Jogadores> gerarPacote(Inventario inventario) {
        List<Jogadores> obtidos = new ArrayList<>();
        var rnd = ThreadLocalRandom.current();

        for (int i = 0; i < 5; i++) {
            String[] base = POOL[rnd.nextInt(POOL.length)];
            String raridade = sortearRaridade();

            Jogadores j = new Jogadores();
            j.setNomeJogadores(base[0]);
            j.setPosicaoJogadores(base[1]);
            j.setRaridadeJogadores(raridade);
            j.setImgJogadores(caminhoImagem(base.length > 2 ? base[2] : null));
            j.setInventario(inventario);

            obtidos.add(jogadoresRepository.save(j));
        }

        int qtd = inventario.getQuantidade() == null ? 0 : inventario.getQuantidade();
        inventario.setQuantidade(qtd + 5);
        inventarioRepository.save(inventario);

        return obtidos;
    }
}
