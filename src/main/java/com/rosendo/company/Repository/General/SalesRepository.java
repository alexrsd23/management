package com.rosendo.company.Repository.General;

import com.rosendo.company.Entity.General.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
