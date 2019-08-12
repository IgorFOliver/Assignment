package com.waes.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waes.assignment.entity.Archive;
import com.waes.assignment.enumarator.SideEnum;

@Repository
public interface ArchiveRepository extends CrudRepository<Archive, Long> {

	Archive findByFileNumberAndSide(Integer id, SideEnum side);

}
