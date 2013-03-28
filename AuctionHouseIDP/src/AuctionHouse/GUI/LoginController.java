package AuctionHouse.GUI;

import AuctionHouse.Messages.LoginMessage;


public class LoginController {
	public String username;
	
    private LoginView view;
    private ControllerMediator mediator;
    
    public LoginController(LoginView associatedView, ControllerMediator med){
        view = associatedView;
        mediator = med;
    }
    
    public boolean login(String user, String password, int role){
    	username = user;
    	
    	// Checks login credentials with the mediator
    	LoginMessage msg = new LoginMessage(user,password,role);
    	
        return (Boolean)mediator.sendMessage(msg);
    }
    
    public void setVisibility(boolean value){
        view.setVisible(value);
    }
}
