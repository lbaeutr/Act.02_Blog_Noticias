package org.example.model


import org.bson.codecs.pojo.annotations.BsonId

data class Cliente(
    @BsonId
    val email: String,  // ID unico en Mongo
    val nombreCompleto: String,
    val username: String,
    val banned: Boolean = false,
    val activo: Boolean = true,
    val direccion: Direccion, // Dirección instanciada con otra data class que contiene la dirección y todos sus atributos
    val telefonos: List<String>
)

