package com.record.rabobank.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.record.rabobank.service.RecordService;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {
	@InjectMocks
	RecordService obj;

	@Mock
	MultipartFile multipartFile;

	@Test
	public void processTestForxml() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records.xml")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml",
				"records.xml", "text/xml", is);
		is.close();
		Assert.assertNotNull(obj.process(multipartFile, "text/xml"));

	}

	@Test(expected = Exception.class)
	public void processTestForxmlException() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records.xml")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		is.close();
		obj.process(multipartFile, "text/xml");

	}

	@Test(expected = Exception.class)
	public void processTestForxmlException2() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records.xml")
				.getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml",
				"records.xml", "text/csv", is);
		is.close();
		obj.process(multipartFile, "text/csv");

	}

}
