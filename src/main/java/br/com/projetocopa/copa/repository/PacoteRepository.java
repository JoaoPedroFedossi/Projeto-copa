package br.com.projetocopa.copa.repository;


import br.com.projetocopa.copa.entity.Pacote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PacoteRepository extends JpaRepository<Pacote, Integer> {
    
    
}