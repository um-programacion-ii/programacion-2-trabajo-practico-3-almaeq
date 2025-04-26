package org.example;

import org.example.enums.EstadoLibro;
import org.example.exceptions.LibroNoDisponibleException;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;
import org.example.sistemas.SistemaPrestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SistemaPrestamoTest {

    @Mock
    private Catalogo catalogoMock;

    @InjectMocks
    private SistemaPrestamo sistemaPrestamo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrestarLibroExitosamente() {
        // Arrange
        Libro libroDisponible = new Libro("111", "Renegados", "Marissa Meyer");
        libroDisponible.setEstado(EstadoLibro.DISPONIBLE);

        when(catalogoMock.buscarPorISBN("111")).thenReturn(libroDisponible);

        // Act
        Prestamo resultado = sistemaPrestamo.prestarLibro("111");

        // Assert
        assertNotNull(resultado); // Ahora verificamos que el préstamo exista
        assertEquals("Renegados", resultado.getLibro().getTitulo());
        assertEquals(EstadoLibro.PRESTADO, resultado.getLibro().getEstado());

        Prestamo prestamoBuscado = sistemaPrestamo.buscarPrestamoPorISBN("111");
        assertNotNull(prestamoBuscado);
        assertEquals("Renegados", prestamoBuscado.getLibro().getTitulo());

        verify(catalogoMock, times(1)).buscarPorISBN("111");
    }

    @Test
    void testPrestarLibroInexistenteLanzaExcepcion() {
        // Arrange
        when(catalogoMock.buscarPorISBN("999")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> sistemaPrestamo.prestarLibro("999")
        );

        assertEquals("El libro con ISBN 999 no existe.", thrown.getMessage());

        verify(catalogoMock, times(1)).buscarPorISBN("999");
    }

    @Test
    void testPrestarLibroNoDisponibleLanzaExcepcion() {
        // Arrange
        Libro libroPrestado = new Libro("222", "El campamento", "Blue Jeans");
        libroPrestado.setEstado(EstadoLibro.PRESTADO);

        when(catalogoMock.buscarPorISBN("222")).thenReturn(libroPrestado);

        // Act & Assert
        LibroNoDisponibleException thrown = assertThrows(
                LibroNoDisponibleException.class,
                () -> sistemaPrestamo.prestarLibro("222")
        );

        assertEquals("El libro no está disponible para préstamo.", thrown.getMessage());

        verify(catalogoMock, times(1)).buscarPorISBN("222");
    }
}
