package com.waes.assignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.waes.assignment.enumarator.SideEnum;

import lombok.Getter;
import lombok.Setter;

/**
 *  
 * 
 * @author igor.furtado
 *
 */
@Entity
public class Archive {
	
	public Integer getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SideEnum getSide() {
		return side;
	}

	public void setSide(SideEnum side) {
		this.side = side;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter
	private Long id;
	
	/**
	 * Number passed through services used to save the file and keep safe the ID number
	 */
	@Getter @Setter
	@Column(nullable = false)
	private Integer fileNumber;		
	
	/**
	 * File System path of the file
	 */
	@Getter @Setter
	@Column(nullable = false)
	private String path;
	
	
	/**
	 * Side of the file
	 */
	@Getter @Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SideEnum side;
	
	public Archive() {
		
	}
	
	public Archive(Integer number, String path, SideEnum side) {
		this.fileNumber = number;
		this.path = path;
		this.side = side;
			
	}
	

}
