package br.com.itsstecnologia.integradorbi.service.persist;


import br.com.itsstecnologia.integradorbi.entity.Demanda;
import org.springframework.stereotype.Component;

@Component
public interface DemandaService {

    Demanda createOrUpdate(Demanda demanda);

    void delete(Integer id);
}
