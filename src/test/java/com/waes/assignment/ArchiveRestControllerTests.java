package com.waes.assignment;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.assignment.data.ArchiveVO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ArchiveRestControllerTests {
	
	@Autowired
	private MockMvc  mvc;
	
	@Test
	@DisplayName("POST Service Test Left Success")
	@Order(1)
	public void postDiffLeftTest() throws Exception {
		ArchiveVO archive = new ArchiveVO();
		archive.setBase64("TEVGVDA=");
		
		mvc.perform(post("/v1/diff/{ID}/left", 1).param("{ID}", "1")
				.contentType("application/json").content(convertObjectToJsonBytes(archive)))
		.andExpect(status().is(HttpStatus.CREATED.value()))
		.andExpect(jsonPath("fileNumber", is(1)));
		
	}
	
	@Test
	@DisplayName("POST Service Test Right - Forbidden")
	@Order(2)
	public void postDiffRightForbiddenTest() throws Exception {
		ArchiveVO archive = new ArchiveVO();
		archive.setBase64("TEVGVDA=");
		
		mvc.perform(post("/v1/diff/{ID}/right", 1).param("{ID}", "1")
				.contentType("application/json").content(convertObjectToJsonBytes(archive)))
		.andExpect(status().is(HttpStatus.CREATED.value()))
		.andExpect(jsonPath("fileNumber", is(1)));
		
		mvc.perform(post("/v1/diff/{ID}/right", 1).param("{ID}", "1")
				.contentType("application/json").content(convertObjectToJsonBytes(archive)))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	@DisplayName("POST Service Test Right - Bad Request Response")
	@Order(3)
	public void postDiffRightBadRequestTest() throws Exception {
		mvc.perform(post("/v1/diff/{ID}/right", 1).param("{ID}", "1")
				.contentType("application/json").content(convertObjectToJsonBytes(new ArchiveVO())))
		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	
	@Test
	@DisplayName("GET Service Compare Files")
	@Order(4)
	public void diffTest() throws Exception {
		mvc.perform(get("/v1/diff/{ID}", 1))
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	
	/**
	 * Convert Object in JSON format to be sent to the services
	 * 
	 * @param object Object that will be converted
	 * @return Array of bytes 
	 * @throws IOException 
	 */
	private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	
}
