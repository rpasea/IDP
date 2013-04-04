package AuctionHouse.Main;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Messages.*;

public class SimulatorThread extends Thread {
	
	private Mediator mediator;
	private ControllerMediator controllerMediator;
	
	public int role;

	public SimulatorThread(Mediator mediator, ControllerMediator controllerMediator){
		this.mediator = mediator;
		this.controllerMediator = controllerMediator;
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
//			System.out.println("-----------------------------");
//			System.out.println("Simulare CUMPARATOR inceputa.");
//			System.out.println("-----------------------------");
//			
//			sleep(5000);
//			
//			mediator.sendGuiMessage(new LaunchAuctionMessage("BaniGratis"));
		} else {
//			System.out.println("---------------------------");
//			System.out.println("Simulare FURNIZOR inceputa.");
//			System.out.println("---------------------------");
		}
	}
}
