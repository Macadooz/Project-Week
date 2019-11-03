public class Person{
	int studentID;
	int currentPreference;

	//this field is how much priority the Person currently has
	int score;

	/*
	* I wasn't positive exactly what constructors we were going to need
	* so I just made all of them. We can delete the redundant ones later
	*/

	public Person(int studentID, int currentPreference, int score){
		this.studentID = studentID;
		this.currentPreference = currentPreference;
		this.score = score;
	}

	public Person(int studentID){
		this.studentID = studentID;
		this.currentPreference  = 1;
		this.score = 0;
	}

	public Person(int studentID, int score){
		this.studentID = studentID;
		this.score = score;
		this.currentPreference = 1;
	}
	
	//getter
	public int getStudentID(){
		return this.studentID;
	}
	
	//getter
	public int getCurrentPreference(){
		return this.currentPreference;
	}
	
	//getter
	public int getScore(){
		return this.score;
	}



}
