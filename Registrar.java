import java.util.*;

class Registrar {
    ArrayList<Person> allPeople = new ArrayList();
    ArrayList<Project> allCourses = new ArrayList();
    ArrayList<Person> unluckyPeople = new ArrayList(); //People who were unable to be sorted into one of their choices
    Database db;
	int currentIndex;
    int sizeOfPeople;

    /**
        Constructs Registrar object
    */
    public Registrar(String url) {
        db = new Database(url);

        ArrayList tempList = db.getAllStudentIds();
        for (int i=0; i< tempList.size(); i++) {
			allPeople.add(new Person(tempList.get(i)));
		}

        tempList = db.getAllCourseIds();
        int pid;
        for (int i=0; i< tempList.size(); i++) {
            pid = tempList.get(i);
			allCourses.add(new Project(pid, db.getMaxStudents(pid)));
		}

        currentIndex = 0;
        sizeOfPeople = allPeople.size();
    }

    /**
        public Person tryPlacePerson(Person p)

        Attempts to place a person p into the
    */
    public Person tryPlacePerson(Person p) {
        //first check if the person has more choices
        if (p.getCurrentPreference() > 8) {
            unluckyPeople.add(p);
            return null;
        }

        //then try to actually place them
    }

    /**
        public boolean hasMorePeople()

        returns true or false depending on if there are more unsorted people

        this is defined by if current index < number of people
    */
    public boolean hasMorePeople() {
        return currentIndex < sizeOfPeople;
    }

    /**
        public Person getNextPerson()

        Returns the next unsorted person

        Precondition: Registrar has more allPeople
    */
    public Person getNextPerson() {
        return allPeople.get(currentIndex++);
    }
}

//place person, getNextPerson
//maybe entire  Algorithm?
//how much do we want registrar to do?????
//registrar = no main  fucntion
//while registrar has more people place next person
