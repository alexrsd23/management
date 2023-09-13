package com.rosendo.company.Repository.Users;

import com.rosendo.company.Entity.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
