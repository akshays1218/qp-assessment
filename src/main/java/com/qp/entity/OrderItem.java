package com.qp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "grocery_item_id", referencedColumnName = "id")
	private GroceryItem groceryItem;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	private int quantity;

	public OrderItem() {
		super();

	}

	public OrderItem(Long id, GroceryItem groceryItem, Order order, int quantity) {
		super();
		this.id = id;
		this.groceryItem = groceryItem;
		this.order = order;
		this.quantity = quantity;
	}



	public Long getId() {
		return id;
	}


	public GroceryItem getGroceryItem() {
		return groceryItem;
	}

	public void setGroceryItem(GroceryItem groceryItem) {
		this.groceryItem = groceryItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	

}
