package com.vitor.desafio.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vitor.desafio.model.Cidade;
import com.vitor.desafio.repository.CidadeRepo;
import com.vitor.desafio.tools.csv.CSVClassExtract;
import com.vitor.desafio.tools.csv.CSVFile;


@Service
public class CidadeService implements CSVClassExtract<Cidade> {
	
	@Autowired
	private CidadeRepo repo;
	
	public List<Cidade> salvarArquivoCsv(MultipartFile file) {
		
		if(!CSVFile.isACsvFile(file)) {
			//TODO trata o erro de quando não é um arquivo CSV
		}		
		//TODO Continua o processamento
		CSVFile<Cidade> csvF = new CSVFile<Cidade>(file);
		List<Cidade> cidades = csvF.extract(new CidadeService());
		for(Cidade c : cidades) {
			try {
				repo.save(c);	
			}
			catch(ConstraintViolationException  ex) {
				//TODO verificar qual erro entra aqui
			}catch(org.springframework.dao.DataIntegrityViolationException ex) {
				//TODO tratar erro de valores duplicados
			}catch(Exception ex) {
				//TODO tratar outros erros desconhecidos
			}
		}
		return null;
	}

	@Override
	public Cidade extractClass(CSVRecord csvRecord) {
		
		// TODO tratar erro de quantidade errada
		Cidade c = new Cidade();
		//TODO tratar erro de int
		c.setIdIbge(Integer.parseInt(csvRecord.get("ibge_id")));
		c.setCidade(csvRecord.get("name"));
		c.setEstado(csvRecord.get("uf"));
		c.setCapital(csvRecord.get("capital").compareTo("true") ==0 );
		c.setLongitude(Double.parseDouble(csvRecord.get("lon")));
		c.setLatitude(Double.parseDouble(csvRecord.get("lat")));
		c.setSemAcento(csvRecord.get("no_accents"));
		c.setNomeAlternativo(csvRecord.get("alternative_names"));
		c.setMicroRegiao(csvRecord.get("microregion"));
		c.setMesoRegiao(csvRecord.get("mesoregion"));		
		return c;
	}
	
	public ResponseEntity<List<Cidade>> listaCapitais(){

		Optional<List<Cidade>> optCapitais = repo.getCapitais();
		if(optCapitais.isPresent())
		{
			return ResponseEntity.status(HttpStatus.OK).body(optCapitais.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
