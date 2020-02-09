package com.record.rabobank.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.exception.ResourceNotFoundException;
import com.record.rabobank.model.RecordResponse;
import com.record.rabobank.model.ResponseDTO;
import com.record.rabobank.service.RecordService;

@Slf4j
@RestController
@RequestMapping("/validate")
public class RecordController {
	
	@Autowired
	RecordService recordService;

	@PostMapping(value = "/record" ,consumes = { "multipart/form-data" })
	public RecordResponse recordReceiver(@RequestParam("file") MultipartFile multipartFile)throws  RecordParseException, ResourceNotFoundException{
		RecordResponse recordResponse = new RecordResponse();
		try{
			if (multipartFile.isEmpty()) {
				throw new ResourceNotFoundException("file not found");
			}
            String contentType = multipartFile.getContentType();
            log.info(contentType);
            if (contentType != null && (contentType.equals("text/xml") || contentType.equals("text/csv"))) {
                List<ResponseDTO> response = recordService.process(multipartFile, contentType);
                if(response !=null){
                	recordResponse.setResponse(response);
                }
            } else {
                throw new RecordParseException("Invalid content type");
            }
		}catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("file not found");
		}
		catch (RecordParseException e) {
            throw new RecordParseException("Invalid format exception");
		}
		return recordResponse;
	}
}
