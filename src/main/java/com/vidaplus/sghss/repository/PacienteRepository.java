package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByEmail(String email);

    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    Page<Paciente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Paciente> findByAtivoTrue();

    List<Paciente> findByAtivoFalse();

    List<Paciente> findBySexo(Paciente.Sexo sexo);
    @Query("SELECT p FROM Paciente p WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND (:cpf IS NULL OR p.cpf = :cpf)")
    Page<Paciente> search(@Param("nome") String nome, @Param("cpf") String cpf, Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.ativo = true AND " +
           "(p.endereco IS NULL OR p.telefone IS NULL OR p.nomeMae IS NULL)")
    List<Paciente> findPacientesComDadosIncompletos();

    @Query("SELECT DISTINCT p FROM Paciente p JOIN p.consultas c JOIN c.medico m WHERE m.especialidade = :especialidade")
    List<Paciente> findPacientesPorEspecialidadeMedica(@Param("especialidade") String especialidade);

    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.ativo = true")
    long countPacientesAtivos();

    @Query("SELECT p FROM Paciente p WHERE p.ativo = true ORDER BY p.dataCadastro ASC")
    List<Paciente> findPacientesMaisAntigos(Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.ativo = true ORDER BY p.dataCadastro DESC")
    List<Paciente> findPacientesMaisRecentes(Pageable pageable);

}
