package AuctionHouse.GUI;

import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Messages.Message;

public class ControllerMediator {
	private Mediator mediator;
	private LoginController loginController = null;
	private MainController mainController = null;
	
	int simnr; // FIXME: Testing purpose
	
	public ControllerMediator(Mediator mediator,   int simnr) {
		this.mediator = mediator;
		
		this.simnr = simnr;
	}
	
	public void initGui() {
		LoginView view = new LoginView(this,   simnr);
		loginController = view.getController();
		view.setVisible(true);
	}
	
	public void switchToMain(AHTableModel model) {
		 MainView guiMainWindow = new MainView(model, this);
		 mainController = guiMainWindow.getController();
		 loginController.setVisibility(false);
		 guiMainWindow.setUsername(loginController.username);
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

	public void refreshGUI() {
		if (mainController != null)
			mainController.refresh();
		
	}
	
	public int getRole() {
		return mediator.getRole();
	}
	
	public AHTableModel getModel() {
		if (mainController == null)
			return null;
		return mainController.getModel();
	}

}
