package org.example;

import org.example.enums.EstadoLibro;
import org.example.exceptions.UsuarioNoEncontradoException;
import org.example.sistemas.GestionUsuario;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;
import org.example.modelos.Usuario;
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
    private SistemaPrestamo sistemaPrestamosMock;

    @InjectMocks
    private GestionUsuario gestionUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrestarLibroYRegistrarPrestamoAUsuario() {
        // Arrange
        gestionUsuario.registrarUsuario("Daniel");

        Libro libro = new Libro("001", "Clean Architecture", "Robert C. Martin");
        libro.setEstado(EstadoLibro.DISPONIBLE);

        Prestamo prestamo = new Prestamo(libro);

        when(sistemaPrestamosMock.prestarLibro("001")).thenReturn(true);
        when(sistemaPrestamosMock.buscarPrestamoPorISBN("001")).thenReturn(prestamo);

        // Act
        boolean prestamoExitoso = sistemaPrestamosMock.prestarLibro("001");
        Prestamo prestamoRegistrado = sistemaPrestamosMock.buscarPrestamoPorISBN("001");

        boolean registradoEnUsuario = gestionUsuario.registrarPrestamo("Daniel", prestamoRegistrado);

        // Assert
        assertTrue(prestamoExitoso);
        assertTrue(registradoEnUsuario);

        Usuario usuario = gestionUsuario.buscarUsuario("Daniel");

        assertNotNull(usuario);
        assertEquals(1, usuario.getHistorialPrestamos().size());
        assertEquals("Clean Architecture", usuario.getHistorialPrestamos().get(0).getLibro().getTitulo());

        verify(sistemaPrestamosMock, times(1)).prestarLibro("001");
        verify(sistemaPrestamosMock, times(1)).buscarPrestamoPorISBN("001");
    }

    @Test
    void testRegistrarPrestamoUsuarioNoExistenteLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro("002", "Refactoring", "Martin Fowler");
        Prestamo prestamo = new Prestamo(libro);

        // Act & Assert
        UsuarioNoEncontradoException thrown = assertThrows(
                UsuarioNoEncontradoException.class,
                () -> gestionUsuario.registrarPrestamo("UsuarioInexistente", prestamo)
        );

        assertEquals("Usuario no encontrado: UsuarioInexistente", thrown.getMessage());
    }

    @Test
    void testRegistrarPrestamoConNombreNuloLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro("003", "The Pragmatic Programmer", "Andrew Hunt");
        Prestamo prestamo = new Prestamo(libro);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> gestionUsuario.registrarPrestamo(null, prestamo)
        );

        assertEquals("Nombre de usuario o préstamo no pueden ser nulos.", thrown.getMessage());
    }

    @Test
    void testRegistrarPrestamoConPrestamoNuloLanzaExcepcion() {
        // Arrange
        gestionUsuario.registrarUsuario("Lucia");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> gestionUsuario.registrarPrestamo("Lucia", null)
        );

        assertEquals("Nombre de usuario o préstamo no pueden ser nulos.", thrown.getMessage());
    }
}
