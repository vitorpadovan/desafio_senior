package com.vitor.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.desafio.model.Cidade;
import com.vitor.desafio.model.dto.DtoEstadoQtdCidade;
import com.vitor.desafio.service.CidadeService;

@RestController
@RequestMapping("/estado")
public class EstadoController {

	@Autowired
	private CidadeService servico;

	@GetMapping("/maiormenor")
	public ResponseEntity<List<DtoEstadoQtdCidade>> retornarMaiorMenorEstado() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servico.maiorMenorEstados());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/{estado}")
	public ResponseEntity<List<Cidade>> retornarCidadesPorEstado(@PathVariable String estado) {
		List<Cidade> cidades = servico.getCidadePorEstado(estado);
		if (cidades != null && cidades.size() >0) {
			return ResponseEntity.status(HttpStatus.OK).body(cidades);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
