package AuctionHouse.Network;

import java.awt.Component;
import java.util.List;

import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;
import AuctionHouse.Mediator.Mediator;

/**
 * Mediates network workers with main mediator
 */

public class NetworkCommMediator {
	private Mediator mediator;
	private WebServiceClient wsClient;
	
	public NetworkCommMediator(Mediator mediator) {
		this.mediator = mediator;
		
		wsClient = new WebServiceClientMockup(this);
	}

	public List<Service> doLogin(String user, String password, int role) {
		return wsClient.doLogin(user, password, role);
	}

	public Service getService(String service) {
		return wsClient.getService(service);
	}

	public Person getIdentity() {
		return wsClient.getIdentity();
	}

	public int getRole() {
		return wsClient.getRole();
	}

}
