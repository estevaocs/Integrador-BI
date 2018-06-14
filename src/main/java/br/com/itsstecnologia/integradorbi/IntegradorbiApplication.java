package br.com.itsstecnologia.integradorbi;

import br.com.itsstecnologia.integradorbi.repository.DemandaRepository;
import br.com.itsstecnologia.integradorbi.service.read.ReadExcel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IntegradorbiApplication {

    private static ReadExcel rdxls;

    public static void main(String[] args) {
        SpringApplication.run(IntegradorbiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(DemandaRepository repository) {
        return args -> {
            initRead(repository);
        };

    }

    private void initRead(DemandaRepository repository) {
        rdxls = new ReadExcel();
        rdxls.read(repository);
    }
}
