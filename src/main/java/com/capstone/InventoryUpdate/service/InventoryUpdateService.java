package com.capstone.InventoryUpdate.service;

import org.springframework.stereotype.Service;

import com.capstone.InventoryUpdate.model.InventoryResponse;
import com.capstone.InventoryUpdate.model.Products;

public interface InventoryUpdateService {
	public InventoryResponse cartProcess(int cartid);
	public Products productProcess(int productid);
}
