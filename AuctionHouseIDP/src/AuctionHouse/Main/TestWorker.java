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
		try {
			Thread.sleep(5000);
			t.setToStarted();
			Thread.sleep(5000);
			t.setToInProgress();

			Thread.sleep(5000);
			t.setToCompleted();

			Thread.sleep(5000);
			t.setToFailed();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
