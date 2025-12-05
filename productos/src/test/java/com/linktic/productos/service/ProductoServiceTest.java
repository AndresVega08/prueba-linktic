package com.linktic.productos.service;

import com.linktic.productos.model.Producto;
import com.linktic.productos.repo.ProductoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    private ProductoRepo productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setup() {
        productoRepository = Mockito.mock(ProductoRepo.class);
        productoService = new ProductoService();
        productoService.setProductoRepository(productoRepository);
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(null, "Teclado", 120000.0, "Mecánico RGB");
        when(productoRepository.save(any())).thenReturn(new Producto(1L, "Teclado", 120000.0, "Mecánico RGB"));

        ResponseEntity<?> response = productoService.crearProducto(producto);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorIdExistente() {
        Producto producto = new Producto(1L, "Mouse", 90000.0, "Inalámbrico");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ResponseEntity<?> response = productoService.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorIdNoExistente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productoService.obtenerPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
