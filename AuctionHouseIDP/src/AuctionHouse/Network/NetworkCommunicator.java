package AuctionHouse.Network;

import AuctionHouse.NetworkMessages.NetworkMessage;

/**
 * Communicates directly (p2p) with another user (of opposite role)
 * Also sends/receives services (files)
 */

public class NetworkCommunicator {
	private NetworkCommMediator netMediator;

	public NetworkCommunicator(NetworkCommMediator mediator){
		netMediator = mediator;
	}

	public void sendMessage(NetworkMessage netMsg) {
		// TODO Auto-generated method stub
		
	}
	
}
