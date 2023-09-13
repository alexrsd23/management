package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Sales;
import com.rosendo.company.Repository.General.SalesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SalesService {
    @Autowired
    SalesRepository salesRepository;

    public Sales saveSales(Sales sales){
        return salesRepository.save(sales);
    }

    public List<Sales> salesList(){
        return salesRepository.findAll();
    }

    public Sales getIdSales(Long id) throws Exception {
        Optional<Sales> sales = salesRepository.findById(id);
        if (sales.isEmpty()){
            throw new Exception("Sales not found!");
        }
        return sales.get();
    }

    public Sales updateSales(Sales updatedSales, Long id) throws Exception {
        Sales originalSales = this.getIdSales(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedSales, originalSales);
        return salesRepository.save(originalSales);
    }

    public void deleteSales(Long id) {
        salesRepository.deleteById(id);
    }
}
