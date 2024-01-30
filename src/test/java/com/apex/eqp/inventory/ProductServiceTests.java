package com.apex.eqp.inventory;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.services.ProductService;
import com.apex.eqp.inventory.services.RecalledProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class ProductServiceTests {

    @Autowired
    ProductService productService;

    @Autowired
    RecalledProductService recalledProductService;

    /**
     * Helper method to create test products
     */
    private Product createTestProduct(String productName, Double price, Integer quantity) {
        return Product.builder()
                .name(productName)
                .price(price)
                .quantity(quantity)
                .build();
    }

    /**
     * Helper method to create test recalled products
     */
    private RecalledProduct createTestRecalledProduct(String recalledProductName, Boolean expired) {
        return RecalledProduct.builder()
                .name(recalledProductName)
                .expired(expired)
                .build();
    }

    @Test
    void shouldSaveProduct() {
        Product product = createTestProduct("product1", 1.2, 2);

        Product savedProduct = productService.save(product);

        Product loadedProduct = productService.findById(savedProduct.getId()).orElse(null);

        Assertions.assertNotNull(loadedProduct);
        Assertions.assertNotNull(loadedProduct.getId());
    }

    @Test
    void shouldUpdateProduct() {
        Product product = createTestProduct("product2", 1.3, 5);

        Product savedProduct = productService.save(product);

        Product loadedProduct = productService.findById(savedProduct.getId()).orElse(null);

        Assertions.assertNotNull(loadedProduct);

        loadedProduct.setName("NewProduct");

        productService.save(loadedProduct);

        Assertions.assertNotNull(productService.findById(loadedProduct.getId()).orElse(null));
    }

    /**
     * Test getAllProduct
     * Note that there are products and recalled product in data.sql under src/main/resources
     */
    @Test
    void testGetAllProduct() {
    	Product product1 = createTestProduct("product1", 1.3, 5);
        productService.save(product1);
        Product product2 = createTestProduct("product2", 1.3, 5);
        productService.save(product2);
        
        RecalledProduct recalledProduct = createTestRecalledProduct("product1", true);
        recalledProductService.save(recalledProduct);
        
        List<RecalledProduct> allRecalledProducts = (List<RecalledProduct>) recalledProductService.getAllRecalledProducts();
        Assertions.assertEquals(2, allRecalledProducts.size());
        
        // before fixing all the issues
        List<Product> allProducts = (List<Product>) productService.getAllProduct();
        // Assertions.assertEquals(5, allProducts.size());
        
        // after fixing all the issues
        Assertions.assertEquals(3, allProducts.size());
    }
    
    /**
     * Test getAllProductWithQuery
     * Note that there are products and recalled product in data.sql under src/main/resources
     */
    @Test
    void testGetAllProductWithRecalled() {
    	Product product1 = createTestProduct("product1", 1.3, 5);
        productService.save(product1);
        Product product2 = createTestProduct("product2", 1.3, 5);
        productService.save(product2);
        
        RecalledProduct recalledProduct = createTestRecalledProduct("product1", true);
        recalledProductService.save(recalledProduct);
        
        List<RecalledProduct> allRecalledProducts = (List<RecalledProduct>) recalledProductService.getAllRecalledProducts();
        Assertions.assertEquals(2, allRecalledProducts.size());
        
        List<Product> allProducts = (List<Product>) productService.getAllProductWithQuery();
        allProducts.stream().forEach(p-> {System.out.println(p.getName()); });
        
        Assertions.assertEquals(3, allProducts.size());
    }

}
