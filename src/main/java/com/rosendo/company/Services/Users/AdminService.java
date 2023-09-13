package com.rosendo.company.Services.Users;

import com.rosendo.company.Entity.Users.Admin;
import com.rosendo.company.Repository.Users.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public Admin saveAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public List<Admin> adminList(){
        return adminRepository.findAll();
    }

    public Admin getIdAdmin(Long id) throws Exception {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()){
            throw new Exception("Administrator not found!");
        }
        return admin.get();
    }

    public Admin updateAdmin(Admin updatedAdmin, Long id) throws Exception {
        Admin originalAdmin = this.getIdAdmin(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedAdmin, originalAdmin);
        return adminRepository.save(originalAdmin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
