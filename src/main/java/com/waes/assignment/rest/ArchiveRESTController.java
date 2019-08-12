package com.waes.assignment.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.assignment.data.ComparisonResultVO;
import com.waes.assignment.entity.Archive;
import com.waes.assignment.enumarator.SideEnum;
import com.waes.assignment.enumarator.StatusEnum;
import com.waes.assignment.repository.ArchiveRepository;

/**
 * 
 * @author igor.furtado  
 *
 */
@Service
public class ArchiveService {
	
	@Autowired
	ArchiveRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * Will save the file in the Filesystem and in the Database.
	 * 
	 * 
	 * @param id Number of the file
	 * @param base64 File in base64 encoeded
	 * @param side Side of the file
	 * @return saved Archive with all File Informations
	 */
	public Archive saveFile(Integer id, String base64, SideEnum side) {
		try {
			String realPath = request.getServletContext().getRealPath("/");
			String filename = realPath + FileUtil.BASE_PATH_IMAGES + File.separator + side + File.separator + id;
			FileUtil.saveBase64ToFileSystem(filename, base64);
			Archive archive = new Archive(id, filename, side);
			return this.repository.save(archive);
		} catch (IOException e) {
			logger.trace(">> Error ArchiveService saveBase64ToFileSystem <<" + e.getLocalizedMessage());
			return null;
		} catch(Exception e) {
			logger.trace(">> Error ArchiveService saveFileToDatabase <<" + e.getLocalizedMessage());
			return null;
		}
	
	}

	/**
	 * Method will comapre two diferent files with the same ID and will return if the size are equals or not.
	 * If the size are equals will return Position where the files start to be diffed and the length of the file.
	 * If the size are not equals will return that the size are different.
	 * If one of the files are not found will return fail status with messsage.
	 * 
	 * @param id
	 * @param realPath
	 * @return ComparisonResultVO
	 */
	public ComparisonResultVO compareFiles(Integer id) {
		Archive leftArchive = this.findFileByFileNumberAndSide(id, SideEnum.LEFT);
		Archive rightArchive = this.findFileByFileNumberAndSide(id, SideEnum.RIGHT);
		
		File leftFile = FileUtil.recoveryFile(leftArchive.getPath());
		File rightFile = FileUtil.recoveryFile(rightArchive.getPath());
		
		ComparisonResultVO comparisonResult = new ComparisonResultVO();

		if(leftFile.exists() && rightFile.exists()) {
			try {
				byte[] leftBytes = Files.readAllBytes(leftFile.toPath());
				byte[] rightBytes = Files.readAllBytes(rightFile.toPath());
				if(Arrays.equals(leftBytes, rightBytes)) {
					comparisonResult.setStatus(StatusEnum.SUCCESS);
					comparisonResult.setMessage("The files are the same");
					return comparisonResult;
				}
			
			
				Long leftSize = leftFile.length();
				Long rightSize = rightFile.length();
				comparisonResult.setStatus(StatusEnum.SUCCESS);
				if(!leftSize.equals(rightSize)) {
					comparisonResult.setMessage("The files are not the same size");
				} else {
					for(int pos = 0; pos < leftBytes.length; pos++ ) {
						if(leftBytes[pos] != rightBytes[pos]) {
							comparisonResult.setLenght(leftSize);
							comparisonResult.setOffset(new Long(pos));
							comparisonResult.setMessage("The files are differente from position " + pos);
							return comparisonResult;
						}
					}
				}
			
			} catch (IOException e) {
				comparisonResult.setStatus(StatusEnum.FAIL);
				comparisonResult.setMessage("Fail to load the files");
				return comparisonResult;
			}
			
		} else {
			comparisonResult.setStatus(StatusEnum.FAIL);
			comparisonResult.setMessage("There must have two Files to be compared");
		}
		return comparisonResult;
		
	}

	public Archive findFileByFileNumberAndSide(Integer id, SideEnum side) {
		return this.repository.findByFileNumberAndSide(id, side);
	}


	
}
