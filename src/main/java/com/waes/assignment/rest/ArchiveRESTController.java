package com.waes.assignment.rest;

import java.util.Objects;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.assignment.data.ArchiveVO;
import com.waes.assignment.data.ComparisonResultVO;
import com.waes.assignment.data.ServiceErrorVO;
import com.waes.assignment.entity.Archive;
import com.waes.assignment.enumarator.SideEnum;
import com.waes.assignment.enumarator.StatusEnum;
import com.waes.assignment.service.ArchiveService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Class responsible for providing  file storage and comparison services 
 * 
 * @author igor.furtado
 *
 */
@RestController
@RequestMapping("/v1/diff")
public class ArchiveRESTController {
	
	@Autowired
	ArchiveService archiveService;
	
	/**
	 * Service responsible for comparison of both file sent previously.
	 * 
	 * Return 200 if the comparison was made successfully or 404 if some file was not found to be compared.
	 * 
	 * @param ID - Identificator of both files already sent.
	 * @return Json with result message
	 */
	@ApiOperation( value = "Compare two files sizes and offset", response = ComparisonResultVO.class, 
			notes = "Returns the result of comparing the two previously submitted files" )
	@ApiResponses( value = {@ApiResponse(code = 200, message = "Files compared with success."), 
			@ApiResponse(code = 404, message = "Files to compare was not found.")})
	@RequestMapping(value = "/{ID}")
	public ResponseEntity<ComparisonResultVO> checkDifference(@PathVariable final Integer ID) {
		ComparisonResultVO comparisonResult = this.archiveService.compareFiles(ID);
		if(StatusEnum.SUCCESS.equals(comparisonResult.getStatus())){
			return ResponseEntity.ok(comparisonResult);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comparisonResult);
	}
	
	/** 
	 * 
	 * Service responsible for saving a file to the local file server for future comparison.
	 * The file will be save as Left side of comparison.
	 * 
	 * @param ID - Number that identify the file to be compared
	 * @param archiveVO Base64 of the file
	 * @return
	 */
	@ApiOperation( value = "Save the base64 into FileSystem to be used for comparison later")
	@ApiResponses( value = {@ApiResponse(code = 201, message = "File Save with Success."),
			@ApiResponse(code = 400, message = "Base64 not valid."),
			@ApiResponse(code = 403, message = "The file already exists."),
			@ApiResponse(code = 500, message = "File can`t be saved.")})
	@PostMapping(value = "/{ID}/left")
	public ResponseEntity<?> uploadLeft(@PathVariable Integer ID, @RequestBody final ArchiveVO archiveVO) {
		return manageFileSave(ID, archiveVO, SideEnum.LEFT);
		
	}
	 
	/**
	 * Service responsible for saving a new file to the local file server for future comparison.
	 * The file will be save as Right side of comparison.
	 * 
	 * @param ID - Number that identify the file to be compared
	 * @param archive Base64 of the file
	 * @return
	 */
	@ApiOperation( value = "Save the base64 into FileSystem to be used for comparison later")
	@ApiResponses( value = {@ApiResponse(code = 200, message = "File Save with Success."),
			@ApiResponse(code = 400, message = "Base64 not valid."),
			@ApiResponse(code = 403, message = "The file already exists."),
			@ApiResponse(code = 500, message = "File can`t be saved.")})
	@PostMapping(value = "/{ID}/right")
	public ResponseEntity<?> uploadRight(@PathVariable Integer ID, @RequestBody final ArchiveVO archiveVO) {
		return manageFileSave(ID, archiveVO, SideEnum.RIGHT);
	}
	
	/**
	 * Manage the File save and the return for both services
	 * 
	 * 
	 * @param ID Number that will be used to save the file
	 * @param archiveVO Json with the base64 of the file
	 * @param side Side where the file will be save
	 * 
	 * @return
	 */
	private ResponseEntity<?> manageFileSave(Integer ID, final ArchiveVO archiveVO, SideEnum side) {
		//First will validate if the JSON is valid and if the Base64 is valid
		if(Objects.isNull(archiveVO.getBase64()) || 
				archiveVO.getBase64().isEmpty() || !Base64.isBase64(archiveVO.getBase64())) {
			ServiceErrorVO error = new ServiceErrorVO("JSON format is not valid");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);	
		}else {
			//Check if there is some file already saved for the ID
			Archive savedArchive = this.archiveService.findFileByFileNumberAndSide(ID, side);
			if(Objects.nonNull(savedArchive)) {
				ServiceErrorVO error = new ServiceErrorVO("This service is not allowed to update the file");
				return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
			} else { 
				//Will save the new file under the ID
				Archive archive = this.archiveService.saveFile(ID, archiveVO.getBase64(), side);
				return ResponseEntity.status(HttpStatus.CREATED).body(archive);	
			}
			
		}
	}
		 
}
