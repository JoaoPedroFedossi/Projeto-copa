package br.com.projetocopa.copa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Gacha {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gacha_seq")
    @SequenceGenerator(name = "gacha_seq", sequenceName = "gacha_id_seq", allocationSize = 1)
    @Column(name = "id_gacha")
    private Integer idGacha;

    @Column(name = "nome_gacha", nullable = false)
    private String nome;

    @Column(name = "descricao_gacha")
    private String descricao;
}
