package com.capstone.InventoryUpdate.controller;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.capstone.InventoryUpdate.model.Cart;
import com.capstone.InventoryUpdate.model.Product;
import com.capstone.InventoryUpdate.model.Products;
import com.capstone.InventoryUpdate.service.InventoryUpdateServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class InventoryUpdateController {

	private static final String INVENTORYUPDATE_SERVICE = "InventoryUpdateService";
	private static final Logger logger = LoggerFactory.getLogger(InventoryUpdateController.class);

	@Autowired
	InventoryUpdateServiceImpl iuserv;
	ResponseEntity<?> resentity;
	RestTemplate template;

	// Local variables declaration
	int productid, quantity, availableQty, updatedQty;
	List<Products> listProducts;
	Products updatedProduct;

	@GetMapping("/inventoryupdate/{cartid}")
	@CircuitBreaker(name = "INVENTORYUPDATE_SERVICE", fallbackMethod = "cartAPIFallback")
	public ResponseEntity<?> cartAPI(@PathVariable("cartid") int cartid) {
		template=new RestTemplate();
		listProducts = new ArrayList<Products>();

		// GET API call to get cart details
		Cart cart = template.getForObject("http://localhost:8901/cart/{cartid}", Cart.class, cartid);
		logger.info("Cart details: " + cart);

		List<Product> products = cart.getProduct();
		logger.info("Products inside cart: " + products);

		// Get and update quantity for each product in the list
		for (Product p : products) {

			productid = p.getProductId();
			quantity = p.getQuantity();
			logger.info("Product from Cart: " + p);
			Products product = template.getForObject("http://localhost:9004/productupdate/{productid}",
					Products.class, productid);
			listProducts.add(product);

		}
		logger.info("Updated product list: " + listProducts);
		resentity = new ResponseEntity<>("Products Inventory updated", HttpStatus.FOUND);
		return resentity;
	}

	@GetMapping("/productupdate/{productid}")
	@CircuitBreaker(name = "INVENTORYUPDATE_SERVICE", fallbackMethod = "productAPIFallback")
	public ResponseEntity<?> productAPI(@PathVariable("productid") int productid) {

		// GET API call to get product available quantity
		Products product = template.getForObject("http://localhost:8902/product/{pid}", Products.class, productid);
		logger.info("Current product: " + product);
		availableQty = product.getavailableQty();

		// Calculate updated quantity
		updatedQty = availableQty - quantity;
		product.setavailableQty(updatedQty);

		logger.info("inside for " + listProducts);

		// PUT API call to update available quantity
		String url = "http://localhost:8902/updateProduct";
		HttpEntity<Products> requestUpdate = new HttpEntity<Products>(product);
		ResponseEntity<Products> updatedProduct = template.exchange(url, HttpMethod.PUT, requestUpdate, Products.class);
		logger.info("Updated product: " + updatedProduct);
		resentity = new ResponseEntity<>(updatedProduct, HttpStatus.FOUND);
		return resentity;
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
