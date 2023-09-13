package com.rosendo.company.Repository.General;

import com.rosendo.company.Entity.General.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
