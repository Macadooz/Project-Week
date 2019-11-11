import java.util.*;

class Algorithm {
	public static void main(String[] args) {
		solve("jdbc:sqlite:pweek.db", true);
	}

	public static void solve(String url, boolean randomize) {
		Registrar reg = new Registrar(url, randomize);

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
