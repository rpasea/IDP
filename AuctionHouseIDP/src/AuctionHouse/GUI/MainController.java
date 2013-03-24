package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;
import Messages.LoginMessage;
import Messages.LogoutMessage;


public class MainController {
    private MainView view;
    private AHTableModel tableModel;
    private ControllerMediator mediator;
    private LoginController loginController;
    
    public MainController(MainView view, AHTableModel model,
            ControllerMediator med, LoginController loginController){
        this.view = view;
        tableModel = model;
        mediator = med;
        this.loginController = loginController;
    }
    
    public void logout(){
    	LogoutMessage msg = new LogoutMessage();
        mediator.sendMessage(msg);
    }
    
    public void setVisibility(boolean value){
        view.setVisible(value);
    }
    
    public void tableClicked(java.awt.event.MouseEvent evt) {
    	System.out.println("Cliiiiiick!");
    }
}
