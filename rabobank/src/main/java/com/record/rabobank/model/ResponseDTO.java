package com.record.rabobank.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class ResponseDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9199555025560414462L;
	private String description;
	private int transactionRef;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTransactionRef() {
		return transactionRef;
	}
	public void setTransactionRef(int transactionRef) {
		this.transactionRef = transactionRef;
	}

}
