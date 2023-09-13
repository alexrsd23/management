package com.rosendo.company.Services.Users;

import com.rosendo.company.Entity.Users.Manager;
import com.rosendo.company.Repository.Users.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    public Manager saveManager(Manager manager){
        return managerRepository.save(manager);
    }

    public List<Manager> managerList(){
        return managerRepository.findAll();
    }

    public Manager getIdManager(Long id) throws Exception {
        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isEmpty()){
            throw new Exception("Manager not found!");
        }
        return manager.get();
    }

    public Manager updateManager(Manager updatedManager, Long id) throws Exception {
        Manager originalManager = this.getIdManager(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedManager, originalManager);
        return managerRepository.save(originalManager);
    }

    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
