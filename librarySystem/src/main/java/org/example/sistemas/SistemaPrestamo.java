package org.example.sistemas;

import org.example.enums.EstadoLibro;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class SistemaPrestamo {

    private Catalogo catalogo;
    private List<Prestamo> prestamos = new ArrayList<>();

    public SistemaPrestamo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public boolean prestarLibro(String isbn) {
        Libro libro = catalogo.buscarPorISBN(isbn);
        if (libro != null && libro.getEstado() == EstadoLibro.DISPONIBLE) {
            libro.setEstado(EstadoLibro.PRESTADO);
            Prestamo prestamo = new Prestamo(libro);
            prestamos.add(prestamo);
            return true;
        }
        return false;
    }

    // Obtener todos los préstamos
    public List<Prestamo> obtenerPrestamos() {
        return new ArrayList<>(prestamos); // Devolver una copia para proteger la lista interna
    }

    // Buscar un préstamo por ISBN
    public Prestamo buscarPrestamoPorISBN(String isbn) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getISBN().equals(isbn)) {
                return prestamo;
            }
        }
        return null; // No encontrado
    }
}
