package com.mavapps.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mavapps.crud.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}