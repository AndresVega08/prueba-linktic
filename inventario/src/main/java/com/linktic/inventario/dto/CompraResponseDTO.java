package com.linktic.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponseDTO {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidadComprada;
    private Double precioUnitario;
    private Double total;
    private String estado;
}
