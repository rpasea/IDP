/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;
import AuctionHouse.Messages.LoginMessage;

import java.util.LinkedList;

/**
 *
 * @author iulius
 */
public class LoginController {
    private LoginView view;
    private ControllerMediator mediator;
    
    public LoginController(LoginView associatedView, ControllerMediator med){
        view = associatedView;
        mediator = med;
    }
    
    public boolean login(String user, String password, int role){
        // Checks login credentials with the mediator
    	
    	LoginMessage msg = new LoginMessage(user,password,role);
    	
        return (Boolean)mediator.sendMessage(msg);
    }
    
    public void setVisibility(boolean value){
        view.setVisible(value);
    }
}
