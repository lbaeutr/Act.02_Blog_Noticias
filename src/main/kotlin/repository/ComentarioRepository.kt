package org.example.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.example.config.ConexionMongo
import org.example.model.Comentario


class ComentarioRepository {
    private val collection: MongoCollection<Comentario> = ConexionMongo
        .getDatabase()
        .getCollection("collComentarios", Comentario::class.java)

    /**
     * Agregar un nuevo comentario a la base de datos.
     * Los comentarios son inmutables (no se pueden editar después).
     */
    fun agregarComentario(comentario: Comentario): Boolean {
        collection.insertOne(comentario)
        println("Comentario agregado a la noticia '${comentario.tituloNoticia}' por ${comentario.user}")
        return true
    }

    /**
     * Listar comentarios de una noticia por su título.
     */
    fun listarComentariosPorNoticia(tituloNoticia: String): List<Comentario> {
        return collection.find(eq("tituloNoticia", tituloNoticia)).toList()
    }
}