package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase de datos para almacenar cada registro de saludo
 */
data class GreetingLog(val name: String, val timestamp: String)

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    // Lista mutable para guardar el historial en memoria. 
    private val history = mutableListOf<GreetingLog>()

    /**
     * Maneja la petición de la página principal.
     * Genera el saludo, lo guarda en el historial y devuelve la vista web.
     */
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val greeting = if (name.isNotBlank()) "Hello, $name!" else message
        
        val visitorName = if (name.isNotBlank()) name else "Anónimo"

        // Añadimos el registro solo si hay un nombre explícito
        if (name.isNotBlank()) {
            val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            history.add(GreetingLog(name, currentTime)) // Añadimos a la lista
        }

        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        model.addAttribute("logs", history) // Enviamos la lista entera

        return "welcome"
    }

    /**
     * Endpoint para devolver el historial completo en formato JSON
     */
    @GetMapping("/api/logs")
    @ResponseBody // Ignora el HTML y devuelve lista
    fun getLogs(): List<GreetingLog> {
        return history
    }

}

@RestController
class HelloApiController {
     
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        return mapOf(
            "message" to "Hello, $name!",
            "timestamp" to java.time.Instant.now().toString()
        )
    }
}
