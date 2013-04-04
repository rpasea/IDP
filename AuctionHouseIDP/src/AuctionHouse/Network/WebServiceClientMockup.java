package AuctionHouse.Network;

import java.net.InetSocketAddress;
import java.util.List;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Person;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.XMLDataManager;

public class WebServiceClientMockup extends WebServiceClient {
	private DataManager dataManager;
	private NetworkCommMediator netMediator;
	private InetSocketAddress destAddr;
	
	public WebServiceClientMockup(NetworkCommMediator networkMediator,
				String hostIp, int hostPort){
		super(networkMediator, hostIp, hostPort);
		
		if(hostPort == 50001)
			destAddr = new InetSocketAddress("127.0.0.1", 50002);
		else
			destAddr = new InetSocketAddress("127.0.0.1", 50001);
		
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
	
	@Override
	public InetSocketAddress getPersonsAddress(String person){
		return destAddr;
	}
}
