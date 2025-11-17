package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
    boolean existsByCrmAndIdNot(String crm, Long id);
}