package com.maxtrain.prs.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.maxtrain.prs.user.User;

@Entity(name = "requests")
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=80, nullable=false)
	private String description;
	@Column(length=80, nullable=false)
	private String justification;
	@Column(length=80)
	private String rejectionreason;
	@Column(length=20, nullable=false)
	private String deliverymode = "Pickup";
	@Column(length=10, nullable=false)
	private String status = "New";
	@Column(columnDefinition="decimal(10,2) NOT NULL DEFAULT 0.0")
	private double total = 0;
	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	private User user;
	
	public Request() {}

	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getJustification() { return justification; }
	public void setJustification(String justification) { this.justification = justification; }

	public String getRejectionreason() { return rejectionreason; }
	public void setRejectionreason(String rejectionreason) { this.rejectionreason = rejectionreason; }

	public String getDeliverymode() { return deliverymode; }
	public void setDeliverymode(String deliverymode) { this.deliverymode = deliverymode; }

	public String getStatus() {	return status; }
	public void setStatus(String status) { this.status = status; }

	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	
	
}
