package com.mavapps.crud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mavapps.crud.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom{

 @Query("""
    SELECT e FROM Employee e
    WHERE e.department.id = :departmentId
      AND (
           (:firstName IS NULL OR e.firstName = :firstName)
        OR (:middleName IS NULL OR e.middleName = :middleName)
        OR (:lastName IS NULL OR e.lastName = :lastName)
      )
    """)
    List<Employee> findEmployeesByNameAndDepartment(
        @Param("firstName") String firstName,
        @Param("middleName") String middleName,
        @Param("lastName") String lastName,
        @Param("departmentId") Long departmentId
    );

    @Query(
        """
            SELECT e FROM Employee e
            WHERE :firstName IS NULL OR e.firstName = :firstName
            OR :middleName IS NULL OR e.middleName = :middleName
            OR :lastName IS NULL OR e.lastName = :lastName
            """
        )
    List<Employee> findEmployeesByName(
        @Param("firstName") String firstName,
        @Param("middleName") String middleName,
        @Param("lastName") String lastName
    );


}
