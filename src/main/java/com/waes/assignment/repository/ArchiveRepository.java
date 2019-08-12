package com.waes.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waes.assignment.entity.Archive;

@Repository
public interface ArchiveRepository extends CrudRepository<Archive, Long> {
	
	

}
