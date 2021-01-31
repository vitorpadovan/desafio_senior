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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/estado")
public class EstadoController {

	@Autowired
	private CidadeService servico;

	@GetMapping("/maiormenor")
	@ApiOperation(value = "Retorna maior e menor cidade", notes = "Retorna o maior e menor estado existente", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna os dois estados com maior e menor cidades"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento") })
	public ResponseEntity<List<DtoEstadoQtdCidade>> retornarMaiorMenorEstado() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servico.maiorMenorEstados());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/{estado}")
	@ApiOperation(value = "Retorna cidades por estado", notes = "Retorna todas as cidades por estado", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna todas as cidades do estado escolhido"),
			@ApiResponse(code = 404, message = "Não foi encontrado o estado selecionado") })
	public ResponseEntity<List<Cidade>> retornarCidadesPorEstado(@PathVariable String estado) {
		List<Cidade> cidades = servico.getCidadePorEstado(estado);
		return ResponseEntity.status(HttpStatus.OK).body(cidades);
	}
}
