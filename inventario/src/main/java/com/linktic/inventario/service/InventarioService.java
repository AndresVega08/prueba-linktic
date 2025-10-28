package com.linktic.inventario.service;

import com.linktic.inventario.dto.*;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repo.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    //private final String PRODUCTOS_URL = "http://localhost:8081/productos/";
    @Value("${PRODUCTOS_URL}")
    private String PRODUCTOS_URL;

    public ResponseEntity<JsonApiResponse<InventarioAttributesDTO>> obtenerPorId(Long productoId) {
        Optional<Inventario> inv = inventarioRepository.findById(productoId);
        if (inv.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Inventario i = inv.get();
        JsonApiResponse<InventarioAttributesDTO> response = JsonApiResponse.<InventarioAttributesDTO>builder()
                .data(JsonApiData.<InventarioAttributesDTO>builder()
                        .type("inventario")
                        .id(String.valueOf(i.getProductoId()))
                        .attributes(InventarioAttributesDTO.builder()
                                .productoId(i.getProductoId())
                                .cantidad(i.getCantidad())
                                .build())
                        .build())
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<JsonApiResponse<InventarioAttributesDTO>> actualizarCantidad(Long productoId, Integer cantidad) {
        Inventario inv = inventarioRepository.findById(productoId)
                .orElse(Inventario.builder().productoId(productoId).build());

        inv.setCantidad(cantidad);
        inventarioRepository.save(inv);

        return obtenerPorId(productoId);
    }

    public ResponseEntity<JsonApiResponse<CompraResponseDTO>> comprar(Long productoId, Integer cantidad) {
        ResponseEntity<JsonApiResponse<Object>> response =
                restTemplate.getForEntity(PRODUCTOS_URL + productoId, (Class<JsonApiResponse<Object>>) (Class<?>) JsonApiResponse.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var data = (java.util.LinkedHashMap<String, Object>) response.getBody().getData().getAttributes();
        String nombreProducto = (String) data.get("nombre");
        Double precioUnitario = Double.valueOf(data.get("precio").toString());

        Inventario inventario = inventarioRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (inventario.getCantidad() < cantidad) {
            CompraResponseDTO errorCompra = CompraResponseDTO.builder()
                    .productoId(productoId)
                    .nombreProducto(nombreProducto)
                    .cantidadComprada(cantidad)
                    .precioUnitario(precioUnitario)
                    .total(0.0)
                    .estado("INVENTARIO_INSUFICIENTE")
                    .build();
            return ResponseEntity.ok(JsonApiResponse.<CompraResponseDTO>builder()
                    .data(JsonApiData.<CompraResponseDTO>builder()
                            .type("compra")
                            .id(String.valueOf(productoId))
                            .attributes(errorCompra)
                            .build())
                    .build());
        }

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioRepository.save(inventario);

        CompraResponseDTO compra = CompraResponseDTO.builder()
                .productoId(productoId)
                .nombreProducto(nombreProducto)
                .cantidadComprada(cantidad)
                .precioUnitario(precioUnitario)
                .total(precioUnitario * cantidad)
                .estado("COMPRA_EXITOSA")
                .build();

        return ResponseEntity.ok(JsonApiResponse.<CompraResponseDTO>builder()
                .data(JsonApiData.<CompraResponseDTO>builder()
                        .type("compra")
                        .id(String.valueOf(productoId))
                        .attributes(compra)
                        .build())
                .build());
    }

    public void setInventarioRepository(InventarioRepository repo) {
        this.inventarioRepository = repo;
    }

}
