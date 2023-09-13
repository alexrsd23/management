package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Supplier;
import com.rosendo.company.Repository.General.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Supplier saveSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public List<Supplier> supplierList(){
        return supplierRepository.findAll();
    }

    public Supplier getIdSupplier(Long id) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty()){
            throw new Exception("Supplier not found!");
        }
        return supplier.get();
    }

    public Supplier updateSupplier(Supplier updatedSupplier, Long id) throws Exception {
        Supplier originalSupplier = this.getIdSupplier(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedSupplier, originalSupplier);
        return supplierRepository.save(originalSupplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
