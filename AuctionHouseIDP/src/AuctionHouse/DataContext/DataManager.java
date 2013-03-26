package AuctionHouse.DataContext;

import java.util.List;

public interface DataManager {
	public void init();
	public boolean isLoginValid(String user, String password, int role);
	public int getRole();
	public List<Service> doLogin(String user, String password, int role); 
}
