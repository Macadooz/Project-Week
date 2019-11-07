import java.util.*;
import java.util.HashMap; 
import java.util.Map; 

class Registrar {
    ArrayList<Person> allPeople = new ArrayList<Person>();
    HashMap<Integer, Project> allCourses = new HashMap<>();
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
			allPeople.add(new Person(tempList.get(i), calculateScore(tempList.get(i))));
            // System.out.println(tempList.get(i));
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

    private int calculateScore(int studentId) {
        int prevScores[] = db.getPrevYears(studentId);
        int score=0;
        for (int i=0; i<3;i++){
            if (prevScores[i]>0) {
                score += prevScores[i];
            }
            else {
                score += 3;
            }
        }

        score *= 100;
        score /= 3;

        return score;
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
        if (nextPrefChoice == Integer.MIN_VALUE || nextPrefChoice == 0) {
            unluckyPeople.add(p);
            return null;
        }

        Project nextProject = allCourses.get(nextPrefChoice);

        if (!nextProject.isFull()) {
            nextProject.addStudent(p);
            return null;
        }

        Person lowestPerson = nextProject.getLowestScorePerson();

        if (lowestPerson.getScore() < p.getScore()) {
            nextProject.removeStudent(lowestPerson);
            nextProject.addStudent(p);

            lowestPerson.increaseCurrentPreference();
            return lowestPerson;
        }        
        p.increaseCurrentPreference();
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

    public void printProjects() {
        ArrayList<Integer> tempList = db.getAllCourseIds();
        for (int i=0; i<tempList.size(); i++)
            System.out.println(allCourses.get(tempList.get(i)));
    }

    public void printBadPeople() {
        System.out.println(unluckyPeople);
    }

    public void outputResults() {
        ArrayList<Integer> tempList = db.getAllStudentIds();
        int scores[] = new int[9];
        for (int i=0; i < tempList.size(); i++) {
            scores[allPeople.get(i).getCurrentPreference()-1]++;
        }
        for (int i=0; i<9; i++) {
            System.out.println("Choice"+(i+1)+": "+scores[i]);
        }
    }
}

//place person, getNextPerson
//maybe entire  Algorithm?
//how much do we want registrar to do?????
//registrar = no main  fucntion
//while registrar has more people place next person
