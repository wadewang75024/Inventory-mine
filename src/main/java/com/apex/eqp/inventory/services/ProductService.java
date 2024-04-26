package com.apex.eqp.inventory.services;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.helpers.ProductFilter;
import com.apex.eqp.inventory.repositories.InventoryRepository;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final InventoryRepository inventoryRepository;
    private final RecalledProductRepository recalledProductRepository;

    @Transactional
    public Product save(Product product) {
        return inventoryRepository.save(product);
    }

    public Collection<Product> getAllProduct() {
    	List<RecalledProduct> recalled = recalledProductRepository.findAll();
    	Set<String> recalledNames = recalled.stream().map(rp-> rp.getName()).collect(Collectors.toSet());
    	
        // ProductFilter filter = new ProductFilter(null);
    	ProductFilter filter = new ProductFilter(recalledNames);

        return filter.removeRecalledFrom(inventoryRepository.findAll());
    }
    
    public Collection<Product> getAllProductWithQuery() {
    	return inventoryRepository.getAllProductsThatAreNotRecalled();
    }
    
    public Collection<Product> findNonRecalledProduct() {
    	return inventoryRepository.findNonRecalledProduct();
    }

    public Optional<Product> findById(Integer id) {
        return inventoryRepository.findById(id);
    }
    
    public void delete(Integer id) {
    	inventoryRepository.deleteById(id);  	
    }
    
    public boolean deleteById(Integer id) {
    	Optional<Product> p = inventoryRepository.findById(id);
    	
    	if (p.isPresent()) {
    		inventoryRepository.delete(p.get());
    		return true;    		
    	}
    	return false;
    }
    
    public boolean update(Integer id, Product p) {
    	if (p==null || id == null)
    		return false;
    	
    	Optional<Product> op = inventoryRepository.findById(id);
    	if (op.isPresent()) {
    		inventoryRepository.save(p);
    		return true;
    	}
    	else
    		return false;
    }
    
    public boolean update(Product p) {
    	if (p==null || p.getId() == null)
    		return false;
    	
    	Optional<Product> op = inventoryRepository.findById(p.getId());
    	if (op.isPresent()) {
    		inventoryRepository.save(p);
    		return true;
    	}
    	else
    		return false;
    }
    
    public Collection<Product> findProductByName(String name) {
    	System.out.println("************************ findProductByName " + name);
    	return inventoryRepository.findByName(name);
    }
}
