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
    StatWizard dylan;
    

    /**
        Constructs Registrar object, given a database url, and shuffle, which indicates if the list of people 
        should be randomized at runtime
    */
    public Registrar(String url, boolean shuffle) {
        db = new Database(url);

        ArrayList<Integer> tempList = db.getAllStudentIds();
        dylan = new StatWizard(db.getAllAverages());

        int seniorAvg = 0;
        int numSeniors = 0;

        for (int i=0; i < tempList.size(); i++) {
			allPeople.add(new Person(tempList.get(i), calculateScore(tempList.get(i))));
        }

        //Randomize the list, if shuffle == true
        if (shuffle)
             Collections.shuffle(allPeople); 

        //set the templist of the course ids, to iterate through
        tempList = db.getAllCourseIds();
        int pid;
        for (int i=0; i < tempList.size(); i++) {
            pid = tempList.get(i);                                          //create new project, put it into the hastable <courseID, course>
			allCourses.put(pid, new Project(pid, db.getMaxStudents(pid)));
		}

        currentIndex = 0;
        sizeOfPeople = allPeople.size();
    }

    /**
        private int calculateScore(int studentId)

        Calculates the score for a given student ID
        Calculated by sum of previous years *100
        If one year they did not go on project, add random value from standard distributon of scores
    */
    private int calculateScore(int studentId) {
        int prevScores[] = db.getPrevYears(studentId); //Get previous year placements (NOTE: Can Possibly be saved into an object at the beginning)
        int score=0;
        for (int i=0; i<3;i++){
            if (prevScores[i]>0) {
                score += prevScores[i]*100; //multiply by 100, since we committed to using ints, and we need at least 2 figures of precison
            }
            else {
                //generate a random number to switch it up, from the StatWizard object
                score += Math.abs(dylan.getNextNormalValue() * 100);
            }
        }
        return score;
    }

    /**
        public Person tryPlacePerson(Person p)

        Attempts to place a person p into their next project
    */
    public Person tryPlacePerson(Person p) {
        //first check if the person has more choices; if they are at 9, give up because they're out of choices
        //add them to unlucky people
        if ( p.getCurrentPreference() > 8) {
            unluckyPeople.add(p);
            return null;
        }

        int nextPrefChoice = db.getPreference(p.getStudentID(), p.getCurrentPreference());
        if (nextPrefChoice == Integer.MIN_VALUE || nextPrefChoice == 0) {   //if the next choice is empty or null, give up, since presumably they are out of choices
            unluckyPeople.add(p);                                         
            return null;
        }

        Project nextProject = allCourses.get(nextPrefChoice);

        //if there is an open spot in the person's next project
        if (!nextProject.isFull()) {
            nextProject.addStudent(p);
            return null;
        }

        //get the person with the lowest score NOTE: This can be refactored, perhaps using insertionsort to 
        //make it faster to get the person with the lowest score
        Person lowestPerson = nextProject.getLowestScorePerson();

        if (lowestPerson.getScore() < p.getScore()) {
            nextProject.removeStudent(lowestPerson);
            nextProject.addStudent(p);

            lowestPerson.increaseCurrentPreference(); //tell the person to advance to their next peference
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

    /**
        public void printProjects()

        print all of the projects and their people.
    */
    public void printProjects() {
        ArrayList<Integer> tempList = db.getAllCourseIds();
        for (int i=0; i<tempList.size(); i++)
            System.out.println(allCourses.get(tempList.get(i)));
        System.out.println(unluckyPeople);
    }

    /**
        public void outputResults(String url)

        Save the results to a CSV called 'output.csv'

        Currently saves "ID", "ChoiceNum", "ProjID", "Gender", "Grade", "Score"

        Also prints out the numbers of people in each num
    */
    public void outputResults(String url) {
        Saver saver = new Saver(url);

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
