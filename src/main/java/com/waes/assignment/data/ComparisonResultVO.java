package com.waes.assignment.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.waes.assignment.enumarator.StatusEnum;

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
	@JsonProperty(value =  "result")
	private String message; 

}
