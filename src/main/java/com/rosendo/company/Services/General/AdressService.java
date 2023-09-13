package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Adress;
import com.rosendo.company.Repository.General.AdressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdressService {
    @Autowired
    AdressRepository adressRepository;

    public Adress saveAdress(Adress adress){
        return adressRepository.save(adress);
    }

    public List<Adress> adressList(){
        return adressRepository.findAll();
    }

    public Adress getIdAdress(Long id) throws Exception {
        Optional<Adress> adress = adressRepository.findById(id);
        if (adress.isEmpty()){
            throw new Exception("Adress not found!");
        }
        return adress.get();
    }

    public Adress updateAdress(Adress updatedAdress, Long id) throws Exception {
        Adress originalAdress = this.getIdAdress(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedAdress, originalAdress);
        return adressRepository.save(originalAdress);
    }

    public void deleteAdress(Long id) {
        adressRepository.deleteById(id);
    }
}
