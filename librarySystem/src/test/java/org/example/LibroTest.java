package org.example;

import org.example.enums.EstadoLibro;
import org.example.modelos.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibroTest {

    @Test
    void testCrearLibroValido() {
        // Arrange
        Libro libro = new Libro("978-3-16-148410-0", "Hunger Games", "Suzanne Collins");

        // Assert
        assertEquals("978-3-16-148410-0", libro.getISBN());
        assertEquals("Hunger Games", libro.getTitulo());
        assertEquals("Suzanne Collins", libro.getAutor());
        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado());
    }

    @Test
    void testCambioEstadoLibro() {
        // Arrange
        Libro libro = new Libro("123-4-56-789101-1", "Heartless", "Marissa Meyer");

        // Act & Assert
        libro.setEstado(EstadoLibro.PRESTADO);
        assertEquals(EstadoLibro.PRESTADO, libro.getEstado());

        libro.setEstado(EstadoLibro.DISPONIBLE);
        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado());
    }
}
