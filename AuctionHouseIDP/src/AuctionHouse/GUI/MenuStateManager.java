package AuctionHouse.GUI;

public class MenuStateManager {
	private MenuState state;
	private ControllerMediator mediator;
	
	public MenuStateManager(ControllerMediator mediator) {
		state = null;
		this.mediator = mediator;
	}
	
	public void setToDemandContextualMenu() {
		
	}
	
	public void setToOfferContextualMenu() {
		
	}
	
	public void setToDemandServiceMenu() {
		
	}
	
	public void setToNoMenu() {
		if ( state != null) {
			state.killMenu();
			state = null;
		}
	}
	

}
