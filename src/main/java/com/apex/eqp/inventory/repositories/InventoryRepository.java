package com.apex.eqp.inventory.repositories;

import com.apex.eqp.inventory.entities.Product;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.name not in (SELECT rp.name FROM RecalledProduct rp)")
	Collection<Product> getAllProductsThatAreNotRecalled();
	
	@Query("SELECT p FROM Product p LEFT JOIN RecalledProduct rp ON p.name=rp.name where rp.id IS NULL")
    Collection<Product> findAllNotRecalled();
	
	@Query(value="select * from product p where p.name not in (select rp.name from Recalled_Product rp)", nativeQuery=true)
	Collection<Product> findNonRecalledProduct();
	
	Collection<Product> findByName(@Param("name") String name);
}
