package com.rest.crudsure.dto;

import java.math.BigDecimal;

public class ProductoResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private Long categoriaId;
    private String categoriaNombre;

    public ProductoResponse(Long id, String nombre, BigDecimal precio, Integer stock, Long categoriaId, String categoriaNombre) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public Long getCategoriaId() { return categoriaId; }
    public String getCategoriaNombre() { return categoriaNombre; }
}