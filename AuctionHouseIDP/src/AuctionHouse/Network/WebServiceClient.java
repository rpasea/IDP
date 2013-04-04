package AuctionHouse.Network;

import java.net.InetSocketAddress;
import java.util.List;

import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;

/**
 * Communicates with WebService for login, database info and p2p
 * connections requests
 */

public class WebServiceClient {

	public WebServiceClient(NetworkCommMediator networkMediator,
				String hostIp, int hostPort){
		
		// TODO: Register myself (and my host details) to the webservice
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

	public InetSocketAddress getPersonsAddress(String person) {
		// TODO Auto-generated method stub
		return null;
	}
}
