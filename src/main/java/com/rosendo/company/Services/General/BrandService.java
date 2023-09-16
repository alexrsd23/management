package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Brand;
import com.rosendo.company.Repository.General.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    public Brand saveBrand(Brand brand){
        return brandRepository.save(brand);
    }

    public List<Brand> brandList(){
        return brandRepository.findAll();
    }

    public Brand getIdBrand(Long id) throws Exception {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()){
            throw new Exception("Brand not found!");
        }
        return brand.get();
    }

    public Brand updateBrand(Brand updatedBrand, Long id) throws Exception {
        Brand originalBrand = this.getIdBrand(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedBrand, originalBrand);
        return brandRepository.save(originalBrand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
