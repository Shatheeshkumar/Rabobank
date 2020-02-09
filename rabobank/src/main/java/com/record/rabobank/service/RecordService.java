package com.record.rabobank.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.helper.CSVRecordHelper;
import com.record.rabobank.helper.RecordValidationHelper;
import com.record.rabobank.helper.XMLRecordHelper;
import com.record.rabobank.model.RecordRequest;
import com.record.rabobank.model.ResponseDTO;

@Slf4j
@Service
public class RecordService {

	public List<ResponseDTO> process(MultipartFile multipartFile, String contentType)throws RecordParseException{
		RecordParserImpl recordParser=null;
        try {
            if (contentType.equals("text/xml")) {
            	recordParser = new XMLRecordHelper();
            } else {
            	recordParser = new CSVRecordHelper();
            }
            	RecordRequest recordRequest = recordParser.parse(multipartFile);
                if (recordRequest!=null) {
                	RecordValidationHelper recordValidationHelper = new RecordValidationHelper(); 
                    return recordValidationHelper.FrameResponse(recordRequest);
                } else {
                    throw new RecordParseException("parser response exception");
                }
        }catch(Exception e){
            log.error("record service exception",e.getMessage());
            throw new RecordParseException(e.getMessage());
        }
	}
}
