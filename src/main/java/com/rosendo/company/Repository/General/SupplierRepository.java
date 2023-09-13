package com.rosendo.company.Repository.General;

import com.rosendo.company.Entity.General.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository  extends JpaRepository<Supplier, Long> {
}
