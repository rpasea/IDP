package AuctionHouse.Network;

import java.net.InetSocketAddress;
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
	
	public NetworkCommMediator(NetworkMediator mediator, String hostIp, int hostPort) {
		this.mediator = mediator;
		
		wsClient = new WebServiceClientMockup(this, hostIp, hostPort);
		netComm = new NetworkCommunicator(this, hostIp, hostPort);
	}
	
	/*
	 * Database related
	 */
	
	public List<Service> doLogin(String user, String password, int role) {
		List<Service> result = wsClient.doLogin(user, password, role);
		if(result != null) netComm.startRunning();
		
		return result;
	}
	
	public void doLogout(){
		netComm.stopRunning();
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
	 * WebService related
	 */
	
	public InetSocketAddress getPersonsAddress(String person){
		return wsClient.getPersonsAddress(person);
	}


	/*
	 * P2P communication related
	 */
	
	public void sendMakeOffer(String service, String buyer, String offer) {
		NetworkMessage netMsg = new MakeOfferNetworkMessage(service, buyer, offer);
		
		netComm.sendMessage(netMsg);
	}

}
