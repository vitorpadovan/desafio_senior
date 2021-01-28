package com.vitor.desafio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vitor.desafio.model.Cidade;

public interface CidadeRepo extends JpaRepository<Cidade, Integer>{
	
	
	@Query("SELECT c FROM Cidade c WHERE capital = true order by cidade")
	public Optional<List<Cidade>> getCapitais();
}
