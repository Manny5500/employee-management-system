package com.mavapps.crud.repositories;

import java.util.List;

import com.mavapps.crud.entities.Employee;

public interface EmployeeRepositoryCustom {
    
    List<Employee> searchWithCriteria (
        String firstName,
        String middleName,
        String lastName,
        Long departmentId,
        int pageSize,
        int pageNumber,
        String sortBy,
        Boolean isAsc
    );

}