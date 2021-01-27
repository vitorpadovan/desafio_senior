package com.vitor.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.desafio.model.Cidade;

public interface CidadeRepo extends JpaRepository<Cidade, Integer>{

}
