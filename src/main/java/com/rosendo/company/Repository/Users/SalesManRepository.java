package com.rosendo.company.Repository.Users;

import com.rosendo.company.Entity.Users.SalesMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesManRepository extends JpaRepository<SalesMan, Long> {
}
