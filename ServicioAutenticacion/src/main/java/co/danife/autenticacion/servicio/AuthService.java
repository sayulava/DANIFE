
package co.danife.autenticacion.servicio;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Servicio encargado de registrar usuarios y validar sus credenciales.
 * Esta versión usa memoria temporal para las pruebas del servicio.
 */
@Service
public class AuthService {

    // Almacena temporalmente usuarios registrados durante la ejecución.
    private final Map<String, String> usuarios = new ConcurrentHashMap<>();

    /**
     * Registra un usuario si el nombre no existe.
     */
    public boolean registrar(String usuario, String clave) {
        return usuarios.putIfAbsent(usuario, clave) == null;
    }

    /**
     * Comprueba si el usuario y la clave coinciden.
     */
    public boolean autenticar(String usuario, String clave) {
        String claveGuardada = usuarios.get(usuario);
        return claveGuardada != null && claveGuardada.equals(clave);
    }
}
