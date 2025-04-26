package org.example;

import org.example.enums.EstadoLibro;
import org.example.exceptions.UsuarioNoEncontradoException;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;
import org.example.modelos.Usuario;
import org.example.modelos.Catalogo;
import org.example.sistemas.GestionUsuario;
import org.example.sistemas.SistemaPrestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioTest {

    @Mock
    private Catalogo catalogo;

    @Mock
    private SistemaPrestamo sistemaPrestamos;

    @InjectMocks
    private GestionUsuario gestionUsuarios;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPrestamoExitoso() {
        // Arrange
        gestionUsuarios.registrarUsuario("usuario1");

        Usuario usuario = gestionUsuarios.buscarUsuario("usuario1");

        Libro libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        libro.setEstado(EstadoLibro.DISPONIBLE);

        when(catalogo.buscarPorISBN("978-3-16-148410-0")).thenReturn(libro);

        Prestamo prestamo = new Prestamo(libro);
        when(sistemaPrestamos.prestarLibro("978-3-16-148410-0")).thenReturn(prestamo);

        // Act
        Prestamo prestamoObtenido = sistemaPrestamos.prestarLibro("978-3-16-148410-0"); // Prestar
        gestionUsuarios.registrarPrestamo("usuario1", prestamoObtenido);

        // Assert
        verify(sistemaPrestamos).prestarLibro("978-3-16-148410-0");
        assertEquals(1, usuario.getHistorialPrestamos().size());
        assertEquals("Clean Code", usuario.getHistorialPrestamos().get(0).getLibro().getTitulo());
    }

    @Test
    void testRegistrarPrestamoUsuarioNoExistenteLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro("002", "Refactoring", "Martin Fowler");
        libro.setEstado(EstadoLibro.DISPONIBLE);

        when(catalogo.buscarPorISBN("002")).thenReturn(libro);

        Prestamo prestamo = new Prestamo(libro);
        when(sistemaPrestamos.prestarLibro("002")).thenReturn(prestamo);

        // Act & Assert
        Prestamo prestamoObtenido = sistemaPrestamos.prestarLibro("002");

        UsuarioNoEncontradoException thrown = assertThrows(
                UsuarioNoEncontradoException.class,
                () -> gestionUsuarios.registrarPrestamo("UsuarioInexistente", prestamoObtenido)
        );

        assertEquals("Usuario no encontrado: UsuarioInexistente", thrown.getMessage());
    }

    @Test
    void testRegistrarPrestamoConNombreNuloLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro("003", "The Pragmatic Programmer", "Andrew Hunt");

        when(catalogo.buscarPorISBN("003")).thenReturn(libro);
        when(sistemaPrestamos.prestarLibro("003")).thenReturn(new Prestamo(libro));

        Prestamo prestamoObtenido = sistemaPrestamos.prestarLibro("003");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> gestionUsuarios.registrarPrestamo(null, prestamoObtenido)
        );

        assertEquals("Nombre de usuario o préstamo no pueden ser nulos.", thrown.getMessage());
    }

    @Test
    void testRegistrarPrestamoConIsbnNuloLanzaExcepcion() {
        // Arrange
        gestionUsuarios.registrarUsuario("Lucia");

        Usuario usuario = gestionUsuarios.buscarUsuario("Lucia");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> gestionUsuarios.registrarPrestamo("Lucia", null)
        );

        assertEquals("Nombre de usuario o préstamo no pueden ser nulos.", thrown.getMessage());
    }
}
