package com.record.rabobank.helper;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.model.RecordDTO;
import com.record.rabobank.model.RecordRequest;
import com.record.rabobank.model.ResponseDTO;

@Slf4j
@Component
public class RecordValidationHelper {
	
	public List<ResponseDTO> FrameResponse(RecordRequest records)throws RecordParseException{
		Set<Integer> recordSet = new HashSet<Integer>();
		List<ResponseDTO> response= new ArrayList<ResponseDTO>();
		try{
			log.info("frame"+records.getRecords());
		for(RecordDTO record : records.getRecords()){
			ResponseDTO responseDTO = new ResponseDTO();
			if (!recordSet.add(record.getTransactionRef())){
				responseDTO.setTransactionRef(record.getTransactionRef());
				responseDTO.setDescription("duplicate entry");
			}else if(validateBalance(record)!= 0){
				responseDTO.setTransactionRef(record.getTransactionRef());
				responseDTO.setDescription("Balance mismatch");
			}else{
				responseDTO.setTransactionRef(record.getTransactionRef());
				responseDTO.setDescription("valid entry");
			}
			response.add(responseDTO);
		}
		log.info("res"+response);
		}catch(Exception e){
			log.error("record validation exception",e.getMessage());
			throw new RecordParseException(e.getMessage());
		}
		return response;	
	}
	
	public int validateBalance(RecordDTO record ){
		MathContext mc = new MathContext(4);
		return((record.getStartBalance().add(record.getMutation(),mc)).compareTo(record.getEndBalance()));	
	}
}
