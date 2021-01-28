package com.vitor.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/capital")
	public ResponseEntity<List<Cidade>> retornarCapitais(){
		return servico.listaCapitais();
	}

	
	@PostMapping("/upload")
	public ResponseEntity<List<Cidade>> salvarCsv(@RequestParam("file") MultipartFile file) {
		servico.salvarArquivoCsv(file);
		return null;
	}
}
