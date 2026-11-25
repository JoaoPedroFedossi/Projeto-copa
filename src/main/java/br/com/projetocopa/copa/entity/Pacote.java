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
public class Pacote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pacote_seq")
    @SequenceGenerator(name = "pacote_seq", sequenceName = "pacote_id_seq", allocationSize = 1)
    @Column(name = "id_pacote")
    private Integer idPacote;

    @Column(name = "raridade_pacote", nullable = false)
    private String raridadePacote;

    @Column(name = "jogadores_pacote")
    private String jogadoresPacote;

    @ManyToOne
    @JoinColumn(name = "id_gacha")
    private Gacha gacha;
}
