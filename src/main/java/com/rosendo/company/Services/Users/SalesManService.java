package com.rosendo.company.Services.Users;

import com.rosendo.company.Entity.Users.SalesMan;
import com.rosendo.company.Repository.Users.SalesManRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SalesManService {
    @Autowired
    SalesManRepository salesManRepository;

    public SalesMan saveSalesMan(SalesMan salesMan){
        return salesManRepository.save(salesMan);
    }

    public List<SalesMan> salesManList(){
        return salesManRepository.findAll();
    }

    public SalesMan getIdSalesMan(Long id) throws Exception {
        Optional<SalesMan> salesMan = salesManRepository.findById(id);
        if (salesMan.isEmpty()){
            throw new Exception("SalesMan not found!");
        }
        return salesMan.get();
    }

    public SalesMan updateSalesMan(SalesMan updatedSalesMan, Long id) throws Exception {
        SalesMan originalSalesMan = this.getIdSalesMan(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedSalesMan, originalSalesMan);
        return salesManRepository.save(originalSalesMan);
    }

    public void deleteSalesMan(Long id) {
        salesManRepository.deleteById(id);
    }
}
