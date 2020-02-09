package com.record.rabobank.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;

import com.record.rabobank.controller.RecordController;
import com.record.rabobank.exception.RecordParseException;
import com.record.rabobank.exception.ResourceNotFoundException;
import com.record.rabobank.model.ResponseDTO;
import com.record.rabobank.service.RecordService;

@RunWith(MockitoJUnitRunner.class)
public class RecordControllerTest {

	@InjectMocks
	RecordController obj;

	@Mock
	private TestRestTemplate restTemplate;

	@Mock
	RecordService recordService;

	@LocalServerPort
	private int port;

	@Test
	public void processTest() throws IOException, RecordParseException,
			ResourceNotFoundException {
		restTemplate.getForEntity(new URL("http://localhost:" + port
				+ "/validate/record").toString(), String.class);
		File csvFile = new File(this.getClass().getResource("/records.xml")
				.getFile());
		InputStream is = new FileInputStream(csvFile);

		MockMultipartFile multipartFile = new MockMultipartFile("xml",
				"records.xml", "text/xml", is);
		is.close();
		List<ResponseDTO> response = new ArrayList<>();
		ResponseDTO res = new ResponseDTO();
		res.setDescription("error");
		res.setTransactionRef(123456);
		response.add(res);
		Mockito.when(recordService.process(Mockito.any(), Mockito.any()))
				.thenReturn(response);
		Assert.assertNotNull(obj.recordReceiver(multipartFile));

	}

	@Test(expected = RecordParseException.class)
	public void processTestWithResourceNotFoundException() throws IOException,
			RecordParseException, ResourceNotFoundException {
		restTemplate.getForEntity(new URL("http://localhost:" + port
				+ "/validate/record").toString(), String.class);
		File csvFile = new File(this.getClass().getResource("/records.xml")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml",
				"records.xml", "text/xml", is);
		is.close();
		Mockito.when(recordService.process(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordParseException("should not be empty"));
		obj.recordReceiver(multipartFile);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void invalidFileException() throws Exception {
		restTemplate.getForEntity(new URL("http://localhost:" + port
				+ "/validate/record").toString(), String.class);
		File csvFile = new File(this.getClass().getResource("/records.txt")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("txt",
				"records.txt", "text", is);
		is.close();
		obj.recordReceiver(multipartFile);

	}

	@Test(expected = RecordParseException.class)
	public void processTestWithRecordParseException() throws IOException,
			RecordParseException, ResourceNotFoundException {
		restTemplate.getForEntity(new URL("http://localhost:" + port
				+ "/v1/rabobank").toString(), String.class);
		File csvFile = new File(this.getClass().getResource("/htmltestfile.html")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("html",
				"htmltestfile.html", "text/html", is);
		is.close();
		obj.recordReceiver(multipartFile);
	}
}
