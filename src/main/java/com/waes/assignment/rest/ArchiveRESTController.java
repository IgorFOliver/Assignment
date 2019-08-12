package com.waes.assignment.rest;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.assignment.data.ArchiveVO;
import com.waes.assignment.data.ComparisonResultVO;
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
	
	@Autowired
	private HttpServletRequest request;
	
	
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
		String realPath = request.getServletContext().getRealPath("/");
		ComparisonResultVO comparisonResult = this.archiveService.compareFiles(ID, realPath);
		if(StatusEnum.SUCCESS.equals(comparisonResult.getStatus())){
			return ResponseEntity.ok(comparisonResult);
		}
		return ResponseEntity.status(404).body(comparisonResult);
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
	@ApiResponses( value = {@ApiResponse(code = 200, message = "File Save with Success."),
			@ApiResponse(code = 400, message = "Base64 not valid."),
			@ApiResponse(code = 500, message = "File can`t be saved.")})
	@PostMapping(value = "/{ID}/left")
	public ResponseEntity<Archive> uploadLeft(@PathVariable Integer ID, @RequestBody final ArchiveVO archiveVO) {
		if(Objects.nonNull(archiveVO) && Objects.nonNull(archiveVO.getBase64()) && !archiveVO.getBase64().isEmpty()) {
			String realPath = request. getServletContext().getRealPath("/");
			Archive archive = this.archiveService.saveFile(ID, archiveVO.getBase64(), SideEnum.LEFT, realPath);
			return ResponseEntity.ok(archive);	
		} else {
			return ResponseEntity.status(400).body(null);
		}
		
	}
	 
	/**
	 * Service responsible for saving a file to the local file server for future comparison.
	 * The file will be save as Left side of comparison.
	 * 
	 * @param ID - Number that identify the file to be compared
	 * @param archive Base64 of the file
	 * @return
	 */
	@ApiOperation( value = "Save the base64 into FileSystem to be used for comparison later")
	@ApiResponses( value = {@ApiResponse(code = 200, message = "File Save with Success."),
			@ApiResponse(code = 400, message = "Base64 not valid."),
			@ApiResponse(code = 500, message = "File can`t be saved.")})
	@PostMapping(value = "/{ID}/right")
	public ResponseEntity<Archive> uploadRight(@PathVariable Integer ID, @RequestBody final ArchiveVO archiveVO) {
		if(Objects.nonNull(archiveVO.getBase64()) && !archiveVO.getBase64().isEmpty()) {
			String realPath = request. getServletContext().getRealPath("/");
			Archive archive = this.archiveService.saveFile(ID, archiveVO.getBase64(), SideEnum.RIGHT, realPath);
			return ResponseEntity.ok(archive);	
		} else {
			return ResponseEntity.status(400).body(null);
		}

	}
		 
}
