package AuctionHouse.DataContext;

import java.util.List;

public interface DataManager {
	public void init();
	public int getRole();
	public List<Service> doLogin(String user, String password, int role); 
	public Service getService(String service);
	public Person getIdentity();
}
