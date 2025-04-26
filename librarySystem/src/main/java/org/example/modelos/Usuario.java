package org.example.modelos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Usuario {

    private String nombre;
    private List<Prestamo> historialPrestamos = new ArrayList<>();

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        historialPrestamos.add(prestamo);
    }
}
