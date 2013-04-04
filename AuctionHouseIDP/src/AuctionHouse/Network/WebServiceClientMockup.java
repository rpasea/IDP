package AuctionHouse.Network;

import java.awt.Component;
import java.util.List;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.XMLDataManager;

public class WebServiceClientMockup extends WebServiceClient {
	private DataManager dataManager;
	private NetworkCommMediator netMediator;
	
	public WebServiceClientMockup(NetworkCommMediator networkMediator){
		super(networkMediator);
		
		netMediator = networkMediator;
		
		// For database access, webservice would be used
		dataManager = new XMLDataManager("Database.xml");
	}
	
	public List<Service> doLogin(String user, String password, int role) {
		return dataManager.doLogin(user, password, role);
	}
	

	public Service getService(String service) {
		return dataManager.getService(service);
	}

	public Person getIdentity() {
		return dataManager.getIdentity();
	}

	public int getRole() {
		return dataManager.getRole();
	}
}
