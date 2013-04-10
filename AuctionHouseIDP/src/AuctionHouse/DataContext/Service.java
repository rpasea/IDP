package AuctionHouse.DataContext;

import java.util.List;

public class Service {
	private String name;
	private String status;
	private List<ServiceEntry> entries;
	
	private boolean active;
	
	public Service( String name, List<ServiceEntry> entries, String status) {
		this.name = name;
		this.entries = entries;
		this.status = status;
		active = false;
		
		for(ServiceEntry e : entries)
			e.setService(this);
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
