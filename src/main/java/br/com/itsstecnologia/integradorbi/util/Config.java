package br.com.itsstecnologia.integradorbi.util;

import br.com.itsstecnologia.integradorbi.config.params.Parametros;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private static Parametros parametros = new Parametros();

    public static Parametros getParametros() {
        return parametros;
    }

}
