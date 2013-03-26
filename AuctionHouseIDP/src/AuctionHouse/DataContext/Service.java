package AuctionHouse.DataContext;

import java.util.List;

public class Service {
	String name;
	List<ServiceEntry> entries;
	
	public Service( String name, List<ServiceEntry> entries) {
		this.name = name;
		this.entries = entries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ServiceEntry> entries) {
		this.entries = entries;
	}
}
