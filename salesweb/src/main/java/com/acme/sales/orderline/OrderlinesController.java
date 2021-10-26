package com.acme.sales.orderline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.sales.order.OrderRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/orderlines")
public class OrderlinesController {
	
	@Autowired
	private OrderlineRepository orderlineRepo;
	@Autowired
	private OrderRepository orderRepo;
	@GetMapping
	public ResponseEntity<Iterable<Orderline>> GetAll() {
		var orderlines = orderlineRepo.findAll();
		return new ResponseEntity<Iterable<Orderline>>(orderlines,HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Orderline> GetById(@PathVariable int id) {
		var orderline = orderlineRepo.findById(id);
		if(orderline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orderline>(orderline.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Orderline> Insert(@RequestBody Orderline orderline) throws Exception {
		if(orderline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		orderline.setId(0);
		var newOrderline = orderlineRepo.save(orderline);
		RecalculateOrder(orderline.getOrder().getId());
		return new ResponseEntity<Orderline>(newOrderline, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity Update(@PathVariable int id, @RequestBody Orderline orderline) throws Exception {
		if(orderline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var oldOrderline = orderlineRepo.findById(orderline.getId());
		if(oldOrderline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		orderlineRepo.save(orderline);
		RecalculateOrder(orderline.getOrder().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity Delete(@PathVariable int id) throws Exception {
		var orderline = orderlineRepo.findById(id);
		if(orderline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		orderlineRepo.deleteById(id);
		RecalculateOrder(orderline.get().getOrder().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	private void RecalculateOrder(int orderId) throws Exception {
		var optOrder = orderRepo.findById(orderId);
		if(optOrder.isEmpty()) {
			throw new Exception("Order id is invalid");
		}
		var order = optOrder.get();
		var orderLines = orderlineRepo.findOrderlineByOrderId(orderId);
		var total = 0;
		for(var orderLine : orderLines) {
			total += orderLine.getQuantity() * orderLine.getPrice();
		}
		order.setTotal(total);
		orderRepo.save(order);
	}
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

