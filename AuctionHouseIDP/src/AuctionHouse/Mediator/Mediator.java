package AuctionHouse.Mediator;

import AuctionHouse.Commands.*;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Messages.*;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;
	private int role;

	public Mediator() {
		controllerMediator = new ControllerMediator(this);
	}

	public void init() {
		controllerMediator.initGui();
	}
	
	private boolean isLoginValid(String user, String password, int role) {
		if (user.equals("gicu") && role == Mediator.ROL_CUMPARATOR)
			return true;
		return false;
	}

	/*
	 * Methods of GUIMediator interface
	 */

	@Override
	public Object sendGuiMessage(Message message) {
		Object result = null;
		switch (message.getType()) {
		case Login: {
			LoginMessage mess = (LoginMessage) message;
			if (!isLoginValid(mess.getUser(), mess.getPassword(),
					mess.getRole()))
				result = false;
			else {
				Command com = new LoginCommand(mess.getUser(),
						mess.getPassword(), mess.getRole(), controllerMediator);
				result = com.run();
				// if logged in, set the role
				if ((Boolean)result)
					this.role = mess.getRole();
			}
			break;
		}
		case Logout:
			result = controllerMediator.logout();
			break;
		}

		return result;
	}
	
	public int getRole() {
		return this.role;
	}

	/*
	 * Methods of NetworkMediator interface
	 */

	/*
	 * Methods of WebClientMediator interface
	 */

}
