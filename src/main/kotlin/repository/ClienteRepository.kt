package org.example.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.example.config.ConexionMongo
import org.example.model.Cliente
import kotlin.compareTo

class ClienteRepository {
    private val collection: MongoCollection<Cliente> = ConexionMongo
        .getDatabase()
        .getCollection("collUsuarios", Cliente::class.java)

    /**
     * Registra un nuevo usuario en la base de datos.
     * Retorna true si el usuario se guardó correctamente, false si ya existe.
     */
    fun registrarCliente(cliente: Cliente): Boolean {
        // Verificar que el email (ID) sea único
        if (collection.countDocuments(eq("_id", cliente.email)) > 0) {
            println("Error: El cliente con email ${cliente.email} ya existe.")
            return false
        }

        // Comprobar que el username sea único
        if (collection.countDocuments(eq("username", cliente.username)) > 0) {
            println("Error: El username ${cliente.username} ya existe.")
            return false
        }

        // Insertar el cliente en la base de datos
        collection.insertOne(cliente)
        println("Cliente registrado correctamente: ${cliente.email}")
        return true
    }

    /**
     * Busca un usuario por su email.
     */
    fun buscarClientePorEmail(email: String): Cliente? {
        return collection.find(eq("_id", email)).firstOrNull()
    }

    /**
     * Verifica si un usuario está activo (no inactivo ni baneado).
     */
    fun esUsuarioActivo(username: String): Boolean {
        val cliente = collection.find(eq("username", username)).firstOrNull()
        return cliente?.activo == true && cliente.banned == false
    }

}