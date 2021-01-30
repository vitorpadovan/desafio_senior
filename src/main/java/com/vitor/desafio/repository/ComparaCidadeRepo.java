package com.vitor.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vitor.desafio.model.ComparaCidade;

public interface ComparaCidadeRepo extends JpaRepository<ComparaCidade, Integer> {
	
	

}
