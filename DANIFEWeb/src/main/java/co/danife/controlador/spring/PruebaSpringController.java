
package co.danife.controlador.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PruebaSpringController {

    @GetMapping("/prueba")
    @ResponseBody
    public String prueba() {
        return "Spring MVC funciona correctamente en DANIFEWeb";
    }
}