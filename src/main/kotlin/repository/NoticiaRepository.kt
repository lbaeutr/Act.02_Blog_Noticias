package org.example.repository

import com.mongodb.client.MongoCollection
import org.example.config.ConexionMongo
import org.example.model.Noticia
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Sorts


class NoticiaRepository {
    private val collection: MongoCollection<Noticia> = ConexionMongo
        .getDatabase()
        .getCollection("collNoticias", Noticia::class.java)

    /**
     * Publicar una noticia en la base de datos.
     * La fecha de publicación debe ser única.
     */
    fun publicarNoticia(noticia: Noticia): Boolean {

        collection.insertOne(noticia)
        println("Noticia publicada correctamente: ${noticia.titulo}")
        return true
    }

    /**
     * Listar todas las noticias publicadas por un usuario.
     */

    // Todo cambiar esto por nick
    fun listarNoticiasPorUsuario(nick: String): List<Noticia> {
        return collection.find(eq("user", nick)).toList()
    }

    /**
     * Buscar noticias por etiquetas.
     */
    fun buscarNoticiasPorEtiqueta(etiqueta: String): List<Noticia> {
        return collection.find(eq("tag", etiqueta)).toList()
    }

    /**
     * Listar las 10 últimas noticias publicadas.
     */
    fun listarUltimasNoticias(): List<Noticia> {
        return collection.find()
            .sort(Sorts.descending("fechaPub"))
            .limit(10)
            .toList()
    }

    fun existeNoticia(titulo: String): Boolean {
        return collection.countDocuments(eq("titulo", titulo)) > 0
    }
}