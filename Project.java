public class Project{
	int enrolledStudents;
	int maxStudents;
	int projectID;
	int numBoys;
	int numGirls;

	public Project(int projectID, int maxStudents){
		this.projectID = projectID;
		this.maxStudents = maxStudents;
		this.enrolledStudents = 0;
		this.numBoys = 0;
		this.numGirls = 0;
	}

	public Project(int projectID, int enrolledStudents, int numBoys, int numGirls){
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

	public int getmaxStudents(int maxstudents){
		return maxStudents;
	}
	public void setEnrolledStudents(int newStudents){
		this.enrolledStudents = newStudents;
	}
	public void setNumBoys(int boys){
		this.numBoys = boys;
	}
	public void setNumGirls(int girls){
		this.numGirls = girls;
	}


}
