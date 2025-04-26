package org.example.modelos;

import org.example.enums.EstadoLibro;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Libro> libros = new ArrayList<>();

    // Agregar al catálogo
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    // Buscar por su ISBN
    public Libro buscarPorISBN(String isbn) {
        for (Libro libro : libros) {
            if (libro.getISBN().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // Obtener todos los disponibles
    public List<Libro> obtenerLibrosDisponibles() {
        return libros.stream()
                .filter(libro -> libro.getEstado() == EstadoLibro.DISPONIBLE)
                .toList();
    }

}
