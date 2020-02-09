package com.record.rabobank.service;


import org.springframework.web.multipart.MultipartFile;

import com.record.rabobank.model.RecordRequest;

public interface RecordParserImpl {
	
	public  RecordRequest parse(MultipartFile multipartFile)throws Exception;

}
