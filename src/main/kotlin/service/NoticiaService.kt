package org.example.service

import org.example.model.Noticia
import org.example.repository.ClienteRepository
import org.example.repository.NoticiaRepository


class NoticiaService(
    internal val noticiaRepository: NoticiaRepository,
    private val clienteRepository: ClienteRepository
) {

    /**
     * Publicar una noticia, verificando si el usuario está activo y si el titulo y el cuerpo no estan en blanco.
     *
     */
    fun publicarNoticia(noticia: Noticia): Boolean {

        // verificar si el usuario pue

        // Verificar si ya existe una noticia con el mismo titulo
        if (noticiaRepository.existeNoticia(noticia.titulo)) {
            println("Error: Ya existe una noticia con el mismo título ${noticia.titulo}.")
            return false
        }

        // Verificar si el usuario tiene permisos para publicar
        if (!clienteRepository.esUsuarioActivo(noticia.user)) {
            println("Error: El usuario ${noticia.user} no puede publicar noticias (baneado o inactivo).")
            return false
        }

        // Verificar si el titulo y el cuerpo no estan en blanco
        if (noticia.titulo.isBlank() || noticia.cuerpo.isBlank()) {
            println("Error: El título y el cuerpo de la noticia no pueden estar vacíos.")
            return false
        }

        return noticiaRepository.publicarNoticia(noticia)
    }

    /**
     * Buscar noticias por usuario.
     */

    //todo tenemos que cambiar esto para que busque por nick
    fun listarNoticiasDeUsuario(nick: String): List<Noticia> {
        return noticiaRepository.listarNoticiasPorUsuario(nick)
    }

    /**
     * Buscar noticias por etiqueta.
     */
    fun buscarNoticiasPorEtiqueta(etiqueta: String): List<Noticia> {
        return noticiaRepository.buscarNoticiasPorEtiqueta(etiqueta)
    }

    /**
     * Obtener las últimas 10 noticias publicadas.
     */
    fun listarUltimasNoticias(): List<Noticia> {
        return noticiaRepository.listarUltimasNoticias()
    }
}