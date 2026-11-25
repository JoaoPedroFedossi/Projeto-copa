package br.com.projetocopa.copa.repository;

import br.com.projetocopa.copa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNome(String nome);

    boolean existsByNome(String nome);
}
