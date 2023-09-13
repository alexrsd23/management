package com.rosendo.company.Repository.General;

import com.rosendo.company.Entity.General.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
