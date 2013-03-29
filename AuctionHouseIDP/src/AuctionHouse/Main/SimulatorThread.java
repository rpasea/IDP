package AuctionHouse.Main;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Messages.*;

public class SimulatorThread extends Thread {
	
	private Mediator mediator;
	private ControllerMediator controllerMediator;
	private DataManager dataManager;
	
	public int role;

	public SimulatorThread(Mediator mediator, ControllerMediator controllerMediator, DataManager dataManager){
		this.mediator = mediator;
		this.controllerMediator = controllerMediator;
		this.dataManager = dataManager;
	}
	
	private void sleep(int milis){
		// Scap de enervantele try-catchuri
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		if(role == Mediator.ROL_CUMPARATOR){
			System.out.println("-----------------------------");
			System.out.println("Simulare CUMPARATOR inceputa.");
			System.out.println("-----------------------------");
			
			sleep(5000);
			
			mediator.sendGuiMessage(new LaunchAuctionMessage("BaniGratis"));
			
			sleep(5000);
			
			mediator.sendGuiMessage(new DropAuctionMessage("BaniGratis"));
			
			sleep(5000);
			
			mediator.sendGuiMessage(new LaunchAuctionMessage("BaniGratis"));
			
			sleep(5000);
			
			mediator.sendGuiMessage(new AcceptOfferMessage("BaniGratis", "Fragulea", "100"));
			
			sleep(20000);
			
			mediator.sendGuiMessage(new DropAuctionMessage("BaniGratis"));
		} else {
			System.out.println("---------------------------");
			System.out.println("Simulare FURNIZOR inceputa.");
			System.out.println("---------------------------");

			sleep(1000);
			
			mediator.sendGuiMessage(new MakeOfferMessage("BaniGratis", "Pamfila", "500"));
			
			
		}
	}
}
