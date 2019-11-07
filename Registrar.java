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
			int j = 0;
			int score = 0;
			int[] prevYears = db.getPrevYears(tempList.get(i));
			int grade = db.getGrade(tempList.get(i));
			System.out.println(grade);
			if (grade == -1) continue;
			for(; j < 2022 - grade; j++ ){
				
				score += prevYears[j];
			}
			for (; j < 3; j++){
				score += 4;
			}
				
			allPeople.add(new Person(tempList.get(i),score));
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
		System.out.println("Number of people who are not placed is: " + unluckyPeople.size());	
	}


    /**
        public Person tryPlacePerson(Person p)

        Attempts to place a person p into the
    */
    public Person tryPlacePerson(Person applicant) {
        //first check if the person has more choices
    	System.out.println("trying to place a person");
	if (applicant.getCurrentPreference() > 8) {
            System.out.println("unlucky");
		unluckyPeople.add(applicant);
            return null;
    	}
	
		//lookup which project
	System.out.println("a");
   		Integer pid = db.getPreference(applicant.getStudentID(), applicant.getCurrentPreference());
		if(pid == -1){
			System.out.println(3);
			return getNextPerson();

		}
		System.out.println(4);
		//then try to actually place them
    		Project test = allCourses.get(pid);
		System.out.println(5);
		if(test == null){System.out.println("AHAHAAHA"); return null;}
		System.out.println(test.getMaxStudents());
		System.out.println(test.getEnrolledStudents().size());
		if (test.getEnrolledStudents().size() < test.getMaxStudents()){	

			System.out.println(test.getEnrolledStudents().size());
			test.addStudent(applicant);	

			System.out.println(2);
			return null;	
		}
		else if (applicant.getScore() > test.getLowestStudent().getScore()){
			System.out.println("Kicking someone out");
			Person unlucky = test.getLowestStudent();
			test.bumpLowestStudent();
			test.addStudent(applicant);
			unlucky.nextPreference();
			return unlucky;
		}
		System.out.println(1);
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
