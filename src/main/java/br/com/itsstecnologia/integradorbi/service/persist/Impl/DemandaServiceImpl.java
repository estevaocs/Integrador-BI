package br.com.itsstecnologia.integradorbi.service.persist.Impl;


import br.com.itsstecnologia.integradorbi.entity.Demanda;
import br.com.itsstecnologia.integradorbi.repository.DemandaRepository;
import br.com.itsstecnologia.integradorbi.service.persist.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemandaServiceImpl implements DemandaService {

    @Autowired
    private DemandaRepository repository;

    @Override
    public Demanda createOrUpdate(Demanda demanda) {
        return this.repository.save(demanda);
    }

    @Override
    public void delete(Integer id) {
        this.repository.deleteById(id);
    }
}
