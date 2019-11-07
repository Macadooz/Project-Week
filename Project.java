import java.util.*;
import static java.lang.Math.*;

public class Project{
	ArrayList<Person> enrolledStudents;
	int maxStudents;
	int projectID;
	int numBoys;
	int numGirls;
	int lowestScore;

	public Project(int projectID, int maxStudents){
		this.projectID = projectID;
		this.maxStudents = maxStudents;
		this.enrolledStudents = new ArrayList<Person>();
		this.numBoys = 0;
		this.numGirls = 0;
	}

	public Project(int projectID, ArrayList<Person> enrolledStudents, int numBoys, int numGirls){
		this.projectID = projectID;
		this.enrolledStudents = enrolledStudents;
		this.numBoys = numBoys;
    	this.numGirls = numGirls;
	}

	//getters
	public int getProjectID(){
		return this.projectID;
	}

	public int getnumBoys(int numBoys){
		return numBoys;
	}

	public int getnumGirls(int numGirls){
		return numGirls;
	}

	public int getMaxStudents(){
		return maxStudents;
	}

	public void setEnrolledStudents(ArrayList<Person> newStudents){
		this.enrolledStudents = newStudents;
	}

	public ArrayList<Person> getEnrolledStudents(){
		return this.enrolledStudents;
	}

	public Person getLowestStudent(){
		return this.enrolledStudents.get(0);
	}

	public void addStudent(Person p){
		
		for(int i = 0; i < enrolledStudents.size(); i++){
			Person current = enrolledStudents.get(i);
			if(p.getScore() < current.getScore()){
				this.enrolledStudents.add(i, p);
				return;
			}
		}
		this.enrolledStudents.add(p);
	
	}

	public void removeStudent(Person p){
		this.enrolledStudents.remove(p);
	}

	public void bumpLowestStudent(){
		this.enrolledStudents.remove(0);
	}

	public void setNumBoys(int boys){
		this.numBoys = boys;
	}

	public void setNumGirls(int girls){
		this.numGirls = girls;
	}

	public int getLowestScore() {
		int lowestScore = enrolledStudents.get(0).getScore();
		for (int i=0; i<enrolledStudents.size(); i++) {
			lowestScore = Math.min(lowestScore, enrolledStudents.get(i).getScore());
		}
		return lowestScore;
	}

}
