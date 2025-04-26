package org.example.sistemas;

import org.example.modelos.Prestamo;
import org.example.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestionUsuario {

    private List<Usuario> usuarios = new ArrayList<>();

    // Registrar un nuevo usuario
    public void registrarUsuario(String nombre) {
        usuarios.add(new Usuario(nombre));
    }

    // Buscar usuario por nombre usando Stream
    public Usuario buscarUsuario(String nombre) {
        return usuarios.stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // Registrar un préstamo para un usuario
    public boolean registrarPrestamo(String nombreUsuario, Prestamo prestamo) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario != null) {
            usuario.agregarPrestamo(prestamo);
            return true;
        }
        return false; // No se encontró el usuario
    }
}
