package org.example.service

import org.example.model.Cliente
import org.example.repository.ClienteRepository


class ClienteService(internal val clienteRepository: ClienteRepository) {

    /**
     * Intenta registrar un nuevo usuario con validaciones.
     */
    fun registroUsuario(cliente: Cliente): Boolean {
        if (cliente.email.isBlank() || cliente.username.isBlank()) {
            println("Error: El email y el username no pueden estar vacíos")
            return false
        }


        if (!cliente.email.contains("@") || !cliente.email.contains(".")) {
            println("Error: El email '${cliente.email}' no es válido.")
            return false
        }

        if (cliente.username.length < 3) {
            println("Error: El username '${cliente.username}' es demasiado corto.")
            return false
        }
        return clienteRepository.registrarCliente(cliente)
    }

    /**
     * Verifica si un usuario tiene permitido publicar noticias.
     */
    fun puedePublicarNoticias(email: String): Boolean {
        return clienteRepository.esUsuarioActivo(email)
    }

    /**
     * Verifica si un usuario puede escribir comentarios.
     */
    fun puedeComentar(email: String): Boolean {
        return clienteRepository.esUsuarioActivo(email)
    }


}