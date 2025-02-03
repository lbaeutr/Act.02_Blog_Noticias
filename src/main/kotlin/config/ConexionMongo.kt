package org.example.config


import com.mongodb.KotlinCodecProvider
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.example.config.ConexionMongo.getDatabase

//
//object ConexionMongo {
//    private val mongoClient: MongoClient by lazy {
//        val dotenv = dotenv()
//        val connectString = dotenv["URL_MONGODB"]
//
//        MongoClients.create(connectString)
//    }
//
//    fun getDatabase(bd: String): MongoDatabase {
//        return mongoClient.getDatabase(bd)
//    }
//
//    fun close() {
//        mongoClient.close()
//    }
//
//    fun testConnection() {
//        try {
//            val stats = getDatabase("URL_MONGODB").runCommand(Document("ping", 1))
//            println("✅ Conexión exitosa a MongoDB: $stats")
//        } catch (e: Exception) {
//            println("❌ Error conectando a MongoDB: ${e.message}")
//        }
//    }
//
//}

//object ConexionMongo {
//    private val dotenv = dotenv()
//
//    private val settings = MongoClientSettings.builder()
//        .codecRegistry(
//            CodecRegistries.fromRegistries(
//                CodecRegistries.fromProviders(KotlinCodecProvider()), // Codec para Kotlin
//                MongoClientSettings.getDefaultCodecRegistry()
//            )
//        )
//        .applyConnectionString(
//            com.mongodb.ConnectionString(dotenv["URL_MONGODB"])
//        )
//        .build()
//
//    private val client: MongoClient = MongoClients.create(settings)
//
//    fun getDatabase(databaseName: String) = client.getDatabase(databaseName)
//
//
//    fun close() {
//        client.close()
//    }
//
//
//    fun testConnection() {
//        try {
//            val stats = getDatabase("URL_MONGODB").runCommand(Document("ping", 1))
//            println("✅ Conexión exitosa a MongoDB: $stats")
//        } catch (e: Exception) {
//            println("❌ Error conectando a MongoDB: ${e.message}")
//        }
//    }
//}



object ConexionMongo {
    private val dotenv = dotenv()

    private val mongoUri: String = dotenv["URL_MONGODB"]
        ?: throw RuntimeException("❌ No se encontró la variable de entorno URL_MONGODB en .env")

    private const val DATABASE_NAME = "dbada" // Ajustado al nombre de tu base de datos

    private val settings: MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(com.mongodb.ConnectionString(mongoUri))
        .codecRegistry(
            CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
                CodecRegistries.fromProviders(KotlinCodecProvider()) // Soporte para data classes
            )
        )
        .build()

    private val client: MongoClient = MongoClients.create(settings)

    fun getDatabase(): MongoDatabase {
        return client.getDatabase(DATABASE_NAME)
    }

    fun close() {
        client.close()
    }

    fun testConnection() {
        try {
            val stats = getDatabase().runCommand(Document("ping", 1))
            println("✅ Conexión exitosa a MongoDB en la base de datos '$DATABASE_NAME': $stats")
        } catch (e: Exception) {
            println("❌ Error conectando a MongoDB: ${e.message}")
        }
    }
}
