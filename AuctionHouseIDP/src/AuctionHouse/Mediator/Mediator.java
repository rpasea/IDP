package AuctionHouse.Mediator;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {
	
	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;
	
	public Mediator(){
		// TODO
	}

	/*
	 * Methods of GUIMediator interface
	 */
	@Override
	public boolean isLoginValid(String user, String password, int role) {
                // Checks if credentials are valid
		// TODO
		return false;
	}
	
	
	/*
	 * Methods of NetworkMediator interface
	 */
	
	
	
	/*
	 * Methods of WebClientMediator interface
	 */
	
	
}
