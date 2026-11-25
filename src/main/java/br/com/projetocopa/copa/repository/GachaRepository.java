package br.com.projetocopa.copa.repository;


import br.com.projetocopa.copa.entity.Gacha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GachaRepository extends JpaRepository<Gacha, Integer> {
    
    
}