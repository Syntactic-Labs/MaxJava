package com.maxtrain.tutorial.models;

public class PrimeCustomer extends Customer {

	public PrimeCustomer(String name) {
		super("Prime " + name);
	}
	public PrimeCustomer(String name, double sales) {
		super("Prime " + name, sales);
	}
}
