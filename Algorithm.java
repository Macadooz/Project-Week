import java.util.*;

class Algorithm {
	public static void main(String[] args) {
		Registrar reg = new Registrar("jdbc:sqlite:pweek.db");

		Person nextP;
		//while registrar has more unsorted people
		while (reg.hasMorePeople()) {
			nextP = reg.getNextPerson();

			while (nextP != null) {
				nextP = reg.tryPlacePerson(nextP);
			}
		}
		//Do some stuff to return the values, probably going to have to save to a db or a file or something
		reg.outputResults();
	}
}
