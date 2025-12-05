package com.linktic.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioAttributesDTO {
    private Long productoId;
    private Integer cantidad;
}
