import java.util.*;
import java.util.HashMap; 
import java.util.Map; 

class Registrar {
    ArrayList<Person> allPeople = new ArrayList<Person>();
    ArrayList<Project> allCourses = new ArrayList<Project>();
    HashMap<Integer, Person> allCourses = new HashMap<>();
    ArrayList<Person> unluckyPeople = new ArrayList<Person>(); //People who were unable to be sorted into one of their choices
    Database db;
	int currentIndex;
    int sizeOfPeople;

    /**
        Constructs Registrar object
    */
    public Registrar(String url) {
        db = new Database(url);

        ArrayList<Integer> tempList = db.getAllStudentIds();
        for (int i=0; i < tempList.size(); i++) {
			allPeople.add(new Person(tempList.get(i)));
		}

        tempList = db.getAllCourseIds();
        int pid;
        for (int i=0; i < tempList.size(); i++) {
            pid = tempList.get(i);
			allCourses.put(pid, new Project(pid, db.getMaxStudents(pid)));
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
        if ( p.getCurrentPreference() > 8) {
            unluckyPeople.add(p);
            return null;
        }

        int nextPrefChoice = db.getPreference(p.getStudentID(), p.getCurrentPreference());

        Project nextProject = allCourses.get(nextPrefChoice);

        if (!nextProject.isFull()) {
            nextProject.addStudent(p);
            return null;
        }

        Person lowestPerson = nextProject.getLowestPerson();

        if (lowestPerson.getScore() < p.getScore()) {
            nextProject.removeStudent(lowestPerson);
            nextProject.addStudent(p);
            return lowestPerson;
        }        

        return p; //for now
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
