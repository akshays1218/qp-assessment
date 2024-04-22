package com.qp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qp.entity.GroceryItem;
import com.qp.entity.Order;
import com.qp.entity.OrderItem;
import com.qp.repository.GroceryItemRepository;
import com.qp.repository.OrderItemRepository;
import com.qp.repository.OrderRepository;
import com.qp.request.OrderItemRequest;
import com.qp.request.UserRequest;
import com.qp.response.UserResponse;
import com.qp.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	GroceryItemRepository groceryItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@PostMapping("/save")
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
		try {
			UserResponse userResponse = userService.saveUser(userRequest);
			return ResponseEntity.ok(userResponse);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/items")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAvailableItems() {
		List<GroceryItem> items = groceryItemRepository.findAll();
		return ResponseEntity.ok().body(items);
	}

	@PostMapping("/book/orders")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> bookItems(@RequestBody List<OrderItemRequest> orderItems) {
		Order order = new Order();
		orderRepository.save(order);

		for (OrderItemRequest orderItemRequest : orderItems) {
			Long itemId = orderItemRequest.getItemId();
			int quantity = orderItemRequest.getQuantity();

			Optional<GroceryItem> itemOptional = groceryItemRepository.findById(itemId);
			if (itemOptional.isPresent()) {
				GroceryItem item = itemOptional.get();

				if (item.getQuantity() >= quantity) {
					item.setQuantity(item.getQuantity() - quantity);
					groceryItemRepository.save(item);

					OrderItem orderItem = new OrderItem();
					orderItem.setOrder(order);
					orderItem.setGroceryItem(item);
					orderItem.setQuantity(quantity);
					orderItemRepository.save(orderItem);
				} else {

					orderRepository.delete(order);
					return ResponseEntity.badRequest().body("Insufficient quantity for item of ID: " + itemId);
				}
			} else {
				orderRepository.delete(order);
				return ResponseEntity.badRequest().body("Item not found with ID: " + itemId);
			}
		}

		return ResponseEntity.ok().body("Order booked successfully");
	}

}
