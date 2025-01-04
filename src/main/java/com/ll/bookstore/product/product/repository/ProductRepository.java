package com.ll.bookstore.product.product.repository;

import com.ll.bookstore.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
