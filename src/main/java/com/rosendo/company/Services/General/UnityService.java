package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Unity;
import com.rosendo.company.Repository.General.UnityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnityService {
    @Autowired
    UnityRepository unityRepository;

    public Unity saveUnity(Unity unity){
        return unityRepository.save(unity);
    }

    public List<Unity> unityList(){
        return unityRepository.findAll();
    }

    public Unity getIdUnity(Long id) throws Exception {
        Optional<Unity> unity = unityRepository.findById(id);
        if (unity.isEmpty()){
            throw new Exception("Unity not found!");
        }
        return unity.get();
    }

    public Unity updateUnity(Unity updatedUnity, Long id) throws Exception {
        Unity originalUnity = this.getIdUnity(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedUnity, originalUnity);
        return unityRepository.save(originalUnity);
    }

    public void deleteUnity(Long id) {
        unityRepository.deleteById(id);
    }
}
