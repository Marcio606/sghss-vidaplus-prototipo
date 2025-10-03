package com.vidaplus.sghss.repository;

import com.vidaplus.sghss.model.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // Busca por nome
    List<Medicamento> findByNomeContainingIgnoreCase(String nome);

    // Busca por princípio ativo
    List<Medicamento> findByPrincipioAtivoContainingIgnoreCase(String principioAtivo);

    // Busca por categoria
    List<Medicamento> findByCategoria(Medicamento.CategoriaMedicamento categoria);

    // Busca por tipo de receita
    List<Medicamento> findByTipoReceita(Medicamento.TipoReceita tipoReceita);

    // Busca por laboratório
    List<Medicamento> findByLaboratorioContainingIgnoreCase(String laboratorio);

    // Busca por código de barras
    Optional<Medicamento> findByCodigoBarras(String codigoBarras);

    // Busca por número de registro ANVISA
    Optional<Medicamento> findByNumeroRegistroAnvisa(String numeroRegistroAnvisa);

    // Busca medicamentos ativos
    List<Medicamento> findByAtivoTrue();

    // Busca medicamentos inativos
    List<Medicamento> findByAtivoFalse();

    // Busca medicamentos com estoque baixo
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.quantidadeEstoque <= m.quantidadeMinima")
    List<Medicamento> findMedicamentosEstoqueBaixo();

    // Busca medicamentos com estoque crítico
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.quantidadeEstoque <= (m.quantidadeMinima * 0.5)")
    List<Medicamento> findMedicamentosEstoqueCritico();

    // Busca medicamentos sem estoque
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.quantidadeEstoque = 0")
    List<Medicamento> findMedicamentosSemEstoque();

    // Busca medicamentos próximos do vencimento
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.dataValidade BETWEEN CURRENT_DATE AND :dataLimite ORDER BY m.dataValidade")
    List<Medicamento> findMedicamentosProximosVencimento(@Param("dataLimite") LocalDate dataLimite);

    // Busca medicamentos vencidos
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.dataValidade < CURRENT_DATE")
    List<Medicamento> findMedicamentosVencidos();

    // Busca medicamentos por fornecedor
    List<Medicamento> findByFornecedorContainingIgnoreCase(String fornecedor);

    // Busca medicamentos por lote
    List<Medicamento> findByLote(String lote);

    // Busca medicamentos por faixa de preço
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.precoVenda BETWEEN :precoMin AND :precoMax ORDER BY m.precoVenda")
    List<Medicamento> findByPrecoVendaBetween(@Param("precoMin") Double precoMin, 
                                            @Param("precoMax") Double precoMax);

    // Busca medicamentos com maior margem de lucro
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.precoCompra IS NOT NULL AND m.precoVenda IS NOT NULL ORDER BY ((m.precoVenda - m.precoCompra) / m.precoCompra) DESC")
    List<Medicamento> findMedicamentosMaiorMargemLucro(Pageable pageable);

    // Conta medicamentos por categoria
    @Query("SELECT m.categoria, COUNT(m) FROM Medicamento m WHERE m.ativo = true GROUP BY m.categoria ORDER BY COUNT(m) DESC")
    List<Object[]> countMedicamentosPorCategoria();

    // Conta medicamentos por tipo de receita
    @Query("SELECT m.tipoReceita, COUNT(m) FROM Medicamento m WHERE m.ativo = true GROUP BY m.tipoReceita ORDER BY COUNT(m) DESC")
    List<Object[]> countMedicamentosPorTipoReceita();

    // Conta medicamentos por laboratório
    @Query("SELECT m.laboratorio, COUNT(m) FROM Medicamento m WHERE m.ativo = true AND m.laboratorio IS NOT NULL GROUP BY m.laboratorio ORDER BY COUNT(m) DESC")
    List<Object[]> countMedicamentosPorLaboratorio();

    // Conta medicamentos por fornecedor
    @Query("SELECT m.fornecedor, COUNT(m) FROM Medicamento m WHERE m.ativo = true AND m.fornecedor IS NOT NULL GROUP BY m.fornecedor ORDER BY COUNT(m) DESC")
    List<Object[]> countMedicamentosPorFornecedor();

    // Busca por múltiplos critérios
    @Query("SELECT m FROM Medicamento m WHERE " +
           "(:nome IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:principioAtivo IS NULL OR LOWER(m.principioAtivo) LIKE LOWER(CONCAT('%', :principioAtivo, '%'))) AND " +
           "(:categoria IS NULL OR m.categoria = :categoria) AND " +
           "(:tipoReceita IS NULL OR m.tipoReceita = :tipoReceita) AND " +
           "(:laboratorio IS NULL OR LOWER(m.laboratorio) LIKE LOWER(CONCAT('%', :laboratorio, '%'))) AND " +
           "(:fornecedor IS NULL OR LOWER(m.fornecedor) LIKE LOWER(CONCAT('%', :fornecedor, '%'))) AND " +
           "(:ativo IS NULL OR m.ativo = :ativo)")
    Page<Medicamento> findByMultiplosCriterios(@Param("nome") String nome,
                                             @Param("principioAtivo") String principioAtivo,
                                             @Param("categoria") Medicamento.CategoriaMedicamento categoria,
                                             @Param("tipoReceita") Medicamento.TipoReceita tipoReceita,
                                             @Param("laboratorio") String laboratorio,
                                             @Param("fornecedor") String fornecedor,
                                             @Param("ativo") Boolean ativo,
                                             Pageable pageable);

    // Verifica se código de barras já existe
    boolean existsByCodigoBarras(String codigoBarras);

    // Verifica se código de barras já existe (excluindo o próprio medicamento)
    @Query("SELECT COUNT(m) > 0 FROM Medicamento m WHERE m.codigoBarras = :codigoBarras AND (:id IS NULL OR m.id != :id)")
    boolean existsByCodigoBarrasAndIdNot(@Param("codigoBarras") String codigoBarras, @Param("id") Long id);

    // Verifica se número de registro ANVISA já existe
    boolean existsByNumeroRegistroAnvisa(String numeroRegistroAnvisa);

    // Verifica se número de registro ANVISA já existe (excluindo o próprio medicamento)
    @Query("SELECT COUNT(m) > 0 FROM Medicamento m WHERE m.numeroRegistroAnvisa = :numeroRegistroAnvisa AND (:id IS NULL OR m.id != :id)")
    boolean existsByNumeroRegistroAnvisaAndIdNot(@Param("numeroRegistroAnvisa") String numeroRegistroAnvisa, @Param("id") Long id);

    // Calcula valor total do estoque
    @Query("SELECT SUM(m.quantidadeEstoque * COALESCE(m.precoCompra, 0)) FROM Medicamento m WHERE m.ativo = true")
    Double calcularValorTotalEstoque();

    // Calcula valor total do estoque por categoria
    @Query("SELECT m.categoria, SUM(m.quantidadeEstoque * COALESCE(m.precoCompra, 0)) FROM Medicamento m WHERE m.ativo = true GROUP BY m.categoria")
    List<Object[]> calcularValorEstoquePorCategoria();

    // Busca medicamentos mais vendidos
    @Query("SELECT m, SUM(p.quantidade) as totalVendido FROM Medicamento m JOIN m.prescricoes p GROUP BY m.id, m.nome ORDER BY totalVendido DESC")
    List<Object[]> findMedicamentosMaisVendidos(Pageable pageable);

    // Busca medicamentos menos vendidos
    @Query("SELECT m, COALESCE(SUM(p.quantidade), 0) as totalVendido FROM Medicamento m LEFT JOIN m.prescricoes p GROUP BY m.id, m.nome ORDER BY totalVendido ASC")
    List<Object[]> findMedicamentosMenosVendidos(Pageable pageable);

    // Busca medicamentos que precisam de reposição
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND m.quantidadeEstoque <= m.quantidadeMinima ORDER BY m.quantidadeEstoque ASC")
    List<Medicamento> findMedicamentosPrecisamReposicao();

    // Busca medicamentos por concentração
    List<Medicamento> findByConcentracaoContainingIgnoreCase(String concentracao);

    // Busca medicamentos por forma farmacêutica
    List<Medicamento> findByFormaFarmaceuticaContainingIgnoreCase(String formaFarmaceutica);

    // Conta total de medicamentos ativos
    @Query("SELECT COUNT(m) FROM Medicamento m WHERE m.ativo = true")
    long countMedicamentosAtivos();

    // Busca medicamentos mais antigos
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true ORDER BY m.dataCadastro ASC")
    List<Medicamento> findMedicamentosMaisAntigos(Pageable pageable);

    // Busca medicamentos mais recentes
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true ORDER BY m.dataCadastro DESC")
    List<Medicamento> findMedicamentosMaisRecentes(Pageable pageable);

    // Busca medicamentos por nome com paginação
    Page<Medicamento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca medicamentos por categoria com paginação
    Page<Medicamento> findByCategoria(Medicamento.CategoriaMedicamento categoria, Pageable pageable);

    // Busca medicamentos que precisam de atenção
    @Query("SELECT m FROM Medicamento m WHERE m.ativo = true AND " +
           "(m.quantidadeEstoque <= m.quantidadeMinima OR " +
           "m.dataValidade <= :dataLimite OR " +
           "m.quantidadeEstoque = 0)")
    List<Medicamento> findMedicamentosPrecisamAtencao(@Param("dataLimite") LocalDate dataLimite);

    // Busca medicamentos por lote e validade
    @Query("SELECT m FROM Medicamento m WHERE m.lote = :lote AND m.dataValidade = :dataValidade")
    List<Medicamento> findByLoteAndDataValidade(@Param("lote") String lote, @Param("dataValidade") LocalDate dataValidade);
}
