package com.vitor.desafio.tools.csv;

import org.apache.commons.csv.CSVRecord;

public interface CSVClassExtract<T> {

	T extractClass(CSVRecord csvRecord);
}
