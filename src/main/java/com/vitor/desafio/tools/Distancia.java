package com.vitor.desafio.tools;

import com.vitor.desafio.model.Cidade;

public class Distancia {

	public double getDistancia(Cidade c1, Cidade c2) {
		Double resposta = Haversine.distance(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
		return resposta;
	}

}
