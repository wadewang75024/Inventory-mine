package com.apex.eqp.inventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.services.MyProductService;
import com.apex.eqp.inventory.services.ProductService;

@RestController
@RequestMapping(value="api/myinventorycontroller")
public class MyInventoryController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MyProductService mps;
	
	@PostMapping
	public ResponseEntity<Product> createPrudct(@RequestBody Product newP) {
		Product p = productService.save(newP);
		return ResponseEntity.ok(p);
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Optional<Product> p = productService.findById(id);
		if (p.isPresent())
			return ResponseEntity.ok(p.get());
		else
			return ResponseEntity.badRequest().body(null);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable int id, @RequestParam double newPrice) {
		Optional<Product> p = productService.findById(id);
		if (p.isPresent()) {
			Product np = p.get();
			np.setPrice(newPrice);
			np = productService.save(np);
			return ResponseEntity.ok(np);
		}
		else;
			return ResponseEntity.badRequest().body(null);
	}
	
	@PutMapping
	public ResponseEntity<Product> update(@RequestBody Product up) {
		Optional<Product> p = productService.findById(up.getId());
		
		if ( p.isPresent() ) {			
			Product pp = productService.save(up);
			return ResponseEntity.ok(pp);
		}
		else
			return ResponseEntity.badRequest().body(null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable int id) {
		Optional<Product> p = productService.findById(id);
		
		if (p.isPresent()) {
			productService.delete(id);		
			return ResponseEntity.ok(true);
		}
		else
			return ResponseEntity.badRequest().body(false);
	}
	
	@GetMapping("all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> list = mps.getAllProduct();
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("nr")
	public ResponseEntity<List<Product>> getAllProductsNotRecalled() {
		List<Product> list = mps.getAllProductNotRecalled();
		
		return ResponseEntity.ok(list);
	}
	
}
