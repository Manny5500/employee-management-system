package com.mavapps.crud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mavapps.crud.entities.Employee;
import com.mavapps.crud.repositories.EmployeeRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> getAll(){
        return repo.findAll();
    }
    
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody Employee employee){

        /* 
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
 
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        for (ConstraintViolation<Employee> violation : violations) {
            System.out.println(violation.getMessage()); 
        }
            

        return repo.save(employee);
        */

        repo.save(employee);

        return ResponseEntity.ok("Employee saveed");
    }

    @PostMapping(path = "/delete")
    public String delete(@RequestBody Employee employee){
        try{
            repo.deleteById(employee.getId());
            return "success deletion";
        }catch(Exception e){
            return "deletion failed";
        }
    }
    
    @GetMapping(path = "/search")
    public List<Employee> search(
        @RequestParam String lastName, 
        @RequestParam String firstName,
        @RequestParam String middleName,
        @RequestParam Long departmentId
    ){
        if(departmentId >= 0){
            return repo.findEmployeesByNameAndDepartment(firstName, middleName, lastName, departmentId);
        }else{
            return repo.findEmployeesByName(firstName, middleName, lastName);
        }
    }

    @GetMapping(path = "/searchWithCriteria")
    public ResponseEntity<List<Employee>> searchWithCriteria(
        @RequestParam String lastName,
        @RequestParam String firstName,
        @RequestParam String middleName,
        @RequestParam Long departmentId,
        @RequestParam int pageSize,
        @RequestParam int pageNumber,
        @RequestParam String sortBy,
        @RequestParam Boolean isAsc
    ) {
        return new ResponseEntity<>(repo.searchWithCriteria(
            firstName,
            middleName, 
            lastName, 
            departmentId, 
            pageSize,
            pageNumber,
            sortBy,
            isAsc
        ), HttpStatus.OK);
    }
    

}
