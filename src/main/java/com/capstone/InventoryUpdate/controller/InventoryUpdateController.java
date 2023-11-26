package com.capstone.InventoryUpdate.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.InventoryUpdate.model.InventoryResponse;
import com.capstone.InventoryUpdate.model.Products;
import com.capstone.InventoryUpdate.service.InventoryUpdateService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class InventoryUpdateController {

	private static final String INVENTORYUPDATE_SERVICE = "InventoryUpdateService";
	private static final Logger logger = LoggerFactory.getLogger(InventoryUpdateController.class);

	@Autowired
	InventoryUpdateService iuserv;
	ResponseEntity<?> resentity;
	//RestTemplate template;

	// Local variables declaration
	//int productid, quantity, availableQty, updatedQty;
	//List<Products> listProducts;
	//Products updatedProduct;

	@GetMapping("/inventoryupdate/{cartid}")
	@CircuitBreaker(name = "INVENTORYUPDATE_SERVICE", fallbackMethod = "cartAPIFallback")
	public ResponseEntity<?> cartAPI(@PathVariable("cartid") int cartid) {
		InventoryResponse response=iuserv.cartProcess(cartid);
		resentity = new ResponseEntity<>(response, HttpStatus.OK);
		return resentity;
	}

	@GetMapping("/productupdate/{productid}")
	@CircuitBreaker(name = "INVENTORYUPDATE_SERVICE", fallbackMethod = "productAPIFallback")
	public Products productAPI(@PathVariable("productid") int productid) {
		Products updatedProduct=iuserv.productProcess(productid);
		//resentity = new ResponseEntity<>(updatedProduct, HttpStatus.FOUND);
		return updatedProduct;
	}

	// Fallback method for Cart service
	public ResponseEntity<String> cartAPIFallback() {
		return new ResponseEntity<String>("Cart service is down, pls try later", HttpStatus.OK);
	}

	// Fallback method for Product service
	public ResponseEntity<String> productAPIFallback() {
		return new ResponseEntity<String>("Product service is down, pls try later", HttpStatus.OK);
	}
}
