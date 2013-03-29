package AuctionHouse.GUI;

public class MenuStateManager {
	private MenuState state;
	private MainController mediator;
	
	public MenuStateManager(MainController mediator) {
		state = null;
		this.mediator = mediator;
	}
	
	public void setToDemandContextualMenu(int x, int y, String service, String supplier, String offer) {
		setToNoMenu();
		state = new DemandContextualMenu(mediator, x, y, service, supplier, offer);
		
	}
	
	public void setToOfferContextualMenu(int x, int y, String service, String supplier, String offer) {
		setToNoMenu();
		state = new SupplyContextualMenu(mediator, x, y, service, supplier, offer);
	}
	
	public void setToDemandServiceMenu(int x, int y, String service) {
		setToNoMenu();
		state = new DemandServiceMenu( mediator, x, y, service);
	}
	
	public void setToNoMenu() {
		if ( state != null) {
			state.killMenu();
			state = null;
		}
	}
	

}
