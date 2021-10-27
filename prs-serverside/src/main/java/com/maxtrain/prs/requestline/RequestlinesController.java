package com.maxtrain.prs.requestline;

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


import com.maxtrain.prs.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestlinesController {
	
	@Autowired
	private RequestlineRepository requestlineRepo;
	@Autowired
	private RequestRepository requestRepo;
	
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> GetAll() {
		var requestlines = requestlineRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(requestlines,HttpStatus.OK);
	}
	@GetMapping("{id}")
	public ResponseEntity<Requestline> GetById(@PathVariable int id) {
		var requestline = requestlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Requestline>(requestline.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Requestline> Insert(@RequestBody Requestline requestline) throws Exception {
		if(requestline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		requestline.setId(0);
		var newRequestline = requestlineRepo.save(requestline);
		RecalculateTotal(requestline.getRequest().getId());
		return new ResponseEntity<Requestline>(newRequestline, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity Update(@PathVariable int id, @RequestBody Requestline orderline) throws Exception {
		if(orderline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var requestline = requestlineRepo.findById(id);
		var oldRequestline = requestlineRepo.findById(orderline.getId());
		if(oldRequestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		requestlineRepo.save(orderline);
		RecalculateTotal(requestline.get().getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity Delete(@PathVariable int id) throws Exception {
		var requestline = requestlineRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		requestlineRepo.deleteById(id);
		RecalculateTotal(requestline.get().getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	private void RecalculateTotal(int requestId) throws Exception {
		var optRequest = requestRepo.findById(requestId);
		if(optRequest.isEmpty()) {
			throw new Exception("Request id is invalid");
		}
		var request = optRequest.get();
		var requestLines = requestlineRepo.findRequstlineByRequestId(requestId);
		var total = 0;
		for(var requestLine : requestLines) {
			total += requestLine.getProduct().getPrice() * requestLine.getQuantity();
		}
		request.setTotal(total);
		requestRepo.save(request);

		
	}

}
