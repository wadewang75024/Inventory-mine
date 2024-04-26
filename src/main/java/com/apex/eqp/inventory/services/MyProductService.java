package com.apex.eqp.inventory.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.helpers.ProductFilter;
import com.apex.eqp.inventory.repositories.InventoryRepository;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;

@Service
public class MyProductService {
	
	@Autowired
	InventoryRepository ir;
	@Autowired
	RecalledProductRepository rpr;
	
	public List<Product> getAllProduct() {
		return ir.findAll();
	}
	
	public List<Product> getAllProductNotRecalled() {
		Set<String> recalledNames = rpr.findAll().stream().map(p->p.getName()).collect(Collectors.toSet());
		
		ProductFilter pf= new ProductFilter(recalledNames) ;
		List<Product> pl = pf.removeRecalledFrom(ir.findAll());
		
		return pl;
	}

}
