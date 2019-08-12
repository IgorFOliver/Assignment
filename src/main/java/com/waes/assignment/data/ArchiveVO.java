package com.waes.assignment.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

/**
 * Class responsible for storing JSON attributes for services that will store files
 * <p>
 * @see the service {@link com.waes.assignment.rest.ArchiveRESTController#uploadLeft(Integer, ArchiveVO)}
 * @see the service {@link com.waes.assignment.rest.ArchiveRESTController#uploadRight(Integer, ArchiveVO)}
 * 
 * @author igor.furtado
 *
 */
@JsonRootName(value = "archive")
public class ArchiveVO {
	
	
	/**
	 * Base64 File to be used by the services
	 */
	@Getter @Setter
	@JsonProperty(value = "base64")
	private String base64;

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

}
