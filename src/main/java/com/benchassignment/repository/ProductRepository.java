package com.benchassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.benchassignment.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
