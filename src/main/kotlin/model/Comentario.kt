package org.example.model


import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.Date

data class Comentario(
    @BsonId
    val id: ObjectId? = null, // MongoDB genera automáticamente un _id
    val tituloNoticia: String, // Relacionamos con el título en vez del ID
    val user: String, // Email del usuario que escribió el comentario
    val texto: String, // Contenido del comentario
    val fechaPub: Date = Date() // Fecha automática
)