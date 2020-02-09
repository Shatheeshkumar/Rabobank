package com.record.rabobank.helper;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.model.RecordDTO;
import com.record.rabobank.model.RecordRequest;
import com.record.rabobank.service.RecordParserImpl;
@Slf4j
@Component
public class CSVRecordHelper implements RecordParserImpl{

	@Override
	public RecordRequest parse(MultipartFile multipartFile)
			throws RecordParseException {
		RecordRequest recordRequest= new RecordRequest();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()));
            HeaderColumnNameTranslateMappingStrategy<RecordDTO> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<RecordDTO>();
             beanStrategy.setType(RecordDTO.class);

            Map<String, String> columnMapping = new HashMap<>();
            columnMapping.put("Reference", "transactionRef");
            columnMapping.put("Account Number", "accountNumber");
            columnMapping.put("Description", "description");
            columnMapping.put("Start Balance", "startBalance");
            columnMapping.put("Mutation", "mutation");
            columnMapping.put("End Balance", "endBalance");

            beanStrategy.setColumnMapping(columnMapping);

            CsvToBean<RecordDTO> csvToBean = new CsvToBean<RecordDTO>();

            csvToBean.setMappingStrategy(beanStrategy);
            csvToBean.setCsvReader(reader);
            List<RecordDTO> records = csvToBean.parse();
            recordRequest.setRecords(records);
            
        }catch(Exception e){
        	log.error("csv parser exception",e);
            throw new RecordParseException(e.getMessage());
        }
        return recordRequest;
    }
}
