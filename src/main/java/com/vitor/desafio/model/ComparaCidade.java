package com.vitor.desafio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ComparaCidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "cidade1")
	private Cidade cidade1;

	@ManyToOne
	@JoinColumn(name = "cidade2")
	private Cidade cidade2;

	private Double distancia;

	public ComparaCidade() {
		super();
	}

	public ComparaCidade(Integer id, Cidade cidade1, Cidade cidade2, Double distancia) {
		super();
		this.id = id;
		this.cidade1 = cidade1;
		this.cidade2 = cidade2;
		this.distancia = distancia;
	}

	public ComparaCidade(Cidade cidade1, Cidade cidade2, Double distancia) {
		super();
		this.cidade1 = cidade1;
		this.cidade2 = cidade2;
		this.distancia = distancia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cidade getCidade1() {
		return cidade1;
	}

	public void setCidade1(Cidade cidade1) {
		this.cidade1 = cidade1;
	}

	public Cidade getCidade2() {
		return cidade2;
	}

	public void setCidade2(Cidade cidade2) {
		this.cidade2 = cidade2;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

}
