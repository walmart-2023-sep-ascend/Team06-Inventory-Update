package com.capstone.InventoryUpdate.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.capstone.InventoryUpdate.model.Products;

public interface InventoryRepository extends MongoRepository<Products,Integer>{
	@Query("{productId:?0}")
    Optional<Products> findByproductId(Integer productId);
	
	@Query("{productId:?0}")
	@Update("{'$set':{'availableQty':?1}}")
	int updateQuantity(int pid,int updatedQty);
}