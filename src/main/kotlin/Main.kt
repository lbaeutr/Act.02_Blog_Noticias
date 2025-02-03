package org.example


import org.example.config.ConexionMongo
import org.example.repository.ClienteRepository
import org.example.repository.ComentarioRepository
import org.example.repository.NoticiaRepository
import org.example.service.ClienteService
import org.example.service.ComentarioService
import org.example.service.NoticiaService
import org.example.utils.MenuUtils
fun main() {
    val clienteService = ClienteService(ClienteRepository())
    val noticiaService = NoticiaService(NoticiaRepository(), clienteService.clienteRepository)
    val comentarioService = ComentarioService(ComentarioRepository(), clienteService.clienteRepository , noticiaService.noticiaRepository)
    val clienteRepository = ClienteRepository()

    val menuUtils = MenuUtils(clienteService, noticiaService, comentarioService, clienteRepository)

    while (true) {
        println("\n=====  MENÚ PRINCIPAL  =====")
        println("1 - Registrar un usuario")
        println("2 - Publicar una noticia")
        println("3 - Listar noticias de un usuario")
        println("4 - Buscar noticias por etiqueta")
        println("5 - Listar las 10 últimas noticias")
        println("6 - Agregar un comentario")
        println("7 - Listar comentarios de una noticia")
        println("0 - Salir")
        print("👉 Selecciona una opción: ")

        val opcion = readLine()?.toIntOrNull() ?: -1

        when (opcion) {
            1 -> menuUtils.registrarUsuario()
            2 -> menuUtils.publicarNoticia()
            3 -> menuUtils.listarNoticiasPorUsuario()
            4 -> menuUtils.buscarNoticiasPorEtiqueta()
            5 -> menuUtils.listarUltimasNoticias()
            6 -> menuUtils.agregarComentario()
            7 -> menuUtils.listarComentariosPorNoticia()
            0 -> {
                println("👋 Saliendo del programa...")
                return
            }
            else -> println(" Opción no válida, intenta de nuevo.")


        }
    }

    // Cerrar la conexión con MongoDB
    ConexionMongo.close()
}

