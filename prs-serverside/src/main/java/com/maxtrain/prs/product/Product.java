package com.maxtrain.prs.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.maxtrain.prs.vendor.Vendor;

@Entity(name="products")
@Table(uniqueConstraints=@UniqueConstraint(name="UIDX_partnbr", columnNames = {"partnbr"}))
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String partnbr;
	@Column(length=30, nullable=false)
	private String name;
	@Column(columnDefinition="decimal(10,2) NOT NULL DEFAULT 0.0")
	private double price;
	@Column(length=30, nullable=false)
	private String unit = "each";
	@Column(length=255)
	private String photopath;
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId")
	private Vendor vender;

	public Product() {}

	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }

	public String getPartnbr() { return partnbr; }
	public void setPartnbr(String partnbr) { this.partnbr = partnbr; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }

	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }

	public String getPhotopath() { return photopath; }
	public void setPhotopath(String photopath) { this.photopath = photopath; }

	public Vendor getVender() {	return vender; }
	public void setVender(Vendor vender) { this.vender = vender; }
	
	
}
