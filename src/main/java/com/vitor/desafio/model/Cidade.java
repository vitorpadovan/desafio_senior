package com.vitor.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.SQLInsert;

import com.sun.istack.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "cidade_estado", columnNames = { "cidade", "estado" }),
		@UniqueConstraint(name = "localizacao", columnNames = { "longitude", "latitude" }) })
public class Cidade implements Comparable<Cidade> {

	@Id
	private int idIbge;

	@NotNull
	private String estado;

	@NotNull
	private String cidade;

	@Column(columnDefinition = " boolean default false")
	private Boolean capital;
	private double longitude;
	private double latitude;
	private String semAcento;
	private String nomeAlternativo;
	private String microRegiao;
	private String mesoRegiao;

	public Cidade() {
		super();
	}

	public Cidade(int idIbge, String estado, String cidade, Boolean capital, double longitude, double latitude,
			String semAcento, String nomeAlternativo, String microRegiao, String mesoRegiao) {
		super();
		this.idIbge = idIbge;
		this.estado = estado;
		this.cidade = cidade;
		this.capital = capital;
		this.longitude = longitude;
		this.latitude = latitude;
		this.semAcento = semAcento;
		this.nomeAlternativo = nomeAlternativo;
		this.microRegiao = microRegiao;
		this.mesoRegiao = mesoRegiao;
	}

	public int getIdIbge() {
		return idIbge;
	}

	public void setIdIbge(int idIbge) {
		this.idIbge = idIbge;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getSemAcento() {
		return semAcento;
	}

	public void setSemAcento(String semAcento) {
		this.semAcento = semAcento;
	}

	public String getNomeAlternativo() {
		return nomeAlternativo;
	}

	public void setNomeAlternativo(String nomeAlternativo) {
		this.nomeAlternativo = nomeAlternativo;
	}

	public String getMicroRegiao() {
		return microRegiao;
	}

	public void setMicroRegiao(String microRegiao) {
		this.microRegiao = microRegiao;
	}

	public String getMesoRegiao() {
		return mesoRegiao;
	}

	public void setMesoRegiao(String mesoRegiao) {
		this.mesoRegiao = mesoRegiao;
	}

	public Boolean getCapital() {
		return capital;
	}

	public void setCapital(Boolean capital) {
		this.capital = capital;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + idIbge;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mesoRegiao == null) ? 0 : mesoRegiao.hashCode());
		result = prime * result + ((microRegiao == null) ? 0 : microRegiao.hashCode());
		result = prime * result + ((nomeAlternativo == null) ? 0 : nomeAlternativo.hashCode());
		result = prime * result + ((semAcento == null) ? 0 : semAcento.hashCode());
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
		Cidade other = (Cidade) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (idIbge != other.idIbge)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (mesoRegiao == null) {
			if (other.mesoRegiao != null)
				return false;
		} else if (!mesoRegiao.equals(other.mesoRegiao))
			return false;
		if (microRegiao == null) {
			if (other.microRegiao != null)
				return false;
		} else if (!microRegiao.equals(other.microRegiao))
			return false;
		if (nomeAlternativo == null) {
			if (other.nomeAlternativo != null)
				return false;
		} else if (!nomeAlternativo.equals(other.nomeAlternativo))
			return false;
		if (semAcento == null) {
			if (other.semAcento != null)
				return false;
		} else if (!semAcento.equals(other.semAcento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [idIbge=" + idIbge + ", estado=" + estado + ", cidade=" + cidade + ", capital=" + capital
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", semAcento=" + semAcento
				+ ", nomeAlternativo=" + nomeAlternativo + ", microRegiao=" + microRegiao + ", mesoRegiao=" + mesoRegiao
				+ "]";
	}

	@Override
	public int compareTo(Cidade o) {
		return o.getIdIbge() - this.getIdIbge();
	}

}
