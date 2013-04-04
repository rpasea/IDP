package AuctionHouse.Network;

import java.awt.Component;
import java.util.List;

import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;

/**
 * Communicates with WebService for login, database info and p2p
 * connections requests
 */

public class WebServiceClient {

	public WebServiceClient(NetworkCommMediator networkMediator){
		
	}

	public List<Service> doLogin(String user, String password, int role) {
		// TODO Auto-generated method stub
		return null;
	}

	public Service getService(String service) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person getIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRole() {
		// TODO Auto-generated method stub
		return 0;
	}
}
