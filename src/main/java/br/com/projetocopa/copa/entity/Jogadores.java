package br.com.projetocopa.copa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jogadores {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jogadores_seq")
    @SequenceGenerator(name = "jogadores_seq", sequenceName = "jogadores_id_seq", allocationSize = 1)
    @Column(name = "id_jogadores")
    private Integer idJogadores;

    @Column(name = "raridade_jogadores", nullable = false)
    private String raridadeJogadores;

    @Column(name = "img_jogadores", nullable = false)
    private String imgJogadores;

    @Column(name = "posicao_jogadores", nullable = false)
    private String posicaoJogadores;

    @Column(name = "nome_jogadores", nullable = false)
    private String nomeJogadores;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;
}
