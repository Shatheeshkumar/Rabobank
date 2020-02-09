package com.record.rabobank.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
@Data
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633734697481111057L;
	@XmlElement(name = "record")
	private List<RecordDTO> records = null;
	
	public List<RecordDTO> getRecords() {
		return records;
    }

    public void setRecords(List<RecordDTO> records) {
        this.records = records;
    }
}
