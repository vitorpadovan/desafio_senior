package com.vitor.desafio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vitor.desafio.model.Cidade;

public interface CidadeRepo extends JpaRepository<Cidade, Integer>{
	
	
	@Query("SELECT c FROM Cidade c WHERE capital = true order by cidade")
	public Optional<List<Cidade>> getCapitais();
	
	
	
	//Existe uma opção mais eficiente, porem seria necessário criar duas views no banco manualmente, sem a intervenção do JPA
	@Query("SELECT c, "
			+ "sqrt((c.latitude - d.latitude)*(c.latitude - d.latitude) + (c.longitude - d.longitude)*(c.longitude - d.longitude)) as g "
			+ "FROM Cidade c INNER JOIN Cidade d on c.idIbge != d.idIbge"
			+ " ORDER BY sqrt((c.latitude - d.latitude)*(c.latitude - d.latitude) + (c.longitude - d.longitude)*(c.longitude - d.longitude)) DESC")
	public Optional<List<Cidade>> getCidadeDistantes();
}
