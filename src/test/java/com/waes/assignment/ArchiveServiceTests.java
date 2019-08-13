package com.waes.assignment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.waes.assignment.enumarator.SideEnum;
import com.waes.assignment.service.ArchiveService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ArchiveServiceTests {
	
	@Autowired
	private ArchiveService service = new ArchiveService();
	
	@DisplayName("Test Saving file with success Left Side")
	@Test
	@Order(1)
	public void saveFileLeftTest() {
		assertNotNull(service.saveFile(1, "TEVGVDA=", SideEnum.LEFT));
	}
	
	@DisplayName("Test Saving file with success Right Side")
	@Test
	@Order(2)
	public void saveFileRightTest() {
		assertNotNull(service.saveFile(1, "TEVGVDA=", SideEnum.RIGHT));
	}
	
	@DisplayName("Test Exception in integration with database, not null fileNumber ")
	@Test
	@Order(3)
	public void saveFileExceptionTest() {
		assertNull(service.saveFile(null, "TEVGVDA=", SideEnum.LEFT));
	}
	
	@DisplayName("Test Compare Equal Files")
	@Test
	@Order(4)
	public void compareFilesEquals() {
		assertNotNull(service.compareFiles(1));
	}
	
	@DisplayName("Test Compare Different Files")
	@Test
	@Order(5)
	public void compareFilesDifferent() {
		service.saveFile(2, "TEVGVDA=", SideEnum.LEFT);
		service.saveFile(2, "TEVEVDExMjNhc2Q=", SideEnum.RIGHT);
		assertNotNull(service.compareFiles(2));
	}
	
	
	@DisplayName("Test Compare Same Size Different Values")
	@Test
	@Order(6)
	public void compareFilesSameSizeDifferentValue() {
		service.saveFile(3, "TEVGVDA=", SideEnum.LEFT);
		service.saveFile(3, "TEVEVDE=", SideEnum.RIGHT);
		assertNotNull(service.compareFiles(2));
	}
	
}
