package com.linktic.productos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoAttributesDTO {
    private String nombre;
    private Double precio;
    private String descripcion;
}
