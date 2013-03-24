package AuctionHouse.Messages;

public class LogoutMessage implements Message {

	@Override
	public MessageType getType() {
		return MessageType.Logout;
	}

}
