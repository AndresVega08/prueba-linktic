package com.linktic.productos.service;

import com.linktic.productos.dto.JsonApiData;
import com.linktic.productos.dto.JsonApiResponse;
import com.linktic.productos.dto.ProductoAttributesDTO;
import com.linktic.productos.model.Producto;
import com.linktic.productos.repo.ProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepo productoRepository;

    public ResponseEntity<JsonApiResponse<ProductoAttributesDTO>> crearProducto(Producto producto) {
        Producto nuevo = productoRepository.save(producto);
        return ResponseEntity.ok(buildResponse(nuevo));
    }

    public ResponseEntity<JsonApiResponse<ProductoAttributesDTO>> obtenerPorId(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto
                .map(value -> ResponseEntity.ok(buildResponse(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> listarTodos() {
        List<JsonApiData<ProductoAttributesDTO>> data = productoRepository.findAll()
                .stream()
                .map(this::buildData)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(java.util.Map.of("data", data));
    }

    private JsonApiResponse<ProductoAttributesDTO> buildResponse(Producto producto) {
        return JsonApiResponse.<ProductoAttributesDTO>builder()
                .data(buildData(producto))
                .build();
    }

    private JsonApiData<ProductoAttributesDTO> buildData(Producto producto) {
        ProductoAttributesDTO attributes = ProductoAttributesDTO.builder()
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .build();

        return JsonApiData.<ProductoAttributesDTO>builder()
                .type("producto")
                .id(String.valueOf(producto.getId()))
                .attributes(attributes)
                .build();
    }

    public void setProductoRepository(ProductoRepo repo) {
        this.productoRepository = repo;
    }

}
