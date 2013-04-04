package AuctionHouse.Network;

import java.util.List;

import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;
import AuctionHouse.Mediator.NetworkMediator;
import AuctionHouse.NetworkMessages.MakeOfferNetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessage;

/**
 * Mediates network workers with main mediator
 */

public class NetworkCommMediator {
	private NetworkMediator mediator;
	private WebServiceClient wsClient;
	private NetworkCommunicator netComm;
	
	public NetworkCommMediator(NetworkMediator mediator) {
		this.mediator = mediator;
		
		wsClient = new WebServiceClientMockup(this);
		netComm = new NetworkCommunicator(this);
	}
	
	/*
	 * Database related
	 */
	
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


	/*
	 * P2P communication related
	 */
	
	public void sendMakeOffer(String service, String buyer, String offer) {
		NetworkMessage netMsg = new MakeOfferNetworkMessage(service, buyer, offer);
		
		netComm.sendMessage(netMsg);
	}

}
