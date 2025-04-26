package org.example.modelos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Prestamo {
    private  Libro libro;
    private LocalDate fecha;

    public Prestamo(Libro libro) {
        this.libro = libro;
        this.fecha = LocalDate.now();
    }
}
