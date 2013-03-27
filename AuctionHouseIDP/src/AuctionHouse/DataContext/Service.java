package AuctionHouse.DataContext;

import java.util.List;

public class Service {
	String name;
	String status;
	List<ServiceEntry> entries;
	
	public Service( String name, List<ServiceEntry> entries, String status) {
		this.name = name;
		this.entries = entries;
		this.status = status;
	}
	
	public ServiceEntry getEntry(String personName) {
		for (ServiceEntry se : entries) {
			if (se.getPerson().equals(personName))
				return se;
		}
		
		return null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
