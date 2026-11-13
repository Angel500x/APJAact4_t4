package com.rest.crudsure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.crudsure.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
