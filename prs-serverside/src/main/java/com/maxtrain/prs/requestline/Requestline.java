package com.maxtrain.prs.requestline;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.maxtrain.prs.product.Product;
import com.maxtrain.prs.request.Request;

@Entity(name="requestline")
public class Requestline {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity = 0;
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId")
	private Request request;
	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	private Product product;
	
	public Requestline() {}

	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }

	public int getQuantity() { return quantity;	}
	public void setQuantity(int quantity) {	this.quantity = quantity; }

	public Request getRequest() { return request; }
	public void setRequest(Request request) { this.request = request; }

	public Product getProduct() { return product; }
	public void setProduct(Product product) { this.product = product; }
	
	
}
