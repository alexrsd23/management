package com.rosendo.company.Repository.Users;

import com.rosendo.company.Entity.Users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
