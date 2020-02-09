package com.record.rabobank.model;

import java.io.Serializable;
import java.math.BigDecimal;




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110043185469031190L;
	
	@XmlAttribute(name = "reference")
	private int transactionRef;
	private String accountNumber;
	private BigDecimal mutation;
	private BigDecimal startBalance;
	private BigDecimal endBalance;
	private String description;
	
	@JsonProperty(value="Reference")
	public int getTransactionRef() {
		return transactionRef;
	}
	public void setTransactionRef(int transactionRef) {
		this.transactionRef = transactionRef;
	}
	@JsonProperty(value="Start Balance")
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@JsonProperty(value="Mutation")
	public BigDecimal getMutation() {
		return mutation;
	}
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	@JsonProperty(value="End Balance")
	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
