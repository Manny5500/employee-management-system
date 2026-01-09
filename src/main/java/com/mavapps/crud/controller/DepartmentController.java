package com.mavapps.crud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mavapps.crud.entities.Department;
import com.mavapps.crud.repositories.DepartmentRepository;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentRepository repo;

    public DepartmentController(DepartmentRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<Department> getAll(){
        return repo.findAll();
    }

    @PostMapping
    public Department create(@RequestBody Department department){
        return repo.save(department);
    }
    
}
