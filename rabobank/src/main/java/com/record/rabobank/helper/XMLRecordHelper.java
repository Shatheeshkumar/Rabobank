package com.record.rabobank.helper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.model.RecordRequest;
import com.record.rabobank.service.RecordParserImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class XMLRecordHelper implements RecordParserImpl{
	
	@Override
	public RecordRequest parse(MultipartFile multipartFile) throws RecordParseException {
		RecordRequest records= new RecordRequest();
		try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RecordRequest.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        records = (RecordRequest) unmarshaller.unmarshal(multipartFile.getInputStream());

	    }catch (Exception e){
	        log.error("XML parser exception",e.getMessage());
	        throw new RecordParseException(e.getMessage());

	    }
	    return records;
	}
}
