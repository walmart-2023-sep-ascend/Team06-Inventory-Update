package com.capstone.InventoryUpdate.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.capstone.InventoryUpdate.model.Cart;
import com.capstone.InventoryUpdate.model.InventoryResponse;
import com.capstone.InventoryUpdate.model.Product;
import com.capstone.InventoryUpdate.model.Products;

@Service
public class InventoryUpdateServiceImpl implements InventoryUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(InventoryUpdateServiceImpl.class);

	RestTemplate template;

	// Local variables declaration
	int productid, quantity, availableQty, updatedQty;
	List<Products> listProducts;
	Products updatedProduct;
	InventoryResponse invresponse;

	public InventoryResponse cartProcess(int cartid) {
		template = new RestTemplate();
		invresponse=new InventoryResponse();
		listProducts = new ArrayList<Products>();
		
		invresponse.setCartId(cartid);
		
		// GET API call to get cart details
		Cart cart = template.getForObject("http://localhost:8901/cart/{cartid}", Cart.class, cartid);
		logger.info("Cart details: " + cart);
		
		List<Product> products = cart.getProduct();
		logger.info("Products inside cart: " + products);

		invresponse.setProduct(products);
		
		// Get and update quantity for each product in the list
		for (Product p : products) {

			productid = p.getProductId();
			quantity = p.getQuantity();
			logger.info("Product from Cart: " + p);
			Products product = template.getForObject("http://localhost:6003/productupdate/{productid}", Products.class,
					productid);
			listProducts.add(product);

		}
		logger.info("Updated product list: " + listProducts);
		invresponse.setProducts(listProducts);
		invresponse.setInventoryStatus("All Products inventory updated successfully");
		return invresponse;
	}

	public Products productProcess(int productid) {
		// GET API call to get product available quantity
		Products product = template.getForObject("http://localhost:8902/product/{pid}", Products.class, productid);
		logger.info("Current product: " + product);
		availableQty = product.getAvailableQty();

		// Calculate updated quantity
		updatedQty = availableQty - quantity;
		product.setAvailableQty(updatedQty);

		logger.info("inside for " + listProducts);

		// PUT API call to update available quantity
		String url = "http://localhost:8902/updateProduct";
		HttpEntity<Products> requestUpdate = new HttpEntity<Products>(product);
		//Products updatedProduct = template.exchange(url, HttpMethod.PUT, requestUpdate, Products.class);
		ResponseEntity<Products> updatedProduct = template.exchange(url, HttpMethod.PUT, requestUpdate, Products.class);
		logger.info("Updated product: " + updatedProduct);
		return updatedProduct.getBody();
	}
}
