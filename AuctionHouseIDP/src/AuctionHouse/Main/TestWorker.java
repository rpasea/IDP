package AuctionHouse.Main;

import javax.swing.SwingWorker;

import AuctionHouse.Mediator.Transaction;

public class TestWorker extends SwingWorker<Integer, Integer> {
	private Transaction t;
	
	public TestWorker(Transaction t) {
		this.t = t;
	}

	@Override
	protected Integer doInBackground() throws Exception {
	
		return 1;
	}
}
