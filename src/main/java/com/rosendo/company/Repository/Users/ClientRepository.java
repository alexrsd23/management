package com.rosendo.company.Repository.Users;

import com.rosendo.company.Entity.Users.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
