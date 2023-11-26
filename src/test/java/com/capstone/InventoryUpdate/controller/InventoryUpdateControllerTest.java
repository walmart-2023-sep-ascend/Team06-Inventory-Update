package com.capstone.InventoryUpdate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.capstone.InventoryUpdate.model.InventoryResponse;
import com.capstone.InventoryUpdate.model.Product;
import com.capstone.InventoryUpdate.service.InventoryUpdateService;
import com.capstone.InventoryUpdate.model.Products;

@ExtendWith(MockitoExtension.class)
public class InventoryUpdateControllerTest {

	@InjectMocks
	InventoryUpdateController inventoryController;
	
	@Mock
	InventoryUpdateService inventoryService;
		
	
	@SuppressWarnings("unchecked")
	@Test
	void testcartAPI() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<InventoryResponse> responseEntity = null;
		InventoryResponse responseData=new InventoryResponse();
		
		List<Product> product= new ArrayList<Product>();
		Product p1=new Product();
		p1.setProductId(1);
		p1.setQuantity(10);
		product.set(0, p1);
		Product p2=new Product();
		p2.setProductId(3);
		p2.setQuantity(5);
		product.set(0, p2);
		
		List<Products> products= new ArrayList<Products>();
		Products ps1=new Products();
		ps1.setProductId(1);
		ps1.setAvailableQty(35);
		ps1.setProductName("Iphone");
		ps1.setProductRetailPrice(450);
		products.set(0, ps1);
		Products ps2=new Products();
		ps2.setProductId(3);
		ps2.setAvailableQty(42);
		ps2.setProductName("Samsung Mobile");
		ps2.setProductRetailPrice(250);
		products.set(1, ps2);
	
		responseData.setCartId(1);
		responseData.setInventoryStatus("All Products inventory updated successfully");
		responseData.setProduct(product);
		responseData.setProducts(products);		
		
		//when(walletService.getUserInfo(anyInt())).thenReturn(user);
		
		try {
			responseEntity = (ResponseEntity<InventoryResponse>) inventoryController.cartAPI(1);
		} catch (Exception e) {
		}
		assertEquals("All Products inventory updated successfully",responseEntity.getBody().getInventoryStatus());
		assertEquals(responseData.getProduct().size(),responseEntity.getBody().getProduct().size());
		assertEquals(responseData.getProducts().size(),responseEntity.getBody().getProducts().size());
		assertEquals("200 OK",responseEntity.getStatusCode().toString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testProductAPI() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Products responseEntity = null;
		Products products=new Products();
		products.setProductId(1);
		products.setAvailableQty(35);
		products.setProductName("Iphone");
		products.setProductRetailPrice(450);
		
		try {
			responseEntity = inventoryController.productAPI(1);
		} catch (Exception e) {
		}
		assertEquals(products.getProductId(),responseEntity.getProductId());
		assertEquals(products.getProductName(),responseEntity.getProductName());
		assertEquals(products.getProductRetailPrice(),responseEntity.getProductRetailPrice());
		
	}
	
}
