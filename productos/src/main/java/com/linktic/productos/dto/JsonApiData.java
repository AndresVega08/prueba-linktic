package com.linktic.productos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonApiData<T> {
    private String type;
    private String id;
    private T attributes;
}
