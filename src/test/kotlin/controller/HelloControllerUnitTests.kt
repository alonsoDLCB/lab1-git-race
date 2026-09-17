package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        controller = HelloController("Test Message")
        model = ExtendedModelMap()
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Test Message")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController()
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hello, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }

    @Test
    fun `should add greeting to history only when name is provided`() {
        // Simulamos una visita pasándole un nombre explícito
        val view = controller.welcome(model, "Prueba")
        
        // Verificamos que la función devuelve el texto de welcome (nombre de la plantilla HTML)
        assertThat(view).isEqualTo("welcome")
        
        // Recuperamos la lista de logs que el controlador ha metido en el modelo
        @Suppress("UNCHECKED_CAST")
        val logs = model.getAttribute("logs") as List<GreetingLog>
        
        // Comprobamos que hay exactamente 1 registro guardado y coincide
        assertThat(logs).hasSize(1)
        assertThat(logs[0].name).isEqualTo("Profesor")
        assertThat(logs[0].timestamp).isNotBlank()
    }
}
