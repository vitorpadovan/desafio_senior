package com.vitor.desafio.service;

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.resource.transaction.spi.TransactionCoordinatorOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vitor.desafio.model.Cidade;
import com.vitor.desafio.model.dto.DtoEstadoQtdCidade;
import com.vitor.desafio.repository.CidadeRepo;
import com.vitor.desafio.repository.ComparaCidadeRepo;
import com.vitor.desafio.service.exception.ArquivoInvalidoException;
import com.vitor.desafio.service.exception.ObjectoNaoEncontradoException;
import com.vitor.desafio.tools.csv.CSVClassExtract;
import com.vitor.desafio.tools.csv.CSVFile;

import one.util.streamex.StreamEx;

@Service
public class CidadeService implements CSVClassExtract<Cidade> {

	@Autowired
	private CidadeRepo repo;

	@Autowired
	private ComparaCidadeRepo repoComparacao;

	@Override
	public Cidade extractClass(CSVRecord csvRecord) {

		Cidade c = new Cidade();
		try {
			c.setIdIbge(Integer.parseInt(csvRecord.get("ibge_id")));
		} catch (NumberFormatException e) {
			throw new RuntimeException("Formato de ID do Ibge inválido");
		}

		c.setCidade(csvRecord.get("name"));
		c.setEstado(csvRecord.get("uf"));
		c.setCapital(csvRecord.get("capital").compareTo("true") == 0);
		c.setLongitude(Double.parseDouble(csvRecord.get("lon")));
		c.setLatitude(Double.parseDouble(csvRecord.get("lat")));
		c.setSemAcento(csvRecord.get("no_accents"));
		c.setNomeAlternativo(csvRecord.get("alternative_names"));
		c.setMicroRegiao(csvRecord.get("microregion"));
		c.setMesoRegiao(csvRecord.get("mesoregion"));
		return c;
	}

	public List<Cidade> uploadArquivoCsv(MultipartFile file) {

		System.out.println("-1");
		System.out.println(file.getContentType());
		System.out.println(file.getName());

		System.out.println(CSVFile.isACsvFile(file));
		System.out.println("-2");
		if (!CSVFile.isACsvFile(file)) {
			throw new ArquivoInvalidoException("O arquivo enviado não é válido");
		}
		CSVFile<Cidade> csvF = new CSVFile<Cidade>(file);
		List<Cidade> cidades = csvF.extract(new CidadeService());
		for (Cidade c : cidades) {
			try {
				repo.save(c);
			} catch (ConstraintViolationException ex) {
				System.out.println("1");
			} catch (org.springframework.dao.DataIntegrityViolationException ex) {
				System.out.println("2");
			} catch (Exception ex) {
				System.out.println("3");
			}
		}
		return cidades;
	}

	public List<Cidade> listaCapitais() {
		Optional<List<Cidade>> optCidades = repo.getCapitais();
		if (optCidades.isPresent()) {
			return optCidades.get();
		}
		return null;
	}

	private Map<String, Long> qtdCidadesEstado() {
		List<Cidade> cidades = repo.findAll();
		Map<String, Long> mapCidades = cidades.stream().sorted()
				.collect(Collectors.groupingBy(o -> o.getEstado(), TreeMap::new, Collectors.counting()));
		return mapCidades;
	}

	public List<DtoEstadoQtdCidade> contarEstados() {
		Map<String, Long> mapCidades = this.qtdCidadesEstado();
		List<DtoEstadoQtdCidade> resposta = new ArrayList<DtoEstadoQtdCidade>();
		for (String s : mapCidades.keySet()) {
			resposta.add(new DtoEstadoQtdCidade(s, mapCidades.get(s)));
		}
		return resposta;
	}

	public List<DtoEstadoQtdCidade> maiorMenorEstados() {
		Map<String, Long> mapCidades = this.qtdCidadesEstado();
		String menor = null;
		String maior = null;
		Long qtdMenor = 99999L;
		Long qtdMaior = 0L;
		for (String s : mapCidades.keySet()) {
			if (mapCidades.get(s) > qtdMaior) {
				qtdMaior = mapCidades.get(s);
				maior = s;
			}
			if (mapCidades.get(s) < qtdMenor) {
				qtdMenor = mapCidades.get(s);
				menor = s;
			}
		}

		List<DtoEstadoQtdCidade> resposta = new ArrayList<>();
		resposta.add(new DtoEstadoQtdCidade(maior, qtdMaior));
		resposta.add(new DtoEstadoQtdCidade(menor, qtdMenor));

		return resposta;
	}

	public Cidade getCidade(Integer id) {
		Optional<Cidade> optR = repo.findById(id);
		if (optR.isPresent()) {
			return optR.get();
		}
		return null;
	}

	public List<Cidade> getCidadePorEstado(String estado) {
		Optional<List<Cidade>> optCidades = repo.cidadesPorEstado(estado);
		if (optCidades.isPresent()) {
			return optCidades.get();
		} else {
			throw new ObjectoNaoEncontradoException("Não foi possível encontrar o estado " + estado);
		}
	}

	public Cidade salvar(Cidade cidade) {
		if (repo.findById(cidade.getIdIbge()).isPresent()) {
			throw new ObjectoNaoEncontradoException("Cidade com o código " + cidade.getIdIbge() + " já existe");
		}
		return repo.save(cidade);
	}

	public Boolean deletar(Integer idIbge) {
		repo.deleteById(idIbge);
		if (repo.findById(idIbge).isPresent()) {
			throw new ObjectoNaoEncontradoException("Não encontramos o item para deletar");
		}
		return true;
	}

	public List<Cidade> filtrar(String filtro, String valor) {
		Optional<List<Cidade>> optCidades = null;

		switch (filtro) {
		case "ibge_id":

			Optional<Cidade> optC = repo.findById(Integer.parseInt(valor));
			if (optC.isPresent()) {
				List<Cidade> r = new ArrayList<Cidade>();
				r.add(optC.get());
				return r;
			}
			break;
		case "uf":
			optCidades = repo.findByEstadoContaining(valor);
			break;
		case "name":
			optCidades = repo.findByCidadeContaining(valor);
			break;
		case "capital":
			optCidades = repo.findByCapitalContaining(valor);
			break;
		case "lon":
			optCidades = repo.findByLongitude(Double.parseDouble(valor));
			break;
		case "lat":
			optCidades = repo.findByLatitude(Double.parseDouble(valor));
			break;
		case "no_accents":
			optCidades = repo.findBySemAcentoContaining(valor);
			break;
		case "alternative_names":
			optCidades = repo.findByNomeAlternativoContaining(valor);
			break;
		case "microregion":
			optCidades = repo.findByMicroRegiaoContaining(valor);
			break;
		case "mesoregion":
			optCidades = repo.findByMesoRegiaoContaining(valor);
			break;
		default:
			throw new RuntimeException("Opção de coluna inexistente");
		}
		if (optCidades != null && optCidades.isPresent()) {
			return optCidades.get();
		}
		return null;
	}

	public int filtrarPorColuna(String filtro) {
		List<Cidade> cidades = repo.findAll();
		StreamEx<Cidade> groupBy = StreamEx.of(cidades);
		List<Cidade> resultado = null;
		switch (filtro) {
		case "ibge_id":
			resultado = groupBy.distinct(c -> c.getIdIbge()).toList();
			break;
		case "uf":
			resultado = groupBy.distinct(c -> c.getEstado()).toList();
			break;
		case "name":
			resultado = groupBy.distinct(c -> c.getCidade()).toList();
			break;
		case "capital":
			resultado = groupBy.distinct(c -> c.getCapital()).toList();
			break;
		case "lon":
			resultado = groupBy.distinct(c -> c.getLongitude()).toList();
			break;
		case "lat":
			resultado = groupBy.distinct(c -> c.getLatitude()).toList();
			break;
		case "no_accents":
			resultado = groupBy.distinct(c -> c.getSemAcento()).toList();
			break;
		case "alternative_names":
			resultado = groupBy.distinct(c -> c.getNomeAlternativo()).toList();
			break;
		case "microregion":
			resultado = groupBy.distinct(c -> c.getMicroRegiao()).toList();
			break;
		case "mesoregion":
			resultado = groupBy.distinct(c -> c.getMesoRegiao()).toList();
			break;
		default:
			throw new RuntimeException("Opção de coluna inexistente");
		}
		return resultado.size();
	}
}
