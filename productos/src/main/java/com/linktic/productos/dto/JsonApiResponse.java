package com.linktic.productos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonApiResponse<T> {
    private JsonApiData<T> data;
}
