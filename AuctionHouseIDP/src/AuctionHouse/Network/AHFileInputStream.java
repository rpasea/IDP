package AuctionHouse.Network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import AuctionHouse.Mediator.Transaction;

public class AHFileInputStream extends FileInputStream {
	private Transaction transaction;

	public AHFileInputStream(String arg0, Transaction t) throws FileNotFoundException {
		super(arg0);
		this.transaction = t;

	}
	
	public AHFileInputStream(File f, Transaction t) throws FileNotFoundException {
		super(f);
		this.transaction = t;

	}
	
	@Override
	public int read (byte[] b, int offset, int length) throws IOException {
		int res = super.read(b, offset, length);
		if (transaction != null)
			transaction.addProgress(res);
		return res;
	}

}
