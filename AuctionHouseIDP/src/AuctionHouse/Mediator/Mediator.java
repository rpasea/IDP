package AuctionHouse.Mediator;

import AuctionHouse.GUI.ControllerMediator;
import Commands.*;
import Messages.*;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;

	public Mediator() {
		controllerMediator = new ControllerMediator(this);
	}

	public void init() {
		controllerMediator.initGui();
	}

	/*
	 * Methods of GUIMediator interface
	 */
	private boolean isLoginValid(String user, String password, int role) {
		if (user.equals("gicu") && role == Mediator.ROL_FURNIZOR)
			return true;
		return false;
	}

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
			}
			break;
		}
		case Logout:
			result = controllerMediator.logout();
			break;
		}

		return result;
	}

	/*
	 * Methods of NetworkMediator interface
	 */

	/*
	 * Methods of WebClientMediator interface
	 */

}
