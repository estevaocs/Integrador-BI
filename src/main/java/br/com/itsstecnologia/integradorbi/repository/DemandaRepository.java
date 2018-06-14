package br.com.itsstecnologia.integradorbi.repository;

import br.com.itsstecnologia.integradorbi.entity.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<Demanda, Integer> {
}