package org.example.service

import org.example.model.Comentario
import org.example.repository.ClienteRepository
import org.example.repository.ComentarioRepository
import org.example.repository.NoticiaRepository


class ComentarioService(
    private val comentarioRepository: ComentarioRepository,
    private val clienteRepository: ClienteRepository,
    private val noticiaRepository: NoticiaRepository
) {

    /**
     * Agregar un comentario si el usuario está activo.
     */
    fun agregarComentario(comentario: Comentario): Boolean {
        if (!clienteRepository.esUsuarioActivo(comentario.user)) {
            println("Error: El usuario ${comentario.user} no puede comentar estado (baneado).")
            return false
        }


        if (comentario.texto.isBlank()) {
            println("Error: El comentario no puede estar vacío.")
            return false
        }

        if (!noticiaRepository.existeNoticia(comentario.tituloNoticia)) {
            println("Error: La noticia '${comentario.tituloNoticia}' no existe. No se puede comentar.")
            return false
        }

        return comentarioRepository.agregarComentario(comentario)
    }

    /**
     * Obtener los comentarios de una noticia por título.
     */
    fun listarComentariosPorNoticia(tituloNoticia: String): List<Comentario> {
        return comentarioRepository.listarComentariosPorNoticia(tituloNoticia)
    }
}