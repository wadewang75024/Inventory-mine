package com.apex.eqp.inventory.controllers;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.services.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/inventory/product")
public class InventoryController {

    private final ProductService productService;

    /**
     *
     * @return all the products that are not recalled
     * 
     * This can be tested with http://localhost:8080/api/inventory/product
     */
    @GetMapping
    public ResponseEntity<Collection<Product>> getAllProducts() {
    	return ResponseEntity.ok(productService.findNonRecalledProduct());
    	// return ResponseEntity.ok(productService.getAllProductWithQuery());
        // return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> findProduct(@PathVariable Integer id) {
        Optional<Product> byId = productService.findById(id);

        return byId.map(ResponseEntity::ok).orElse(null);
    }
    
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
    	if (productService.deleteById(id))
    		return ResponseEntity.ok("Product has been deleted successfully");
    	
    	else {				
    		return ResponseEntity.badRequest().body("Failed to delete the product");
    	}
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @Valid @RequestBody Product newProduct) {
    	System.out.println("*********************** updateProduct");
    	Boolean updated = productService.update(id, newProduct);
    	if (updated)
    		return ResponseEntity.ok().body(newProduct);
    	
    	else 
    		return ResponseEntity.badRequest().body(newProduct);
    }
    
    @PutMapping
    public ResponseEntity<Product> updateProduct1(@RequestBody Product newProduct) {
    	System.out.println("*********************** updateProduct1");
    	boolean updated = productService.update(newProduct);
    	
    	return ResponseEntity.ok(newProduct);
    }
    
    @GetMapping("/name")
    public ResponseEntity<Collection> findProductByName(@RequestParam String name) {
    	return ResponseEntity.ok(productService.findProductByName(name));
    }
    
    /* If this is not commented out, it will be called, otherwise, the GlobalExceptionHandler will be called.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        System.out.println("******************** InventoryController::handleValidationExceptions");	
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    */
}
