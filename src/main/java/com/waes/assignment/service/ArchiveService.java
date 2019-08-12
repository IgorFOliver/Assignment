package com.waes.assignment.service;

import java.io.File;
import java.io.IOException;

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

	Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	
	public Archive saveFile(Integer id, String base64, SideEnum side, String realPath) {
		try {
			String filename = realPath + FileUtil.BASE_PATH_IMAGES + File.separator + SideEnum.LEFT.name() + File.separator + id;
			FileUtil.saveBase64ToFileSystem(filename, base64);
			Archive archive = new Archive(id, filename, side);
			return this.repository.save(archive);
		} catch (IOException e) {
			logger.trace(">> Error ArchiveService saveBase64ToFileSystem <<" + e.getLocalizedMessage());
			return null;
		}
	
	}

	/**
	 * Method will comapre two diferent files with the same ID and will return if the size are equals or not.
	 * If the size are equals will return insights where the diffs are.
	 * If the size are not equals will return that the size are different.
	 * If one of the files are not found will return fail status with messsage.
	 * 
	 * @param ID
	 * @param realPath
	 * @return ComparisonResultVO
	 */
	public ComparisonResultVO compareFiles(Integer ID, String realPath) {
		String path = realPath + FileUtil.BASE_PATH_IMAGES + File.separator + SideEnum.LEFT + File.separator + ID;
		File leftFile = FileUtil.recoveryFile(path);
		path = realPath + FileUtil.BASE_PATH_IMAGES + File.separator + SideEnum.RIGHT + File.separator + ID;
		File rightFile = FileUtil.recoveryFile(path);
		
		ComparisonResultVO comparisonResult = new ComparisonResultVO();

		if(leftFile.exists() && rightFile.exists()) {
			Long leftSize = leftFile.length();
			Long rightSize = rightFile.length();
			comparisonResult.setStatus(StatusEnum.SUCCESS);
			if(leftSize.equals(rightSize)) {
				comparisonResult.setMessage("Both files are the same size");
			} else {
				
			}
			
		} else {
			comparisonResult.setStatus(StatusEnum.FAIL);
			comparisonResult.setMessage("There must have two Files to be compared");
		}
		return comparisonResult;
		
	}


	
}
