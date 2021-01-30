package com.vitor.desafio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vitor.desafio.model.Cidade;
import com.vitor.desafio.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeService servico;

	@PostMapping
	public ResponseEntity<Cidade> salvarCidade(@RequestBody Cidade cidade) {
		Cidade resposta = servico.salvar(cidade);
		return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}

	@PostMapping("/upload")
	public ResponseEntity<List<Cidade>> salvarCsv(@RequestParam("file") MultipartFile file) {
		try {
			List<Cidade> cidades = servico.uploadArquivoCsv(file);
			return ResponseEntity.status(HttpStatus.OK).body(cidades);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/capital")
	public ResponseEntity<List<Cidade>> retornarCapitais() {
		try {
			List<Cidade> capitais = servico.listaCapitais();
			if (capitais != null && capitais.size() > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(capitais);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cidade> retornarCapitais(@PathVariable("id") Integer id) {
		Cidade r = servico.getCidade(id);
		try {
			if (r != null) {
				return ResponseEntity.status(HttpStatus.OK).body(r);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletarCidade(@PathVariable("id") Integer id) {
		if (servico.getCidade(id) != null) {
			// Cidade encontrada
			if (servico.deletar(id)) {
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/filtro/{filtro}/{valor}")
	public ResponseEntity<List<Cidade>> filtrar(@PathVariable("filtro") String filtro,
			@PathVariable("valor") String valor) {
		
		List<Cidade> cidades = servico.filtrar(filtro, valor);
		if(cidades != null && cidades.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(cidades);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/contagem/{filtro}")
	public ResponseEntity<String> contarCampo(@PathVariable("filtro") String filtro){
		Integer qtd = servico.filtrarPorColuna(filtro);
		return ResponseEntity.status(HttpStatus.OK).body(qtd.toString());
	}
}
