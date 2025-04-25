package org.example;

import org.example.enums.EstadoLibro;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {

    @Test
    void testAgregarLibro() {
        Catalogo catalogo = new Catalogo();
        Libro libro = new Libro("111", "Clean Architecture", "Robert C. Martin");

        catalogo.agregarLibro(libro);

        assertEquals(libro, catalogo.buscarPorISBN("111"));
    }

    @Test
    void testBuscarPorISBNExistente() {
        Catalogo catalogo = new Catalogo();
        Libro libro1 = new Libro("222", "Refactoring", "Martin Fowler");
        Libro libro2 = new Libro("333", "Design Patterns", "GoF");

        catalogo.agregarLibro(libro1);
        catalogo.agregarLibro(libro2);

        Libro encontrado = catalogo.buscarPorISBN("333");
        assertNotNull(encontrado);
        assertEquals("Design Patterns", encontrado.getTitulo());
    }

    @Test
    void testObtenerLibrosDisponibles() {
        Catalogo catalogo = new Catalogo();

        Libro disponible1 = new Libro("444", "Domain-Driven Design", "Eric Evans");
        Libro disponible2 = new Libro("555", "The Pragmatic Programmer", "Andy Hunt");

        Libro prestado = new Libro("666", "The Mythical Man-Month", "Fred Brooks");
        prestado.setEstado(EstadoLibro.PRESTADO);

        catalogo.agregarLibro(disponible1);
        catalogo.agregarLibro(disponible2);
        catalogo.agregarLibro(prestado);

        List<Libro> disponibles = catalogo.obtenerLibrosDisponibles();

        assertEquals(2, disponibles.size());
        assertTrue(disponibles.contains(disponible1));
        assertTrue(disponibles.contains(disponible2));
        assertFalse(disponibles.contains(prestado));
    }
}
