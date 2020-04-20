package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Project;

/**
 * The ProjectRepository Interface for the Project database table.
 * 
 */
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    /**
     * The findByProjectName method
     * 
     * @param projectName
     * @return Project
     */
    public Project findByProjectName(String projectName);
}
