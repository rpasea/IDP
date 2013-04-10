package AuctionHouse.DataContext;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class GenerateXML {
	public static void main(String args[]) {

		List<ServiceEntry> entries = new LinkedList<ServiceEntry>();

		entries.add(new ServiceEntry("Ion", "No Offer", ""));
		entries.add(new ServiceEntry("Lulache", "No Offer", ""));
		entries.add(new ServiceEntry("Fragulea", "Offer Made", "100"));
		entries.add(new ServiceEntry("Bilioana", "No Offer", ""));

		Service service = new Service("BaniGratis", entries, "");

		List<Service> services = new LinkedList<Service>();
		services.add(service);

		Person person = new Person("gicu", "password", 1, services);

		List<Person> people = new LinkedList<Person>();
		people.add(person);

		entries = new LinkedList<ServiceEntry>();

		entries.add(new ServiceEntry("gicu", "No Offer", ""));
		entries.add(new ServiceEntry("Pamfila", "No Offer", ""));
		entries.add(new ServiceEntry("LotoProno", "No Offer", ""));
		entries.add(new ServiceEntry("Bilioana", "No Offer", ""));

		service = new Service("BaniGratis", entries, "");

		services = new LinkedList<Service>();
		services.add(service);

		person = new Person("Lulache", "password", 0, services);
		people.add(person);

		AuctionHouseDB db = new AuctionHouseDB(people);

		XStream xstream = new XStream(new StaxDriver());

		String xml = xstream.toXML(db);

		try {
			PrintWriter out = new PrintWriter("Database.xml");
			out.print(xml);
			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
