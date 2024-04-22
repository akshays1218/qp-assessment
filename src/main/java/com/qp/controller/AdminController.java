package com.qp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qp.entity.GroceryItem;
import com.qp.repository.GroceryItemRepository;
import com.qp.request.GroceryItemRequestDTO;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	GroceryItemRepository groceryItemRepository;
	
	@PostMapping("/save/items")
    public ResponseEntity<?> addItem(@RequestBody GroceryItem newItem) {
        GroceryItem savedItem = groceryItemRepository.save(newItem);
        return ResponseEntity.ok().body(savedItem);
    }

	@GetMapping("/items")
    public ResponseEntity<?> getItems() {
        List<GroceryItem> items = groceryItemRepository.findAll();
        return ResponseEntity.ok().body(items);
    }

	@DeleteMapping("/delete/items/{itemId}")
	public ResponseEntity<?> removeItem(@PathVariable(name="itemId") Long itemId) {
		Optional<GroceryItem> itemOptional = groceryItemRepository.findById(itemId);
		if (itemOptional.isPresent()) {
			groceryItemRepository.deleteById(itemId);
			return ResponseEntity.ok().body("Item removed successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/update/items/{itemId}")
	public ResponseEntity<?> updateItem(@PathVariable(name="itemId") Long itemId, @RequestBody GroceryItemRequestDTO updatedItem) {
		Optional<GroceryItem> itemOptional = groceryItemRepository.findById(itemId);
		if (itemOptional.isPresent()) {
			GroceryItem item = itemOptional.get();
			item.setName(updatedItem.getName());
			item.setPrice(updatedItem.getPrice());
			item.setQuantity(updatedItem.getQuantity());
			groceryItemRepository.save(item);
			return ResponseEntity.ok().body("Item updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/manage/items/{itemId}")
	public ResponseEntity<?> manageInventory(@PathVariable(name="itemId") Long itemId,@RequestParam(name="quantity") int quantity) {
		Optional<GroceryItem> itemOptional = groceryItemRepository.findById(itemId);
		if (itemOptional.isPresent()) {
			GroceryItem item = itemOptional.get();
			int updatedQuantity = item.getQuantity() + quantity;
			if (updatedQuantity < 0) {
				return ResponseEntity.badRequest().body("Inventory quantity cannot be negative");
			}
			item.setQuantity(updatedQuantity);
			groceryItemRepository.save(item);
			return ResponseEntity.ok().body("Inventory quantity updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
