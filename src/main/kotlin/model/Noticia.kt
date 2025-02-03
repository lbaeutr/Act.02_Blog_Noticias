package org.example.model


import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.Date
import java.util.Objects

data class Noticia(
    @BsonId
    val id: ObjectId? = null, // MongoDB genera automaticamente un _id
    val titulo: String,
    val cuerpo: String,
    val fechaPub: Date = Date(), // Fecha establecidada por defecto y no editable
    val tag: List<String> = listOf(),
    val user: String // Email del usuario que publico la noticia
)