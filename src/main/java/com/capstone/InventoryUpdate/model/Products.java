package com.capstone.InventoryUpdate.model;

//POJO class for Products
public class Products {
	int productId;
	int availableQty;
	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}
	//public Products(Product products) {
		// TODO Auto-generated constructor stub
//	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getavailableQty() {
		return availableQty;
	}
	public void setavailableQty(int quantity) {
		this.availableQty= quantity;
	}
	@Override
	public String toString() {
		return "Products [productId=" + productId + ", quantity=" + availableQty + "]";
	}
	
}
