package com.rest.crudsure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.crudsure.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
