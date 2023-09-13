package com.rosendo.company.Services.General;

import com.rosendo.company.Entity.General.Product;
import com.rosendo.company.Repository.General.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> productList(){
        return productRepository.findAll();
    }

    public Product getIdProduct(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new Exception("Product not found!");
        }
        return product.get();
    }

    public Product updateProduct(Product updatedProduct, Long id) throws Exception {
        Product originalProduct = this.getIdProduct(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedProduct, originalProduct);
        return productRepository.save(originalProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
