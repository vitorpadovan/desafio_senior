package com.vitor.desafio.controller;

import java.util.List;

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
import com.vitor.desafio.model.dto.DtoContagemPorColuna;
import com.vitor.desafio.service.CidadeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeService servico;

	@ApiOperation(value = "Salva uma cidade enviada por JSON", notes = "Salva uma cidade que foi enviada por JSON", response = Cidade.class)
	@PostMapping
	public ResponseEntity<Cidade> salvarCidade(@RequestBody Cidade cidade) {
		Cidade resposta = servico.salvar(cidade);
		return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}

	@ApiOperation(value = "Salva as cidades enviadas por um arquivo CSV", notes = "Salva as cidades que são enviadas por um arquivo CSV e retorna todos as cidades que foram salvas", response = Cidade.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cidades salvas com sucesso"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento") })
	@PostMapping("/upload")
	public ResponseEntity<List<Cidade>> salvarCsv(@RequestParam("file") MultipartFile file) {
			List<Cidade> cidades = servico.uploadArquivoCsv(file);
			return ResponseEntity.status(HttpStatus.OK).body(cidades);
	
	}

	@ApiOperation(value = "Retorna capitais", notes = "Retorna uma lista em JSON contendo todas as capitais existentes", response = Cidade.class, responseContainer = "List")
	@GetMapping("/capital")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cidades salvas com sucesso"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento"),
			@ApiResponse(code = 404, message = "Não foram encontrado capitais") })
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

	@ApiOperation(value = "Retorna uma cidade específica", notes = "Retorna uma cidade específica dada o seu ID do Ibge", response = List.class, responseContainer = "List")
	@GetMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A cidade indicada pelo id IBGE existe"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento"),
			@ApiResponse(code = 404, message = "Não foi encontrado a cidade específica") })
	public ResponseEntity<Cidade> retornarCidade(@PathVariable("id") Integer id) {
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

	@ApiOperation(value = "Deleta uma cidade específica", notes = "Deleta uma cidade específica dada o seu ID do Ibge", response = ResponseEntity.class)
	@DeleteMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A cidade foi deletada"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento"),
			@ApiResponse(code = 404, message = "Não foi encontrado a cidade para ser deletada") })
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

	@ApiOperation(value = "Filtra cidades por um determinada coluna do CSV ", notes = "Filtra as cidades apartir de uma coluna que é especificada na URL", response = ResponseEntity.class)
	@GetMapping("/filtro/{filtro}/{valor}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cidades especificadas pelo filtro é retornada"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento"),
			@ApiResponse(code = 404, message = "Não foi encontrado cidades dados os filtros") })
	public ResponseEntity<List<Cidade>> filtrar(@PathVariable("filtro") String filtro,
			@PathVariable("valor") String valor) {

		List<Cidade> cidades = servico.filtrar(filtro, valor);
		if (cidades != null && cidades.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(cidades);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/contagem/{filtro}")
	@ApiOperation(value = "Conta a quantidade de registris distintos de acordo com uma coluna", notes = "Conta a quantidade de registris distintos de acordo com uma coluna", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a quantidade de itens de acordo com a coluna espeficificada"),
			@ApiResponse(code = 400, message = "Problemas durante o processamento") })
	public ResponseEntity<DtoContagemPorColuna> contarCampo(@PathVariable("filtro") String filtro) {
		Integer qtd = servico.filtrarPorColuna(filtro);
		return ResponseEntity.status(HttpStatus.OK).body(new DtoContagemPorColuna(filtro, qtd));
	}
}
