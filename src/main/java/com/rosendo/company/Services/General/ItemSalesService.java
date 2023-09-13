package com.rosendo.company.Services.General;


import com.rosendo.company.Entity.General.ItemSales;
import com.rosendo.company.Repository.General.ItemSalesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ItemSalesService {
    @Autowired
    ItemSalesRepository itemSalesRepository;

    public ItemSales saveItemSales(ItemSales itemSales){
        return itemSalesRepository.save(itemSales);
    }

    public List<ItemSales> itemSalesList(){
        return itemSalesRepository.findAll();
    }

    public ItemSales getIdItemSales(Long id) throws Exception {
        Optional<ItemSales> itemSales = itemSalesRepository.findById(id);
        if (itemSales.isEmpty()){
            throw new Exception("Item Sales not found!");
        }
        return itemSales.get();
    }

    public ItemSales updateItemSales(ItemSales updatedItemSales, Long id) throws Exception {
        ItemSales originalItemSales = this.getIdItemSales(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedItemSales, originalItemSales);
        return itemSalesRepository.save(originalItemSales);
    }

    public void deleteItemSales(Long id) {
        itemSalesRepository.deleteById(id);
    }
}
