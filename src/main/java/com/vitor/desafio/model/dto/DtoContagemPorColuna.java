package com.vitor.desafio.model.dto;

public class DtoContagemPorColuna {

	private String coluna;
	private int quantidade;

	public DtoContagemPorColuna() {
		super();
	}

	public DtoContagemPorColuna(String coluna, int quantidade) {
		super();
		this.coluna = coluna;
		this.quantidade = quantidade;
	}

	public String getColuna() {
		return coluna;
	}

	public void setColuna(String coluna) {
		this.coluna = coluna;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coluna == null) ? 0 : coluna.hashCode());
		result = prime * result + quantidade;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtoContagemPorColuna other = (DtoContagemPorColuna) obj;
		if (coluna == null) {
			if (other.coluna != null)
				return false;
		} else if (!coluna.equals(other.coluna))
			return false;
		if (quantidade != other.quantidade)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DtoContagemPorColuna [coluna=" + coluna + ", quantidade=" + quantidade + "]";
	};

}
