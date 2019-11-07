import java.util.*;

class Registrar {
    ArrayList<Person> allPeople = new ArrayList<Person>();
    HashMap<Integer, Project> allCourses = new HashMap<Integer, Project>();
    ArrayList<Person> unluckyPeople = new ArrayList<Person>(); //People who were unable to be sorted into one of their choices
    Database db;
	int currentIndex;
    int sizeOfPeople;

    /**
        Constructs Registrar object
    */
    public Registrar(String url) {
        db = new Database(url);

		//setup list of People
        ArrayList<Integer> tempList = db.getAllStudentIds();
        for (int i=0; i < tempList.size(); i++) {
			allPeople.add(new Person(tempList.get(i)));
		}
		
	//setup list of Projects

        tempList = db.getAllCourseIds();
        int pid;
        for (int i=0; i < tempList.size(); i++) {
            pid = tempList.get(i);
	    int max = db.getMaxStudents(pid);
	   
			allCourses.put(tempList.get(i), new Project(pid, max));
		}

        this.currentIndex = 0;
        this.sizeOfPeople = allPeople.size();
    }
	public void status(){
	//	for(Project p : allCourses.values()){
	//		System.out.println(p.getEnrolledStudents().size());
	//	}

	//	System.out.println("Number of unlucky students " + unluckyPeople.size());
	
		int[] x = {0,0,0,0,0,0,0,0};
		
		for(Person p : allPeople){
				if (p.getCurrentPreference()-1 == 8){
				continue;
				}
				x[p.getCurrentPreference()-1]++;
		}

		for (int i =0; i < 8 ;  i++){
			int t = i+1;
			System.out.println("Number of people who got their #" + t + " choice: " + x[i]);	
		}
		
	}


    /**
        public Person tryPlacePerson(Person p)

        Attempts to place a person p into the
    */
    public Person tryPlacePerson(Person applicant) {
        //first check if the person has more choices
    	if (applicant.getCurrentPreference() > 8) {
            unluckyPeople.add(applicant);
            return null;
    	}

	
		//lookup which project
   		Integer pid = db.getPreference(applicant.getStudentID(), applicant.getCurrentPreference());
		if(pid == -1){
			return getNextPerson();

		}
	
		//then try to actually place them
    		Project test = allCourses.get(pid);
	
		if (test.getEnrolledStudents().size() < test.getMaxStudents()){	
			test.addStudent(applicant);	

	
			return null;	
		}
	
		if (applicant.getScore() > test.getLowestStudent().getScore()){
			Person unlucky = test.getLowestStudent();
			test.bumpLowestStudent();
			test.addStudent(applicant);
			unlucky.nextPreference();
			return unlucky;
		}

		applicant.nextPreference();
		return applicant;






    }

    /**
        public boolean hasMorePeople()

        returns true or false depending on if there are more unsorted people

        this is defined by if current index < number of people
    */
    public boolean hasMorePeople() {
        return currentIndex < sizeOfPeople - 1;
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
