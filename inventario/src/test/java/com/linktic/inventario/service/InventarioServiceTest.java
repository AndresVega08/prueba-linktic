package com.linktic.inventario.service;

import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repo.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceTest {

    private InventarioRepository inventarioRepository;
    private InventarioService inventarioService;

    @BeforeEach
    void setup() {
        inventarioRepository = Mockito.mock(InventarioRepository.class);
        inventarioService = new InventarioService();
        inventarioService.setInventarioRepository(inventarioRepository); // agrega este setter en tu service
    }

    @Test
    void testObtenerInventarioExistente() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(new Inventario(1L, 5)));

        ResponseEntity<?> response = inventarioService.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testInventarioNoExistente() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = inventarioService.obtenerPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
