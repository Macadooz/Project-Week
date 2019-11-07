import java.util.*;
import java.util.HashMap; 
import java.util.Map; 
import java.io.IOException;


class Registrar {
    ArrayList<Person> allPeople = new ArrayList<Person>();
    HashMap<Integer, Project> allCourses = new HashMap<>();
    ArrayList<Person> unluckyPeople = new ArrayList<Person>(); //People who were unable to be sorted into one of their choices
    Database db;
	int currentIndex;
    int sizeOfPeople;
    Random ran = new Random();

    /**
        Constructs Registrar object
    */
    public Registrar(String url) {
        db = new Database(url);

        ArrayList<Integer> tempList = db.getAllStudentIds();
        int seniorAvg = 0;
        int numSeniors = 0;

        for (int i=0; i < tempList.size(); i++) {
			allPeople.add(new Person(tempList.get(i), calculateScore(tempList.get(i))));
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
        StatWizard dylan = new StatWizard(db.getAllAverages());

        int prevScores[] = db.getPrevYears(studentId);
        int score=0;
        for (int i=0; i<3;i++){
            if (prevScores[i]>0) {
                //calculate to correct for the fact that we're using ints 
                //that have been multiplied by 100
                score += prevScores[i]*100;
            }
            else {
                //generate a random number to switch it up
                score += dylan.getNextNormalValue();
            }
        }
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
        Saver saver = new Saver("output.csv");

        ArrayList<Integer> tempList = db.getAllCourseIds();
        ArrayList outputStats = new ArrayList();
        ArrayList totalStats = new ArrayList();
        ArrayList<Person> studentsInProject = new ArrayList<Person>();

        Person curPerson = new Person(1);
        int scores[] = new int[9];

        saver.write(new ArrayList<>(Arrays.asList("ID", "ChoiceNum", "ProjID", "Gender", "Grade", "Score")));

        for (int i=0; i < tempList.size(); i++) {
            studentsInProject = allCourses.get(tempList.get(i)).getEnrolledStudents();
            for (int p=0; p<studentsInProject.size(); p++) {
                curPerson = studentsInProject.get(p);
                outputStats.clear();
                
                outputStats.add(curPerson.getStudentID()); //Id
                outputStats.add(curPerson.getCurrentPreference()); //Choice
                outputStats.add(tempList.get(i)); //projectId
                outputStats.add(db.getGender(curPerson.getStudentID())); //Gender
                outputStats.add(db.getGrade(curPerson.getStudentID())); //grade
                outputStats.add((curPerson.getScore())); //score

                scores[curPerson.getCurrentPreference()-1]++;
                saver.write(outputStats);
            }
        }

        // System.out.println(unluckyPeople.size());
        for (int i=0; i<unluckyPeople.size(); i++) {
            curPerson = unluckyPeople.get(i);
            outputStats.clear();
            
            outputStats.add(curPerson.getStudentID());
            outputStats.add(curPerson.getCurrentPreference());
            outputStats.add(-1);
            outputStats.add(db.getGender(curPerson.getStudentID()));
            outputStats.add(db.getGrade(curPerson.getStudentID()));
            outputStats.add((curPerson.getScore())); //score

            scores[curPerson.getCurrentPreference()-1]++;
            saver.write(outputStats);
        }

        saver.close();

        for (int i=0; i<9; i++) {
            System.out.println("Choice"+(i+1)+": "+scores[i]);
        }
    }
}
