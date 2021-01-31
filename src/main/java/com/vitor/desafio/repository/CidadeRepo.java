package com.vitor.desafio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vitor.desafio.model.Cidade;

public interface CidadeRepo extends JpaRepository<Cidade, Integer> {

	@Query("SELECT c FROM Cidade c WHERE capital = true order by cidade")
	public Optional<List<Cidade>> getCapitais();

	@Query("SELECT c FROM Cidade c WHERE " + "idIbge != :id"
			+ " AND idIbge not in (SELECT p.cidade2 from ComparaCidade p where p.cidade1 = :id)"
			+ " AND idIbge not in (SELECT p.cidade1 from ComparaCidade p where p.cidade2 = :id)")
	public Optional<List<Cidade>> cidadesParaComparar(@Param("id") Integer usuario);

	@Query("SELECT c FROM Cidade c WHERE estado = :estado")
	public Optional<List<Cidade>> cidadesPorEstado(@Param("estado") String estado);

	public Optional<List<Cidade>> findByEstadoContaining(String valor);

	public Optional<List<Cidade>> findByCapitalContaining(String valor);

	public Optional<List<Cidade>> findByLongitude(Double parseDouble);

	public Optional<List<Cidade>> findByLatitude(Double parseDouble);

	public Optional<List<Cidade>> findBySemAcentoContaining(String valor);

	public Optional<List<Cidade>> findByNomeAlternativoContaining(String valor);

	public Optional<List<Cidade>> findByMicroRegiaoContaining(String valor);

	public Optional<List<Cidade>> findByMesoRegiaoContaining(String valor);

	public Optional<List<Cidade>> findByCidadeContaining(String valor);
}
