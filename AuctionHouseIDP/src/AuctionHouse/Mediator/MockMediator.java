package AuctionHouse.Mediator;

public class MockMediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	public MockMediator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLoginValid(String user, String password, int category) {
		if(user.equals("gicu") && category == Mediator.ROL_FURNIZOR)
			return true;
		return false;
	}

}
