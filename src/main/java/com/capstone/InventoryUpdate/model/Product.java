package com.capstone.InventoryUpdate.model;

//POJO class for Product
public class Product {
	int productId;
	int quantity;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	//public Product(Product products) {
		// TODO Auto-generated constructor stub
//	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", quantity=" + quantity + "]";
	}
}
