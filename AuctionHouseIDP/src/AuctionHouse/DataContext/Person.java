package AuctionHouse.DataContext;

import java.util.List;

public class Person {
	String name, password;
	int role;
	List<Service> services;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public Person (String name, String password, int role, List<Service> services) {
		this.name = name;
		this.password = password;
		this.role = role;
		this.services = services;
	}
}
