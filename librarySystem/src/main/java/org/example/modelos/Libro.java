package org.example.modelos;

import lombok.Data;
import org.example.enums.EstadoLibro;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Libro {
    private String ISBN;
    private String titulo;
    private String autor;
    private EstadoLibro estado = EstadoLibro.DISPONIBLE;

    public Libro(String ISBN, String titulo, String autor) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
    }

}

