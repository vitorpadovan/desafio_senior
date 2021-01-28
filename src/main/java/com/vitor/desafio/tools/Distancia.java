package com.vitor.desafio.tools;

import com.vitor.desafio.model.Cidade;

public class Distancia {
	
	private Cidade c1;
	private Cidade c2;
	
	private double distancia_x;
	private double distancia_y;
	
	private double lat1;
	private double lon1;
	private double lat2;
	private double lon2;
	
	public Distancia(Cidade c1, Cidade c2) {
		this.lat1 = Math.toRadians(c1.getLatitude());
		this.lon1 = Math.toRadians(c1.getLongitude());
		this.lat2 = Math.toRadians(c2.getLatitude());
		this.lon2 = Math.toRadians(c2.getLongitude());
		
		double dlat = lat1 - lat2;
		double dlon = lon1 = lon2;
		double a = Math.pow(Math.sin(dlat/2), 2)
				+ Math.cos(lat1)*Math.cos(lat2)
				*Math.pow(Math.sin(dlon/2), 2);
		
		double c = 2 * Math.asin(Math.sqrt(a));
		double r = 6371;
		
		
	}

}
