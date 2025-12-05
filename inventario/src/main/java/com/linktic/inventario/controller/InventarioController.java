package com.linktic.inventario.controller;

import com.linktic.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{productoId}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long productoId) {
        return inventarioService.obtenerPorId(productoId);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        return inventarioService.actualizarCantidad(productoId, cantidad);
    }

    @PostMapping("/compras")
    public ResponseEntity<?> comprar(@RequestParam Long productoId, @RequestParam Integer cantidad) {
        return inventarioService.comprar(productoId, cantidad);
    }
}
