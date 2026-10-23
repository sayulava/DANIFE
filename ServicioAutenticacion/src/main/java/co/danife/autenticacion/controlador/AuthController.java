
package co.danife.autenticacion.controlador;

import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.danife.autenticacion.servicio.AuthService;

/** Controlador REST para autenticación de DANIFE. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record Credenciales(
            @NotBlank String usuario,
            @NotBlank String clave) {
    }

    // Registra un usuario nuevo.
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(
            @Valid @RequestBody Credenciales datos) {

        boolean registrado = authService.registrar(
                datos.usuario().trim(), datos.clave());

        if (!registrado) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El usuario ya existe"));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado correctamente"));
    }

    // Valida las credenciales para iniciar sesión.
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody Credenciales datos) {

        boolean valido = authService.autenticar(
                datos.usuario().trim(), datos.clave());

        if (!valido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Usuario o clave incorrectos"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Autenticación exitosa"));
    }
}
