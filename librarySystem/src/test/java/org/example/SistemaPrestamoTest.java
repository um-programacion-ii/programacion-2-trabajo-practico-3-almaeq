package org.example;

import org.example.enums.EstadoLibro;
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

class SistemaPrestamosTest {

    @Mock
    private Catalogo catalogoMock;

    @InjectMocks
    private SistemaPrestamo sistemaPrestamo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPrestamoExitoso() {
        Libro libroDisponible = new Libro("111", "Renegados", "Marissa Meyer");
        libroDisponible.setEstado(EstadoLibro.DISPONIBLE);

        when(catalogoMock.buscarPorISBN("111")).thenReturn(libroDisponible);

        boolean resultado = sistemaPrestamo.registrarPrestamo("111");

        assertTrue(resultado);
        assertEquals(EstadoLibro.PRESTADO, libroDisponible.getEstado());

        Prestamo prestamo = sistemaPrestamo.buscarPrestamoPorISBN("111");
        assertNotNull(prestamo);
        assertEquals("Renegados", prestamo.getLibro().getTitulo());

        verify(catalogoMock, times(1)).buscarPorISBN("111");
    }

    @Test
    void testRegistrarPrestamoLibroInexistente() {
        when(catalogoMock.buscarPorISBN("999")).thenReturn(null);

        boolean resultado = sistemaPrestamo.registrarPrestamo("999");

        assertFalse(resultado);

        Prestamo prestamo = sistemaPrestamo.buscarPrestamoPorISBN("999");
        assertNull(prestamo);

        verify(catalogoMock, times(1)).buscarPorISBN("999");
    }

    @Test
    void testRegistrarPrestamoLibroNoDisponible() {
        Libro libroPrestado = new Libro("222", "El campamento", "Blue Jeans");
        libroPrestado.setEstado(EstadoLibro.PRESTADO);

        when(catalogoMock.buscarPorISBN("222")).thenReturn(libroPrestado);

        boolean resultado = sistemaPrestamo.registrarPrestamo("222");

        assertFalse(resultado);

        Prestamo prestamo = sistemaPrestamo.buscarPrestamoPorISBN("222");
        assertNull(prestamo);

        verify(catalogoMock, times(1)).buscarPorISBN("222");
    }
}
