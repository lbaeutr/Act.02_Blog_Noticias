package org.example.utils

import org.example.model.Cliente
import org.example.model.Comentario
import org.example.model.Direccion
import org.example.model.Noticia
import org.example.repository.ClienteRepository
import org.example.service.ClienteService
import org.example.service.ComentarioService
import org.example.service.NoticiaService
import java.util.Date


class MenuUtils(
    private val clienteService: ClienteService,
    private val noticiaService: NoticiaService,
    private val comentarioService: ComentarioService,
    private val clienteRepository: ClienteRepository
) {

    fun registrarUsuario() {

        println("\nRegistrando un nuevo usuario...")

        val email = pedirEntrada("Email: ")
         val nombre = pedirEntrada("Nombre Completo: ")
        val username = pedirEntrada("Username: ")
        val calle = pedirEntrada("Calle: ")
        val numero = pedirEntrada("Número: ")
        val puerta = pedirEntrada("Puerta: ")
        val codigoPostal = pedirEntrada("Código Postal: ")
        val ciudad = pedirEntrada("Ciudad: ")
        val telefonos = pedirEntrada("Teléfono (separado por comas si hay más de uno): ")
            .split(",").map { it.trim() }

        val cliente = Cliente(
            email = email,
            nombreCompleto = nombre,
            username = username,
            banned = false,
            activo = true,
            direccion = Direccion(calle, numero, puerta, codigoPostal, ciudad),
            telefonos = telefonos
        )

        if (clienteService.registroUsuario(cliente)) {
            println("Usuario registrado correctamente.")
        } else {
            println("Error al registrar el usuario.")
        }
    }

    fun publicarNoticia() {
        println("\nPublicando una nueva noticia...")

        val user = pedirEntrada("Nick del usuario: ")
        val titulo = pedirEntrada("Título de la noticia: ")
        val cuerpo = pedirEntrada("Cuerpo de la noticia: ")
        val tags = pedirEntrada("Etiquetas (separadas por comas): ").split(",").map { it.trim() }

        val noticia = Noticia(
            titulo = titulo,
            cuerpo = cuerpo,
            fechaPub = Date(),
            tag = tags,
            user = user
        )

        if (noticiaService.publicarNoticia(noticia)) {
            println("Noticia publicada correctamente.")
        } else {
            println("No se pudo publicar la noticia.")
        }
    }

    //todo tenemos que cambir el email x por el nick
    fun listarNoticiasPorUsuario() {
        val user = pedirEntrada("\nNick del usuario: ")
        val noticias = noticiaService.listarNoticiasDeUsuario(user)
        if (noticias.isNotEmpty()) {
            println("\nNoticias publicadas por $user:")
            noticias.forEach { println("- ${it.titulo}") }
        } else {
            println("No se encontraron noticias para este usuario.")
        }
    }

    fun buscarNoticiasPorEtiqueta() {
        val etiqueta = pedirEntrada("\nEtiqueta a buscar: ")
        val noticias = noticiaService.buscarNoticiasPorEtiqueta(etiqueta)
        if (noticias.isNotEmpty()) {
            println("\nNoticias con etiqueta '$etiqueta':")
            noticias.forEach { println("- ${it.titulo}") }
        } else {
            println("No se encontraron noticias con esta etiqueta.")
        }
    }

    fun listarUltimasNoticias() {
        println("\nÚltimas 10 noticias publicadas:")
        val noticias = noticiaService.listarUltimasNoticias()
        if (noticias.isNotEmpty()) {
            noticias.forEach { println("- ${it.titulo} (Fecha: ${it.fechaPub})") }
        } else {
            println("No hay noticias publicadas aún.")
        }
    }

    fun agregarComentario() {
        println("\nAgregando un comentario...")

        val user = pedirEntrada("Nick del usuario: ")
        val tituloNoticia = pedirEntrada("Título de la noticia: ")
        val texto = pedirEntrada("Comentario: ")

        val comentario = Comentario(
            tituloNoticia = tituloNoticia,
            user = user,
            texto = texto
        )

        if (comentarioService.agregarComentario(comentario)) {
            println("Comentario agregado correctamente.")
        } else {
            println("No se pudo agregar el comentario.")
        }
    }

    fun listarComentariosPorNoticia() {
        val tituloNoticia = pedirEntrada("\nTítulo de la noticia: ")
        val comentarios = comentarioService.listarComentariosPorNoticia(tituloNoticia)
        if (comentarios.isNotEmpty()) {
            println("\nComentarios en '$tituloNoticia':")
            comentarios.forEach { println("- ${it.texto} (de ${it.user})") }
        } else {
            println("No hay comentarios en esta noticia.")
        }
    }

    // Función auxiliar para  pedir al usuario una entrada de texto
    private fun pedirEntrada(mensaje: String): String {
        print(mensaje)
        return readLine()?.trim()?.lowercase()?.replace("\\s+".toRegex(), " ") ?: ""
    }
}