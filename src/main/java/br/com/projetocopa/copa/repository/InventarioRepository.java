package br.com.projetocopa.copa.repository;

import br.com.projetocopa.copa.entity.Inventario;
import br.com.projetocopa.copa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    Optional<Inventario> findByUsuario(Usuario usuario);
}
