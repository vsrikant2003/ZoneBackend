package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.Employee;

/**
 * The EmployeeRepository Interface for the Employee database table.
 * 
 */
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * The findByUserName method
     * 
     * @param userName
     * @return Employee
     */
    public Employee findByUserName(String userName);
}
