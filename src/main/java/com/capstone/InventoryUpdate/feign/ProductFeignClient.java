package com.capstone.InventoryUpdate.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capstone.InventoryUpdate.model.Products;

@FeignClient(name = "product-service", url = "http://172.172.241.64:8902",fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

	@GetMapping("/product/{productId}")
	Products getProductDetails(@PathVariable("productId") int productId);
	
	@PutMapping("/updateProduct")
	Products updateProduct(@RequestBody Products product);

}
