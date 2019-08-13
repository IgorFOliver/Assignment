package com.waes.assignment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.waes.assignment.enumarator.SideEnum;
import com.waes.assignment.service.ArchiveService;

@SpringBootTest
public class ArchiveServiceTests {
	
	@Autowired
	private ArchiveService service = new ArchiveService();
	
	@DisplayName("Test Saving file with success")
	@Test
	public void saveFileTest() {
		assertNotNull(service.saveFile(1, "TEVGVDA=", SideEnum.LEFT));
	}
	
	@DisplayName("Test Exception in integration with database, not null fileNumber ")
	@Test
	public void saveFileExceptionTest() {
		assertNull(service.saveFile(null, "TEVGVDA=", SideEnum.LEFT));
	}
	
}
