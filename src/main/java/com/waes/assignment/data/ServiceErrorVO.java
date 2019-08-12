package com.waes.assignment.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

/**
 * Class responsible for storing erros from services
 * <p>
 * 
 * 
 * @author igor.furtado
 *
 */
@JsonRootName(value = "archive")
public class ServiceErrorVO {
	
	
	/**
	 * Message used to return errors
	 */
	@Getter @Setter
	@JsonProperty(value = "message")
	private String message;


	public ServiceErrorVO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
