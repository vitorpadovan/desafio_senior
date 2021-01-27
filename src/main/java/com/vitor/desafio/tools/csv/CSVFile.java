package com.vitor.desafio.tools.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVFile<T> {

	private MultipartFile arquivo;

	public CSVFile(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}

	public static boolean isACsvFile(MultipartFile file) {
		return false;
	}

	public List<T> extract(CSVClassExtract<T> extrator) {
		List<T> lista = new ArrayList<T>();

		try {
			InputStream is = arquivo.getInputStream();
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withHeader().parse(fileReader);

			for (CSVRecord csvRecord : csvRecords) {
				lista.add(extrator.extractClass(csvRecord));
			}

			return lista;

		} catch (UnsupportedEncodingException e) {
			// TODO tratar exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO tratar exception
			e.printStackTrace();
		}
		return null;
	}
}
