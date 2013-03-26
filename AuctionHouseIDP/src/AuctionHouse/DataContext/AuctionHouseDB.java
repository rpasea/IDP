package AuctionHouse.DataContext;

import java.util.List;

public class AuctionHouseDB {
	List<Person> people;
	
	public AuctionHouseDB( List<Person> people) {
		this.people = people;
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}
}
