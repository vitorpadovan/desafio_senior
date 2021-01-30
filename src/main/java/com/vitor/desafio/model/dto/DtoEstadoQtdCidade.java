package com.vitor.desafio.model.dto;

public class DtoEstadoQtdCidade {

	private String estado;
	private int qtdCidades;

	public DtoEstadoQtdCidade() {
		super();
	}

	public DtoEstadoQtdCidade(String estado, int qtdCidades) {
		super();
		this.estado = estado;
		this.qtdCidades = qtdCidades;
	}

	public DtoEstadoQtdCidade(String estado, Long qtdCidades) {
		this(estado, qtdCidades.intValue());
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getQtdCidades() {
		return qtdCidades;
	}

	public void setQtdCidades(int qtdCidades) {
		this.qtdCidades = qtdCidades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + qtdCidades;
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
		DtoEstadoQtdCidade other = (DtoEstadoQtdCidade) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (qtdCidades != other.qtdCidades)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DtoEstado [estado=" + estado + ", qtdCidades=" + qtdCidades + "]";
	}

}
