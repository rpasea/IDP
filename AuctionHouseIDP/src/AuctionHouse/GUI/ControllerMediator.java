package AuctionHouse.GUI;

import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Messages.Message;

public class ControllerMediator {
	private Mediator mediator;
	private LoginController loginController = null;
	private MainController mainController = null;
	
	public ControllerMediator(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void initGui() {
		LoginView view = new LoginView(this);
		loginController = view.getController();
		view.setVisible(true);
	}
	
	public void switchToMain(AHTableModel model) {
		 MainView guiMainWindow = new MainView(model, this, loginController);
		 mainController = guiMainWindow.getController();
		 loginController.setVisibility(false);
		 guiMainWindow.setVisible(true);
	}
	
	public Object sendMessage(Message mess) {
		return mediator.sendGuiMessage(mess);
	}
	
	public boolean logout() {
		if (mainController != null)
			mainController.setVisibility(false);
		if (loginController != null)
			loginController.setVisibility(true);
		
		return true;
	}

}
