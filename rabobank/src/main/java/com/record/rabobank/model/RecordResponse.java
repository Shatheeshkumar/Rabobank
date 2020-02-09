package com.record.rabobank.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class RecordResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7273046620224141960L;
	
	private List<ResponseDTO> response = null;

	public List<ResponseDTO> getResponse() {
		return response;
	}

	public void setResponse(List<ResponseDTO> response) {
		this.response = response;
	}

}
