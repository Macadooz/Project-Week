import java.util.*;

class Algorithm {
	public static void main(String[] args) {
		Registrar reg = new Registrar("jdbc:sqlite:pweek.db");

		Person nextP;
		while (reg.hasMorePeople()) {
			nextP = reg.getNextPerson();

			while (nextP != null) {
				nextP = reg.tryPlacePerson(nextP);
			}
		}
		System.out.println(test);
	}
}
