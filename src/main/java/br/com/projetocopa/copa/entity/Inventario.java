package br.com.projetocopa.copa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventario_seq")
    @SequenceGenerator(name = "inventario_seq", sequenceName = "inventario_id_seq", allocationSize = 1)
    @Column(name = "id_inventario")
    private Integer idInventario;

    @Column(name = "quantidade_inventario")
    private Integer quantidade;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
