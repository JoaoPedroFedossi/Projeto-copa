package br.com.projetocopa.copa.repository;

import br.com.projetocopa.copa.entity.Inventario;
import br.com.projetocopa.copa.entity.Jogadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadoresRepository extends JpaRepository<Jogadores, Integer> {

    List<Jogadores> findByInventario(Inventario inventario);

    void deleteByInventario(Inventario inventario);
}
