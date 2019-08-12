package com.waes.assignment.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.waes.assignment.enumarator.StatusEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class responsible for storing JSON attributes that will be returned as a response in the service.
 * <p>
 *  @see the service {@link com.waes.assignment.rest.ArchiveRESTController#checkDifference(Integer)}
 *  
 * @author igor.furtado
 *
 */
@JsonRootName(value = "comparisonResult")
public class ComparisonResultVO {
	
	
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *	Status of Service 
	 */
	@Getter @Setter
	@JsonProperty(value = "status")
	private StatusEnum status;
	
	/**
	 * Result of comparing two files
	 */
	@Getter @Setter
	@JsonProperty(value =  "message")
    @ApiModelProperty(name = "message", value = "Informative message with the result of comparison")
	private String message; 
	
	
	@Getter @Setter
	@JsonProperty(value = "lenght")
	@ApiModelProperty(name = "lenght", value = "Lenght of the files array")
	private Long lenght;
	
	
	@Getter @Setter
	@JsonProperty(value = "offset")
	@ApiModelProperty(name = "offset", value = "Position in file where difference starts")
	private Long offset;


	public Long getLenght() {
		return lenght;
	}

	public void setLenght(Long lenght) {
		this.lenght = lenght;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}
	
	

}
